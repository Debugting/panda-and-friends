package com.example.sysuser.controller;

import javax.annotation.Resource;

import com.example.common.annotation.Auth;
import com.example.sysuser.bean.SysAuth;
import com.example.sysuser.bean.SysUser;
import com.example.utils.AuthTreeUtils;
import com.example.utils.UserUtils;
import com.example.common.utils.ExcelUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.common.bean.DataRes;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

import com.example.sysuser.service.SysAuthService;
import org.springframework.stereotype.Controller;


@Controller
public class SysAuthController {

    @Resource
    private SysAuthService sysAuthService;

    /**
     * 删除
     */
    @RequestMapping("sysAuth/deleteByPrimaryKey")
    @ResponseBody
    public DataRes deleteByPrimaryKey(SysAuth sysAuth, HttpServletRequest request, HttpServletResponse response) {
        return DataRes.success(sysAuthService.deleteByPrimaryKey(sysAuth));
    }

    /**
     * 保存 如果id存在则修改否则新增
     */
    @RequestMapping("sysAuth/save")
    @ResponseBody
    public DataRes save(SysAuth sysAuth, HttpServletRequest request, HttpServletResponse response) {
        sysAuth.setIcon("layui-icon " + sysAuth.getIcon());
        sysAuth.setParentAuthId(sysAuth.getParentAuthId() == null ? 0 : sysAuth.getParentAuthId());
        SysUser user = UserUtils.getUser(request.getSession());
        if (sysAuth.getId() == null) {
            return DataRes.success(sysAuthService.insert(sysAuth));
        }
        return DataRes.success(sysAuthService.update(sysAuth));
    }

    /**
     * 根据主键查询
     */
    @RequestMapping("sysAuth/selectByPrimaryKey")
    @ResponseBody
    public DataRes selectByPrimaryKey(SysAuth sysAuth, HttpServletRequest request, HttpServletResponse response) {
        return DataRes.success(sysAuthService.selectByPrimaryKey(sysAuth));
    }

    /**
     * 根据条件查询
     */
    @RequestMapping("sysAuth/querySysAuthByCondition")
    @ResponseBody
    public DataRes queryByCondition(SysAuth sysAuth, HttpServletRequest request, HttpServletResponse response) {
        return DataRes.success(sysAuthService.selectByCondition(sysAuth));
    }

    /**
     * 查询
     */
    @RequestMapping("sysAuth/selectAll")
    @ResponseBody
    public DataRes selectAll(HttpServletRequest request, HttpServletResponse response) {
        SysAuth sysAuth = new SysAuth();
        sysAuth.setOrderByString(" order by sort asc");
        return DataRes.success(sysAuthService.selectAll(sysAuth));
    }

    /**
     * 查询树
     */
    @RequestMapping("sysAuth/tree")
    @ResponseBody
    @Auth("sysAuth/selectAll")
    public List<SysAuth> selectTree(HttpServletRequest request, HttpServletResponse response) {
        SysAuth sysAuth = new SysAuth();
        sysAuth.setOrderByString(" order by sort asc");
        List<SysAuth> sysAuths = sysAuthService.selectAll(sysAuth);
        List<SysAuth> recurve = AuthTreeUtils.recurve(sysAuths);
        return recurve;
    }

    /**
     * 导出数据
     */
    @RequestMapping("sysAuth/export")
    public void export(SysAuth sysAuth, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<SysAuth> list = sysAuthService.selectAll(sysAuth);
        Map<String, String> header = new LinkedHashMap<>();
        header.put("id", "后台权限");
        header.put("parentAuthId", "父id");
        header.put("name", "名称");
        header.put("href", "链接");
        header.put("icon", "图标");
        header.put("sort", "排序");
        header.put("shows_", "{\"name\":\"是否显示\",\"0\":\"显示\",\"1\":\"不显示\"}");
        ExcelUtils.exportExcel("后台权限", header, list, response, request);
    }

    /**
     * 跳转到列表页面
     */
    @RequestMapping("sysAuth/gotoList")
    public String gotoList(SysAuth sysAuth, HttpServletRequest request, HttpServletResponse response) {
        return "sys/sys_auth_list";
    }

    /**
     * 跳转到详情页面
     */
    @RequestMapping("sysAuth/gotoDetail")
    @Auth("sysAuth/save")
    public String gotoDetail(SysAuth sysAuth, HttpServletRequest request, HttpServletResponse response) {
        if (sysAuth.getId() != null) {
            request.setAttribute("sys_auth", sysAuthService.selectByPrimaryKey(sysAuth));
        } else {
            request.setAttribute("sys_auth", sysAuth);
        }
        return "sys/sys_auth_detail";
    }
}
