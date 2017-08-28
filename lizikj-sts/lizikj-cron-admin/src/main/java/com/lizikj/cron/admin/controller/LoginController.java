package com.lizikj.cron.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
@Controller
@RequestMapping("/")
public class LoginController {

    /**
     * 登录页
     */
    @RequestMapping("/")
    String login() {
        return "login";
    }

    /**
     * 首页
     */
    @RequestMapping("/index")
    String index() {
        return "index";
    }

}
