package com.nowcoder.wenda.configuration;

import com.nowcoder.wenda.interceptor.LoginRequiredInterceptor;
import com.nowcoder.wenda.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by nowcoder on 2016/7/3.
 */
@Component
public class WendaWebConfiguration extends WebMvcConfigurerAdapter {
    @Autowired//注册自己的拦截器
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);//将自己定义的拦截器注册到整个链路上
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/user/*");
        super.addInterceptors(registry);
    }
}
