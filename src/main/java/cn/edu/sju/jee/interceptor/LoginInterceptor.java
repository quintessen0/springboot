package cn.edu.sju.jee.interceptor;

import cn.edu.sju.jee.model.TbUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session =request.getSession();
        TbUser user = (TbUser) session.getAttribute("loginUser");
        if (null != user){
            return true;
        }else {
            response.sendRedirect(request.getContextPath()+"/login/page");
            return false;
        }
    }
}
