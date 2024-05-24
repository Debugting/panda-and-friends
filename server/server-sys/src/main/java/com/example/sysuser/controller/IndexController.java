package com.example.sysuser.controller;

import com.example.common.annotation.Auth;
import com.example.common.annotation.Pass;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    /**
     * 跳转到列表页面
     */
    @RequestMapping(value = {"index/gotoIndex", ""})
    @Auth
    public String gotoIndex() {
        return "index";
    }

    /**
     * 跳转到新版欢迎页面
     */
    @RequestMapping("index/gotoWelcome")
    @Pass
    public String gotoWelcome() {
        return "welcome";
    }

}
