package cn.edu.sju.jee.controller;

import cn.edu.sju.jee.model.ApiResponse;
import cn.edu.sju.jee.model.TbUser;
import cn.edu.sju.jee.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 用户登录API
     */
    @PostMapping("/login/doLogin")
    public ApiResponse<TbUser> loginHandler(@RequestBody TbUser user) {
        try {
            Boolean loginFlag = loginService.checkLogin(user.getUserName(), user.getPassword());
            if (loginFlag) {
                return ApiResponse.success("登录成功", user);
            } else {
                return ApiResponse.error("用户名或密码错误");
            }
        } catch (Exception e) {
            return ApiResponse.error("登录失败: " + e.getMessage());
        }
    }
}
