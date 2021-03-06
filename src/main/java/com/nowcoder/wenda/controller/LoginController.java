package com.nowcoder.wenda.controller;

import com.nowcoder.wenda.async.EventModel;
import com.nowcoder.wenda.async.EventProducer;
import com.nowcoder.wenda.async.EventType;
import com.nowcoder.wenda.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * Created by ${ywj} on 2017/12/22.
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);



    @Autowired
    UserService userService;

    @Autowired
    EventProducer eventProducer;


    @RequestMapping(path = {"/reg/"}, method = {RequestMethod.POST})

    public String reg(Model model,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "next", required = false) String next,
                      HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.register(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", (String) map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);
                eventProducer.fireEvent(new EventModel(EventType.LOGIN).setExt("username", username)
                        .setExt("email", "2438015961@qq.com")
                        .setActorId((int) map.get("userId")));

                if (StringUtils.isNotBlank(next)) {
                    return "redirect:" +next;
                }
                return "redirect:/";

            }else{
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return "login";
        }

    }

    @RequestMapping(path = {"/login/"}, method = {RequestMethod.POST})

    public String login(Model model,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "next", required = false) String next,
                        @RequestParam(value="rememberme", defaultValue ="false") boolean rememberme,
                        HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.login(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", (String) map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);
                if (StringUtils.isNotBlank(next)) {
                    return "redirect:" +next;
                }
                return "redirect:/";

            }else{
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return "login";
        }

    }

    @RequestMapping(path = {"/reglogin"}, method = {RequestMethod.GET})

    public String reg(Model model, @RequestParam(value = "next", required = false) String next) {
        model.addAttribute("next", next);
        return "login";

    }

    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET})

    public String logout(@CookieValue("ticket") String ticket) {

        //使ticket失效
        userService.logout(ticket);
        return "redirect:/";//返回主页
    }




}
