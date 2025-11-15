package cn.edu.sju.jee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hr")
public class HrController {

    /**
     * 跳转到人事管理系统主页
     */
    @GetMapping("/main")
    public String mainPage() {
        return "hr/main";
    }
} 