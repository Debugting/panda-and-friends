package com.example.tbuser.controller;

import javax.annotation.Resource;

import com.example.common.annotation.Pass;
import com.example.common.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Value;;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.common.bean.DataRes;

import java.util.*;

import org.springframework.stereotype.Controller;
import com.example.tbuser.bean.TbUser;
import com.example.tbuser.service.TbUserService;

/**
 * controller
 */
@Controller
public class TbUserController {


    @Value("${rootUser}")
    private String rootUser;
    @Resource
    private TbUserService tbUserService;

    /**
     * 跳转到列表页面
     */
    @RequestMapping("tbUser/gotoList")
    public String gotoList(TbUser tbUser, HttpServletRequest request, HttpServletResponse response) {
        return "example/tb_user_list";
    }


    /**
     * 跳转到详情页面
     */
    @RequestMapping("tbUser/gotoDetail")
    public String gotoDetail(TbUser tbUser, HttpServletRequest request, HttpServletResponse response) {
        if (tbUser.getId() != null) {
            request.setAttribute("tb_user", tbUserService.selectByPrimaryKey(tbUser));
        } else {
            request.setAttribute("tb_user", tbUser);
        }
        return "example/tb_user_detail";

    }


    /**
     * 删除-
     */
    @ResponseBody
    @Pass
    @RequestMapping("tbUser/deleteByPrimaryKey")
    public DataRes deleteByPrimaryKey(TbUser tbUser, HttpServletRequest request, HttpServletResponse response) {
        return DataRes.success(tbUserService.deleteByPrimaryKey(tbUser));
    }


    /**
     * 保存 (主键为空则增加否则修改)->
     */
    @ResponseBody
    @Pass
    @RequestMapping("tbUser/save")
    public DataRes save(TbUser tbUser, HttpServletRequest request, HttpServletResponse response) {
        if (tbUser.getId() != null && tbUser.getId() != 0) {
            tbUserService.update(tbUser);
        } else {
            tbUserService.insert(tbUser);
        }
        return DataRes.success(tbUser.getId());

    }


    /**
     * 保存 (主键为空则增加否则修改)->
     */
    @ResponseBody
    @Pass
    @RequestMapping("tbUser/insert")
    public DataRes insert(@RequestBody TbUser tbUser, HttpServletRequest request, HttpServletResponse response) {
        if (tbUser.getId() != null && tbUser.getId() != 0) {
            tbUserService.update(tbUser);
        } else {
            tbUserService.insert(tbUser);
        }
        return DataRes.success(tbUser.getId());

    }


    /**
     * 根据主键查询->
     */
    @ResponseBody
    @Pass
    @RequestMapping("tbUser/selectByPrimaryKey")
    public DataRes selectByPrimaryKey(TbUser tbUser, HttpServletRequest request, HttpServletResponse response) {
        return DataRes.success(tbUserService.selectByPrimaryKey(tbUser));
    }


    /**
     * 根据条件查询(所有的实体属性都是条件,如果为空则忽略该字段)->
     */
    @ResponseBody
    @Pass
    @RequestMapping("tbUser/selectByCondition")
    public DataRes selectByCondition(TbUser tbUser, HttpServletRequest request, HttpServletResponse response) {
        return DataRes.success(tbUserService.selectByCondition(tbUser));

    }


    /**
     * 分页查询 (所有的实体属性都是条件,如果为空则忽略该字段) (详见Page类.所以的实体都继承该类 默认 page=1 pageSize=10)
     */
    @ResponseBody
    @Pass
    @RequestMapping("tbUser/selectAllByPaging")
    public DataRes selectAllByPaging(TbUser tbUser, HttpServletRequest request, HttpServletResponse response) {
        return DataRes.success(tbUserService.selectAllByPaging(tbUser));

    }


    /**
     * 导出报表->
     */
    @RequestMapping("tbUser/export")
    public void export(TbUser tbUser, HttpServletRequest request, HttpServletResponse response) {
        List<TbUser> list = tbUserService.selectAll(tbUser);
        Map<String, String> header = new LinkedHashMap<>();
        header.put("id", "id");
        header.put("act", "账号");
        header.put("pwd", "密码");
        header.put("name", "姓名");
        header.put("sex_", "{\"name\":\"性别\",\"1\":\"男\",\"0\":\"女\"}");
        header.put("birthday_", "出生日期");
        header.put("province", "籍贯");
        header.put("question", "密保问题");
        header.put("answer", "密保答案");
        header.put("imgUrl", "图片");
        header.put("userType_", "{\"name\":\"类型\",\"1\":\"类型1\",\"2\":\"类型2\",\"3\":\"类型3\"}");
        header.put("status_", "{\"name\":\"状态\",\"1\":\"可用\",\"0\":\"禁用\"}");
        ExcelUtils.exportExcel("用户", header, list, response, request);

    }


}
