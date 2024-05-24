package com.example.sysuser.controller;

import javax.annotation.Resource;

import com.example.common.annotation.Auth;
import com.example.common.annotation.Pass;
import com.example.common.enums.ResponseCode;
import com.example.common.utils.ExcelUtils;
import com.example.sysuser.bean.SysRole;
import com.example.sysuser.service.SysRoleService;
import com.example.utils.UserUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.common.bean.DataRes;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import com.example.sysuser.bean.SysUser;
import com.example.sysuser.service.SysUserService;

/**
 * 后台管理员 controller
 */
@Controller
public class SysUserController {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleService sysRoleService;

    /**
     * 跳转到列表页面
     */
    @RequestMapping("sysUser/gotoList")
    public String gotoList(SysUser sysUser, HttpServletRequest request, HttpServletResponse response) {
        return "sys/sys_user_list";
    }

    /**
     * 跳转到详情页面
     */
    @RequestMapping("sysUser/gotoDetail")
    public String gotoDetail(SysUser sysUser, HttpServletRequest request, HttpServletResponse response) {
        SysRole t = new SysRole();
        List<SysRole> sysRoles = sysRoleService.selectAll(t);
        List<SysRole> usr = new ArrayList<>();
        request.setAttribute("sr", sysRoles);
        if (sysUser.getId() != null) {
            request.setAttribute("sys_user", sysUserService.selectByPrimaryKey(sysUser));
            //查询所有可用的权限
            usr = sysRoleService.queryByUser(sysUser);
        } else {
            request.setAttribute("sys_user", sysUser);
        }
        ArrayList<Integer> usrid = new ArrayList<>();
        usr.forEach(tt -> usrid.add(tt.getId()));
        request.setAttribute("usr", usrid);
        return "sys/sys_user_detail";
    }


    /**
     * 删除-后台管理员
     */
    @ResponseBody
    @Pass
    @RequestMapping("sysUser/deleteByPrimaryKey")
    public DataRes deleteByPrimaryKey(SysUser sysUser, HttpServletRequest request, HttpServletResponse response) {
        return DataRes.success(sysUserService.deleteByPrimaryKey(sysUser));
    }

    @RequestMapping("sysUser/gotoChangePassword")
    @Auth
    public String gotoChangePassword(SysUser sysUser, String newPassword, HttpServletRequest request, HttpServletResponse response) {
        return "sys/change_password";
    }

    /**
     * 修改密码
     */
    @RequestMapping("sysUser/changePassword")
    @ResponseBody
    @Auth
    public DataRes changePassword(SysUser sysUser, HttpServletRequest request, HttpServletResponse response, String password, String newPassword) {
        SysUser user = UserUtils.getUser(request.getSession());
        if (!user.getPwd().equals(password)) {
            return DataRes.error(ResponseCode.LOGIN_UNPASSWORD.code(), ResponseCode.LOGIN_UNPASSWORD.desc());
        }
        user.setPwd(newPassword);
        return DataRes.success(sysUserService.updatePassword(user));
    }

    /**
     * 保存 (主键为空则增加否则修改)-> 后台管理员
     */
    @ResponseBody
    @Pass
    @RequestMapping("sysUser/save")
    public DataRes save(@RequestParam(value = "roles[]", required = false) List<String> roles, SysUser sysUser, HttpServletRequest request, HttpServletResponse response) {
        if (sysUser.getId() == null) {
            return DataRes.success(sysUserService.insert(sysUser, roles));
        }
        return DataRes.success(sysUserService.update(sysUser, roles));
    }


    /**
     * 根据主键查询->后台管理员
     */
    @ResponseBody
    @Pass
    @RequestMapping("sysUser/selectByPrimaryKey")
    public DataRes selectByPrimaryKey(SysUser sysUser, HttpServletRequest request, HttpServletResponse response) {
        return DataRes.success(sysUserService.selectByPrimaryKey(sysUser));
    }


    /**
     * 根据条件查询(所有的实体属性都是条件,如果为空则忽略该字段)->后台管理员
     */
    @ResponseBody
    @Pass
    @RequestMapping("sysUser/selectByCondition")
    public DataRes selectByCondition(SysUser sysUser, HttpServletRequest request, HttpServletResponse response) {
        return DataRes.success(sysUserService.selectByCondition(sysUser));
    }

    /**
     * 分页查询 (所有的实体属性都是条件,如果为空则忽略该字段) (详见Page类.所以的实体都继承该类 默认 page=1 pageSize=10)->后台管理员
     */
    @ResponseBody
    @Pass
    @RequestMapping("sysUser/selectAllByPaging")
    public DataRes selectAllByPaging(SysUser sysUser, HttpServletRequest request, HttpServletResponse response) {
        return DataRes.success(sysUserService.selectAllByPaging(sysUser));
    }

    /**
     * 导出报表->后台管理员
     */
    @RequestMapping("sysUser/export")
    public void export(SysUser sysUser, HttpServletRequest request, HttpServletResponse response) {
        List<SysUser> list = sysUserService.selectAll(sysUser);
        Map<String, String> header = new LinkedHashMap<>();
        header.put("id", "id");
        header.put("act", "账号");
        header.put("pwd", "密码");
        header.put("name", "姓名");
        header.put("icon", "头像");
        header.put("phone", "手机");
        ExcelUtils.exportExcel("后台管理员", header, list, response, request);

    }

}
