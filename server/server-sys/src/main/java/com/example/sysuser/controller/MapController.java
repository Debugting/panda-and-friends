package com.example.sysuser.controller;

import com.example.common.annotation.Pass;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MapController {
    /**
     * 地址选择
     */
    @RequestMapping("map/addressChoice")
    @Pass
    public String mapChoice(HttpServletRequest request, HttpServletResponse response) {
        return "sys/address_choice";
    }
}
