package cn.edu.sju.jee.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@ControllerAdvice
public class ExceptionController {
    /**
     * 全局异常处理，捕获数据库唯一约束异常并跳转error页面
     */
    @ExceptionHandler
    public String handleException(SQLIntegrityConstraintViolationException e,Model model){
        log.error("报错信息如下：",e);
        model.addAttribute("errorMessage",e.getMessage());
        return "error";
    }
    /**
     * 全局异常处理，捕获算术异常并跳转error页面
     */
    @ExceptionHandler
    public String handleException(ArithmeticException e,Model model){
        log.error("报错信息如下：",e);
        model.addAttribute("errorMessage",e.getMessage());
        return "error";
    }
}
