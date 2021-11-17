package com.nft.core.sms;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.nft.core.exception.ExceptionCode;
import com.nft.core.exception.ServiceException;
import com.nft.core.exception.SystemException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = "aliyun.sms")
public class AliyunSMSClient implements ISmsClient, InitializingBean {

    private String accessId;
    private String accessKey;
    private String signature;
    private String loginCodeTemplateId;
    private String registerCodeTemplateId;
    private String modifyPwdTemplateId;
    private String sendReserveTemplateId;
    private String traderAuditPassTemplateId;
    private String traderAuditFailTemplateId;
    private String sendWithdrawMoneyReminderId;
    private String sendSuccessfulWithdrawalId;
    private String sendRechargeReminderId;
    private String sendBalanceChangeId;
    private String sendRechargeFailId;
    private String sendWithdrawalFailId;


    private IAcsClient client;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.client = new DefaultAcsClient(DefaultProfile.getProfile("default", accessId, accessKey));
    }

    @Override
    public SMSResult sendLoginCode(String phone, String verifyCode) throws ServiceException {
        log.info("发送登录短信验证码，username={}, code={}", phone, verifyCode);
        JSONObject tempParam = new JSONObject();
        tempParam.put("code", verifyCode);
        return this.sendCommon(phone, tempParam.toString(), loginCodeTemplateId, signature);
    }

    @Override
    public SMSResult sendRegisterCode(String phone, String verifyCode) throws ServiceException {
        log.info("发送注册短信验证码，username={}, code={}", phone, verifyCode);
        JSONObject tempParam = new JSONObject();
        tempParam.put("code", verifyCode);
        return this.sendCommon(phone, tempParam.toString(), registerCodeTemplateId, signature);
    }

    @Override
    public SMSResult sendReserveCode(String phone, String projectName, String materialsName, BigDecimal securityFund) throws ServiceException {
        String context = projectName + "-" + materialsName;
        log.info("[项目预约提醒通知短信发送] 您预约的项目{}，1小时后就要开始抢单了，保证金:{}，请确保账户保证金余额充足！", context, securityFund);
        JSONObject tempParam = new JSONObject();
        tempParam.put("projectName", context);
        tempParam.put("amount", securityFund);
        return this.sendCommon(phone, tempParam.toString(), sendReserveTemplateId, signature);
    }

    @Override
    public SMSResult sendModifyPwdCode(String phone, String verifyCode) throws ServiceException {
        log.info("[修改密码短信发送] phone={},code ={}, ", phone, verifyCode);
        JSONObject tempParam = new JSONObject();
        tempParam.put("code", verifyCode);
        return this.sendCommon(phone, tempParam.toString(), modifyPwdTemplateId, signature);
    }

    @Override
    public SMSResult sendTraderAuditPass(String phone, String companyName) {
        log.info("[发送贸易商审核通过通知] phone={},company ={}, ", phone, companyName);
        JSONObject tempParam = new JSONObject();
        tempParam.put("compnayName", companyName);
        return this.sendCommon(phone, tempParam.toString(), traderAuditPassTemplateId, signature);
    }

    @Override
    public SMSResult sendTraderAuditFail(String phone, String companyName, String reason) {
        log.info("[发送贸易商审核失败通知] phone={},company ={}, reson={}", phone, companyName, reason);
        JSONObject tempParam = new JSONObject();
        tempParam.put("companyName", companyName);
        tempParam.put("reason", reason);
        return this.sendCommon(phone, tempParam.toString(), traderAuditFailTemplateId, signature);
    }

    @Override
    public SMSResult sendRechargeReminder(String phone, BigDecimal amount) {
        log.info("[保证金补充提交] 您提交的金额为：{}的保证金补充申请，平台已受理，请尽快联系财务进行打款！", amount);
        JSONObject tempParam = new JSONObject();
        tempParam.put("amount", amount);
        return this.sendCommon(phone, tempParam.toString(), sendRechargeReminderId, signature);
    }

    @Override
    public SMSResult sendBalanceChange(String phone, BigDecimal amount) {
        log.info("[保证金补充成功] 您发起的保证金补充申请已成功完成，金额为：{}，您可以登录APP查看结果。", amount);
        JSONObject tempParam = new JSONObject();
        tempParam.put("amount", amount);
        return this.sendCommon(phone, tempParam.toString(), sendBalanceChangeId, signature);
    }

    @Override
    public SMSResult sendWithdrawMoneyReminder(String phone, BigDecimal amount) {
        log.info("[用户提现申请通知] 您提交的金额为：{} 的提现申请，平台已接收，请等待平台完成审核。", amount);
        JSONObject tempParam = new JSONObject();
        tempParam.put("amount", amount);
        return this.sendCommon(phone, tempParam.toString(), sendWithdrawMoneyReminderId, signature);
    }

    @Override
    public SMSResult sendSuccessfulWithdrawal(String phone, BigDecimal amount) {
        log.info("[提现成功通知] 您提交的提现申请，申请金额：{}，平台已通过审核，提现金额已汇至您的企业账户，请注意查收！", amount);
        JSONObject tempParam = new JSONObject();
        tempParam.put("amount", amount);
        return this.sendCommon(phone, tempParam.toString(), sendSuccessfulWithdrawalId, signature);
    }

    @Override
    public SMSResult sendRechargeFail(String phone, BigDecimal amount, String reason) {
        log.info("[保证金补充失败通知] 您提交的金额为：{}的保证金补充申请，平台审核未通过，失败原因：{}。", amount, reason);
        JSONObject tempParam = new JSONObject();
        tempParam.put("amount", amount);
        tempParam.put("reason", reason);
        return this.sendCommon(phone, tempParam.toString(), sendRechargeFailId, signature);
    }

    @Override
    public SMSResult sendWithdrawalFail(String phone, BigDecimal amount, String reason) {
        log.info("[提现审核失败] 您发起的金额为：${amount}的提现申请，平台审核未通过，失败原因：${reason}。", amount, reason);
        JSONObject tempParam = new JSONObject();
        tempParam.put("amount", amount);
        tempParam.put("reason", reason);
        return this.sendCommon(phone, tempParam.toString(), sendWithdrawalFailId, signature);
    }

    private SMSResult sendCommon(String phone, String templateParam, String templateId, String signature) throws ServiceException {
        try {
            CommonRequest request = new CommonRequest();
            request.setMethod(MethodType.POST);
            request.setDomain("dysmsapi.aliyuncs.com");
            request.setVersion("2017-05-25");
            request.setAction("SendSms");
            request.putQueryParameter("RegionId", "default");
            request.putQueryParameter("PhoneNumbers", phone);
            request.putQueryParameter("SignName", signature);
            request.putQueryParameter("TemplateCode", templateId);
            request.putQueryParameter("TemplateParam", templateParam);
            CommonResponse commonResponse = client.getCommonResponse(request);
            String data = commonResponse.getData();
            log.info("短信发送返回结果：{}", data);
            JSONObject jsonObject = JSONObject.parseObject(data);
            String message = jsonObject.getString("Message");
            SMSResult smsResult = new SMSResult();
            smsResult.setSucc("OK".equalsIgnoreCase(message));
            smsResult.setMsg(message);
            return smsResult;
        } catch (ClientException e) {
            throw new SystemException(ExceptionCode.THIRD_PART_SERVICE_ERR);
        } catch (Exception e) {
            log.error("[阿里云短信发送] 异常", e);
            throw new SystemException(ExceptionCode.SMS_SERVICE_ERR);
        }
    }

}
