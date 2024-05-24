package com.example.sysuser.controller;

import com.example.sysuser.constant.SessionConstant;
import com.example.utils.AuthTreeUtils;
import com.example.utils.RandomCodeUtil;
import com.example.utils.UserUtils;
import com.example.common.annotation.Pass;
import com.example.common.enums.ResponseCode;
import com.example.common.bean.DataRes;
import com.example.sysuser.bean.SysAuth;
import com.example.sysuser.bean.SysUser;
import com.example.sysuser.service.SysAuthService;
import com.example.sysuser.service.SysUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 登录控制器
 */
@Controller
public class LoginController {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysAuthService sysAuthService;

    @Value("${rootUser}")
    private String rootUser;

    private String loginHtml = "login";

    /**
     * 跳转到登录页面
     */
    @RequestMapping("login/gotoLogin")
    @Pass
    public String gotoLogin() {
        return loginHtml;
    }

    /**
     * 获取图形验证码
     */
    @RequestMapping("login/imageCode")
    @Pass
    public void imageCode(HttpSession session, HttpServletResponse response) {
        String rand = RandomCodeUtil.getVerifyCode(4);// 生成随机四位验证码
        session.setAttribute(SessionConstant.imageLgoinCode, rand);
        // 绘制验证码图片
        RandomCodeUtil.createValidateCode(response, rand);
    }

    /**
     * 跳转到登录页面
     */
    @RequestMapping("login/loginout")
    @Pass
    public String loginout(HttpSession session) {
        UserUtils.removeUser(session);
        return loginHtml;
    }

    /**
     * 跳转到登录页面
     */
    @RequestMapping("login/login")
    @ResponseBody
    @Pass
    public DataRes login(SysUser sysUser, HttpServletRequest request) {
        if (!RandomCodeUtil.isValidImageVerifyCode(request, "verifyCode")) {
            return DataRes.error(ResponseCode.LOGIN_IMAGECODE.code(), ResponseCode.LOGIN_IMAGECODE.desc());
        }
        SysUser param = new SysUser();
        param.setAct(sysUser.getAct());
        List<SysUser> sysUsers = sysUserService.selectByCondition(param);
        if (sysUsers.size() > 0) {
            SysUser data = sysUsers.get(0);
            if (!data.getPwd().equals(sysUser.getPwd())) {
                return DataRes.error(ResponseCode.LOGIN_UNPASSWORD.code(), ResponseCode.LOGIN_UNPASSWORD.desc());
            }
            List<SysAuth> recurve;
            List<SysAuth> sysAuths;
            HttpSession session = request.getSession();
            //如果是超级管理员查询所有的权限
            if (rootUser.equals(sysUser.getAct())) {
                SysAuth sysAuth = new SysAuth();
                sysAuth.setOrderByString(" order by sort asc");
                sysAuths = sysAuthService.selectAll(sysAuth);
                recurve = AuthTreeUtils.recurve(sysAuths);
            } else {
                sysAuths = sysAuthService.queryByUser(data.getId());
                sysAuths = sysAuths.stream().distinct().collect(Collectors.toList());
                recurve = AuthTreeUtils.recurve(sysAuths);

                //遍历, 已url为key存放用户权限到session中
                Map<String, SysAuth> userAuth = new HashMap<>();
//                sysAuths.forEach(t -> userAuth.put(t.getHref(), t));
                session.setAttribute(SessionConstant.userAuth, userAuth);
            }

            Map<Integer, SysAuth> authAllById = new HashMap<>();
            Map<String, SysAuth> authAllByHref = new HashMap<>();
            sysAuths.forEach(t -> {
                authAllById.put(t.getId(), t);
                authAllByHref.put(t.getHref(), t);
            });
            session.setAttribute(SessionConstant.authAllById, authAllById);
            session.setAttribute(SessionConstant.authAllByHref, authAllByHref);

            session.setAttribute(SessionConstant.auths, recurve);
            UserUtils.loginUser(data, session);

            return DataRes.success(data);
        } else {
            return DataRes.error(ResponseCode.LOGIN_UNUSERNAME.code(), ResponseCode.LOGIN_UNUSERNAME.desc());
        }
    }
}
