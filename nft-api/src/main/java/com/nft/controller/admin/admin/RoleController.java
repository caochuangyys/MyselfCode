package com.nft.controller.admin.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nft.controller.admin.admin.req.RoleDelReq;
import com.nft.controller.admin.admin.req.RoleSaveOrUpdateReq;
import com.nft.core.exception.AdminException;
import com.nft.core.exception.ExceptionCode;
import com.nft.data.dto.admin.PermissionDTO;
import com.nft.data.dto.admin.RoleListDTO;
import com.nft.data.entity.RoleDO;
import com.nft.service.admin.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * @author caoc
 * @createDate 2021/11/15
 */
@RestController
@RequestMapping("/admin/role")
@Api(tags = "角色管理", description = "")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @ApiOperation(value = "角色列表", notes = "角色列表不做分页")
    @GetMapping(value = "/list")
    public List<RoleListDTO> list(){
        return roleService.listRoles();

    }

    @ApiOperation(value = "创建角色", httpMethod = "POST")
    @PostMapping(value = "/create")
    public void create(@RequestBody @Valid RoleSaveOrUpdateReq req){

        int count = roleService.count(new QueryWrapper<RoleDO>().eq("name", req.getName()));
        if (count > 0){
            throw new AdminException(ExceptionCode.ADMIN_ROLE_NAME_EXIST);
        }
        RoleDO roleDO = new RoleDO();
        BeanUtils.copyProperties(req, roleDO);
        if (roleService.save(roleDO) == true){
            roleService.insertRolePermission(roleDO.getId(), req.getPermissions());
        }

    }

    @ApiOperation(value = "更新角色", httpMethod = "POST")
    @PostMapping(value = "/update")
    public void update(@RequestBody @Valid RoleSaveOrUpdateReq req){

        RoleDO roleDO = roleService.getById(req.getId());
        if (roleDO == null){
            throw new AdminException(ExceptionCode.ADMIN_ROLE_NOT_EXIST);
        }

        int count = roleService.count(new QueryWrapper<RoleDO>().eq("name", req.getName()).ne("id", req.getId()));
        if (count > 0){
            throw new AdminException(ExceptionCode.ADMIN_ROLE_NAME_EXIST);
        }
        BeanUtils.copyProperties(req, roleDO);
        if (roleService.updateById(roleDO) == true){
            //删除旧角色
            roleService.deleterRolePermission(roleDO.getId(), null);
            //新增角色
            roleService.insertRolePermission(roleDO.getId(), req.getPermissions());
        }
    }


    @ApiOperation(value = "删除角色", httpMethod = "POST")
    @PostMapping(value = "/delete")
    public void delete(@RequestBody @Valid RoleDelReq req){

        if (roleService.removeById(req.getId()) == true){
            //删除管理员关系
            roleService.deleteAdminRoles(null, Arrays.asList(req.getId()));
            //删除权限关系
            roleService.deleterRolePermission(req.getId(), null);
        }
    }


    @ApiOperation(value = "获取所有权限", httpMethod = "GET")
    @GetMapping(value = "/listAllPermission")
    public List<PermissionDTO> listAllPermission(){

        return roleService.listAllPermission();
    }

    @ApiOperation(value = "获取所有角色选项", httpMethod = "GET")
    @GetMapping(value = "/options")
    public List<Map<String, Object>> options(){
        List<RoleDO> roleDOS = roleService.list();
        List<Map<String, Object>> list = new LinkedList<>();
        roleDOS.forEach(item -> {
            Map<String, Object> map = new HashMap<>(16);
            map.put("value", item.getId());
            map.put("label", item.getName());
            list.add(map);
        });
        return list;
    }
}
