package com.nft.interceptor;

import com.nft.core.component.CacheComponent;
import com.nft.core.exception.AppException;
import com.nft.core.exception.ExceptionCode;
import com.nft.core.interceptor.AbstractInterceptor;
import com.nft.data.consts.SystemConstant;
import com.nft.data.dto.admin.AdminDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Slf4j
@Component
public class AdminLoginInterceptor extends AbstractInterceptor {

    @Autowired
    private CacheComponent cacheComponent;

    @Override
    public List<String> getPathPatterns() {
        return Arrays.asList("/admin/**");
    }

    @Override
    public List<String> getExcludePathPatterns() {
        return Arrays.asList("/admin/admin/login","/admin/file/**");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            log.info("OPTIONS请求，放行");
            return true;
        }

        String accessToken = request.getHeader(SystemConstant.ADMIN_ACCESS_TOKEN);
        if (StringUtils.isEmpty(accessToken)) {
            log.error("请求header中未包含access token，登录状态错误");
            throw new AppException(ExceptionCode.ADMIN_NOT_LOGIN);
        }
        AdminDTO adminDTO = cacheComponent.getObj(accessToken, AdminDTO.class);
        if (adminDTO != null) {
            cacheComponent.putObj(accessToken, adminDTO, SystemConstant.ADMIN_SESSION_EXPIRE_TIME);
            return true;
        } else {
            throw new AppException(ExceptionCode.ADMIN_NOT_LOGIN);
        }
    }
}
