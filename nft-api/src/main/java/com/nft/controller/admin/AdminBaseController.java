package com.nft.controller.admin;

import com.nft.data.dto.admin.AdminDTO;
import com.nft.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author caoc
 * @createDate 2021/11/15
 */
@Controller
public class AdminBaseController {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;

    @Autowired
    private AdminService adminService;

    /**
     * 获取管理员id
     *
     * @return
     */
    protected Integer getAdminId() {
        return adminService.getCurrentAdminId();
    }

    /**
     * 取登录用户名
     *
     * @return
     */
    protected String getAdminName() {
        AdminDTO admin = adminService.getCurrentAdmin();
        if (admin == null) {
            return "未知";
        }
        return admin.getRealname();
    }

}
