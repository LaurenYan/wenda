package com.nowcoder.wenda.interceptor;

import com.nowcoder.wenda.dao.LoginTicketDAO;
import com.nowcoder.wenda.dao.UserDAO;
import com.nowcoder.wenda.model.HostHolder;
import com.nowcoder.wenda.model.LoginTicket;
import com.nowcoder.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by nowcoder on 2016/7/3.
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket = null;
        if (httpServletRequest.getCookies() != null) {
            //找到ticket
            for (Cookie cookie : httpServletRequest.getCookies()) {
                if (cookie.getName().equals("ticket")) {//这个“ticket”是什么？？？
                    ticket = cookie.getValue();
                    break;
                }
            }
        }

        if (ticket != null) {
            LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);

            //判断ticket是否有效
            if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0) {
                return true;
            }

            User user = userDAO.selectById(loginTicket.getUserId());//怎么存user保证后面链路都可以访问，用hostHOlder
            hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && hostHolder.getUser() != null) {
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}
