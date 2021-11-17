### 一、模块说明

--sg-core 核心内容模块

--sg-data 数据层，包含DO、MAPPER和DTO

--sg-service 服务层

--sg-api 对外接口层，包含app和admin

--sg-generator 代码生成


### 二、开发说明


1、创建表结构，并执行到数据库

2、进入sg-generator，修改generator.properties对应表

3、执行MybatisPlusCodeGenerator main方法

4、把相应的类拷贝到对应模块

5、注解UserLoginAuth、AdminLoginAuth登录拦截，可注解在controller或者method


### 三、接口文档地址

http://127.0.0.1:8080/api/doc.html

可切换app和admin接口




### 四、开发规范
* 1、基本开发规范参照《阿里巴巴JAVA开发手册》
* 2、查询请求一律使用GET, 其他请求使用POST
```$xslt
//对于复杂对象，GET请求无需@RequestBody注解
@GetMapping(value = "/page")
public ZPage<CompanyDTO> page(CompanyQueryParamIn paramIn) {

//post请求添加@RequestBody及@Valid注解
@PostMapping(value = "/createOrUpdate")
public void createOrUpdate(@RequestBody @Valid CompanyAddOrUpParamIn paramIn) {
```
* 3、分页查询参数统一继承PageQuery，返回ZPage对象
```$xslt
@Data
@ApiModel(value = "企业搜索")
public class CompanyPageQuery extends PageQuery {

    @ApiModelProperty(value = "城市id")
    private Integer areaId;

    @ApiModelProperty(value = "行业id")
    private Integer industryId;

    @ApiModelProperty(value = "关键字")
    private String keyword;

}
```
* 4、新增、更新、删除接口请求对象，做好字段校验
```$xslt
@ApiModel(value = "管理员登录请求")
@Data
public class AdminLoginReq {

    @NotBlank(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号")
    private String phone;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "短信验证码")
    private String verifyCode;
}
```