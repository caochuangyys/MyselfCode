package com.nft.core.sms;

import com.nft.core.exception.ServiceException;

import java.math.BigDecimal;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
public interface ISmsClient {
    /**
     * 发送登录验证码
     *
     * @param phone
     * @param verifyCode
     * @return
     * @throws ServiceException
     */
    SMSResult sendLoginCode(String phone, String verifyCode) throws ServiceException;


    /**
     * 发送注册验证码
     *
     * @param phone
     * @param verifyCode
     * @return
     * @throws ServiceException
     */
    SMSResult sendRegisterCode(String phone, String verifyCode) throws ServiceException;

    /**
     * 发送项目预约提醒通知短信
     *
     * @param phone
     * @param projectName
     * @param materialsName
     * @param securityFund
     * @return
     * @throws ServiceException
     */
    SMSResult sendReserveCode(String phone, String projectName, String materialsName, BigDecimal securityFund) throws ServiceException;

    /**
     * 发送修改密码短信
     *
     * @param phone
     * @param verifyCode
     * @return
     * @throws ServiceException
     */
    SMSResult sendModifyPwdCode(String phone, String verifyCode) throws ServiceException;

    /**
     * 发送贸易商审核通过通知
     *
     * @param phone
     * @param companyName
     * @return
     */
    SMSResult sendTraderAuditPass(String phone, String companyName);

    /**
     * 发送贸易商审核失败通知
     *
     * @param phone
     * @param companyName
     * @param reason
     * @return
     */
    SMSResult sendTraderAuditFail(String phone, String companyName, String reason);

    /**
     * 充值通知
     *
     * @param phone
     * @param amount
     * @return
     */
    SMSResult sendRechargeReminder(String phone, BigDecimal amount);

    /**
     * 充值成功通知
     *
     * @param phone
     * @param amount
     * @return
     */
    SMSResult sendBalanceChange(String phone, BigDecimal amount);

    /**
     * 提现通知
     *
     * @param phone
     * @param amount
     * @return
     */
    SMSResult sendWithdrawMoneyReminder(String phone, BigDecimal amount);

    /**
     * 提现成功通知
     *
     * @param phone
     * @param amount
     * @return
     */
    SMSResult sendSuccessfulWithdrawal(String phone, BigDecimal amount);

    /**
     * 充值失败通知
     *
     * @param phone
     * @param amount
     * @param reason
     * @return
     */
    SMSResult sendRechargeFail(String phone, BigDecimal amount, String reason);

    /**
     * 提现失败通知
     *
     * @param phone
     * @param amount
     * @param reason
     * @return
     */
    SMSResult sendWithdrawalFail(String phone, BigDecimal amount, String reason);
}
