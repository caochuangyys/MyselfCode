package com.nft.controller.commons;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.nft.core.exception.ExceptionCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author caoc
 * @createDate 2021/11/15
 */
@RestController
@RequestMapping("/commons/common")
@Api(tags = "公共接口", description = "通用型的接口调用")
public class CommonController {


    @ApiOperation(value = "系统错误码", httpMethod = "GET")
    @GetMapping(value = "/exceptionCodes")
    public Map<Integer, String> exceptionCodes(){
        return ExceptionCode.toValueMap();
    }


    @ApiOperation(value = "健康检查", httpMethod = "GET", hidden = true)
    @GetMapping(value = "/health")
    public String health(){

        LocalDateTime now = LocalDateTime.now();

        return LocalDateTimeUtil.format(now, "yyyy-MM-dd HH:mm:ss");

    }

}
