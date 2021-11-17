package com.nft.core.sms;

import com.nft.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Slf4j
public class MockSMSClient implements ISmsClient {

    @Override
    public SMSResult sendLoginCode(String phone, String verifyCode) throws ServiceException {
        log.info("[模拟短信验证码发送] username={}, 验证码: {}", phone, verifyCode);
        SMSResult smsResult = new SMSResult();
        smsResult.setSucc(true);
        smsResult.setMsg("OK");
        return smsResult;
    }

    @Override
    public SMSResult sendRegisterCode(String phone, String verifyCode) throws ServiceException {
        log.info("[模拟注册验证码发送] username={}, 验证码: {}", phone, verifyCode);
        SMSResult smsResult = new SMSResult();
        smsResult.setSucc(true);
        smsResult.setMsg("OK");
        return smsResult;
    }

    @Override
    public SMSResult sendReserveCode(String phone, String projectName, String materialsName, BigDecimal securityFund) throws ServiceException {
        String context = projectName + "-" + materialsName;
        log.info("[模拟项目预约提醒通知短信发送] 您预约的项目{}，1小时后就要开始抢单了，保证金：{}，请确保账户保证金余额充足！", context, securityFund);
        SMSResult smsResult = new SMSResult();
        smsResult.setSucc(true);
        smsResult.setMsg("OK");
        return smsResult;
    }

    @Override
    public SMSResult sendModifyPwdCode(String phone, String verifyCode) throws ServiceException {
        log.info("[模拟修改密码短信发送] phone={},code ={}, ", phone, verifyCode);
        SMSResult smsResult = new SMSResult();
        smsResult.setSucc(true);
        smsResult.setMsg("OK");
        return smsResult;
    }

    @Override
    public SMSResult sendTraderAuditPass(String phone, String companyName) {
        log.info("[模拟发送贸易商审核通过通知] phone={},company ={}, ", phone, companyName);
        SMSResult smsResult = new SMSResult();
        smsResult.setSucc(true);
        smsResult.setMsg("OK");
        return smsResult;
    }

    @Override
    public SMSResult sendTraderAuditFail(String phone, String companyName, String reason) {
        log.info("[模拟发送贸易商审核失败通知] phone={},company ={}, reson={}", phone, companyName, reason);
        SMSResult smsResult = new SMSResult();
        smsResult.setSucc(true);
        smsResult.setMsg("OK");
        return smsResult;
    }

    @Override
    public SMSResult sendRechargeReminder(String phone, BigDecimal amount) {
        log.info("[保证金补充提交] 您提交的金额为：{}的保证金补充申请，平台已受理，请尽快联系财务进行打款！", amount);
        SMSResult smsResult = new SMSResult();
        smsResult.setSucc(true);
        smsResult.setMsg("OK");
        return smsResult;
    }

    @Override
    public SMSResult sendBalanceChange(String phone, BigDecimal amount) {
        log.info("[保证金补充成功] 您发起的保证金补充申请已成功完成，金额为：{}，您可以登录APP查看结果。", amount);
        SMSResult smsResult = new SMSResult();
        smsResult.setSucc(true);
        smsResult.setMsg("OK");
        return smsResult;
    }

    @Override
    public SMSResult sendWithdrawMoneyReminder(String phone, BigDecimal amount) {
        log.info("[用户提现申请通知] 您提交的金额为：{} 的提现申请，平台已接收，请等待平台完成审核。", amount);
        SMSResult smsResult = new SMSResult();
        smsResult.setSucc(true);
        smsResult.setMsg("OK");
        return smsResult;
    }

    @Override
    public SMSResult sendSuccessfulWithdrawal(String phone, BigDecimal amount) {
        log.info("[提现成功通知] 您提交的提现申请，申请金额：{}，平台已通过审核，提现金额已汇至您的企业账户，请注意查收！", amount);
        SMSResult smsResult = new SMSResult();
        smsResult.setSucc(true);
        smsResult.setMsg("OK");
        return smsResult;
    }

    @Override
    public SMSResult sendRechargeFail(String phone, BigDecimal amount, String reason) {
        log.info("[保证金补充失败通知] 您提交的金额为：{}的保证金补充申请，平台审核未通过，失败原因：{}。", amount, reason);
        SMSResult smsResult = new SMSResult();
        smsResult.setSucc(true);
        smsResult.setMsg("OK");
        return smsResult;
    }

    @Override
    public SMSResult sendWithdrawalFail(String phone, BigDecimal amount, String reason) {
        log.info("[提现审核失败] 您发起的金额为：${amount}的提现申请，平台审核未通过，失败原因：${reason}。", amount, reason);
        SMSResult smsResult = new SMSResult();
        smsResult.setSucc(true);
        smsResult.setMsg("OK");
        return smsResult;
    }
}
