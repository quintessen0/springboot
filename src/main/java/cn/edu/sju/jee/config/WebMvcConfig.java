package cn.edu.sju.jee.config;

import cn.edu.sju.jee.interceptor.LoginInterceptor;
import org.springframework.lang.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Spring MVC配置类
 * 替换原来的springmvc.xml配置
 * 
 * @author Spring Boot
 * @version 1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // 在前后端分离架构中不需要JSP视图解析器

    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/student/**")
                .excludePathPatterns("/login/*");
    }

    /**
     * 配置静态资源处理
     */
    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        // 配置静态资源处理
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        // 配置根路径下静态资源处理
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true);
    }

    /**
     * 配置视图控制器
     */
    @Override
    public void addViewControllers(@NonNull ViewControllerRegistry registry) {
        // 根路径重定向到前端应用
        registry.addRedirectViewController("/", "/index.html");
    }

    /**
     * 配置格式化器
     */
    @Override
    public void addFormatters(@NonNull FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter("yyyy-MM-dd"));
    }
}
