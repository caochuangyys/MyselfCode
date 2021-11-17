package com.nft.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_customer")
@ApiModel(value = "Customer对象", description = "资信用户表")
public class CustomerDO extends BaseDO {

    private static final long serialVersionUID=1L;
    @ApiModelProperty(value = "客户类型 1-企业 2-个人")
    private Integer customerType;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "证件类型 1-身份证")
    private Integer papersType;

    @ApiModelProperty(value = "证件号")
    private String personIdno;

    @ApiModelProperty(value = "性别 0女，1男")
    private Boolean gender;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "联系方式")
    private String contactInformation;

    @ApiModelProperty(value = "所在地址")
    private String address;

    @ApiModelProperty(value = "是否征信授权 0-未授权 1-已授权 ")
    private Boolean creditAuthorize;

    @ApiModelProperty(value = "贷款金额")
    private BigDecimal loanAmount;

    @ApiModelProperty(value = "贷款期限(月)")
    private Integer loanTerm;

    @ApiModelProperty(value = "贷款用途")
    private String loanPurpose;

    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;

    @ApiModelProperty(value = "企业信用代码")
    private String licenseNo;

    @ApiModelProperty(value = "经营状态 1-存续 ")
    private Integer operationStatus;

    @ApiModelProperty(value = "成立时间")
    private LocalDate establishTime;

    @ApiModelProperty(value = "注册资本")
    private BigDecimal capital;

    @ApiModelProperty(value = "企业性质 1-有限合伙企业 ")
    private Integer enterpriseNature;

    @ApiModelProperty(value = "员工人数")
    private Integer employeesNumber;

    @ApiModelProperty(value = "所属行业")
    private String industry;

    @ApiModelProperty(value = "是否长期经营，0：否，1：是")
    private Integer isLongTerm;

    @ApiModelProperty(value = "开始营业期限")
    private LocalDate startBusinessTerm;

    @ApiModelProperty(value = "结束营业期限")
    private LocalDate endBusinessTerm;

    @ApiModelProperty(value = "公司详情")
    private String companyDetails;

    @ApiModelProperty(value = "营业执照")
    private String license;

    @ApiModelProperty(value = "法人身份证前")
    private String idFront;

    @ApiModelProperty(value = "法人身份证后")
    private String idBack;

    @ApiModelProperty(value = "负责人id")
    private Integer adminId;

    @ApiModelProperty(value = "受理产品id")
    private Integer loanProductsId;

    @ApiModelProperty(value = "受理担保人id")
    private Integer guaranteeId;

    @ApiModelProperty(value = "客户登记处理意见")
    private String handleOpinion;

    @ApiModelProperty(value = "客户登记状态 0-受理中 1-受理 2-拒绝")
    private Integer registerStatus;

    @ApiModelProperty(value = "最终授信金额")
    private BigDecimal finalCreditAmount;

    @ApiModelProperty(value = "资信审批处理意见")
    private String approvalOpinion;

    @ApiModelProperty(value = "资信审批状态 0-受理中 1-同意 2-拒绝")
    private Integer approvalStatus;



}
