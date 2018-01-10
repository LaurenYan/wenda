package com.nowcoder.wenda.controller;

import com.nowcoder.wenda.model.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ${ywj} on 2017/12/20.
 */
//@Controller
public class IndexController {
    @RequestMapping(path = {"/", "/index"})
    @ResponseBody
    public String index() {
        return "Hello NowCoder";
    }

    @RequestMapping(path = {"/profile/{groundId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @PathVariable("groundId") String groundId,
                          @RequestParam(value = "type",defaultValue = "1") int type,
                          @RequestParam(value = "key", defaultValue = "z",required = false) String key) {
        return String.format("Profile Page of %s/%d,t:%d k:%s", groundId, userId, type, key);
    }

    @RequestMapping(path={"/vm"},method = {RequestMethod.GET})
    public String template(Model model){
        List<String> colors = Arrays.asList(new String[]{"RED", "GREEN", "BLUE"});
        model.addAttribute("colors", colors);
        Map<String, String> map = new HashMap<>();
        for (int i=0;i<4;i++) {
            map.put(String.valueOf(i), String.valueOf(i * i));
        }
        model.addAttribute("user", new User("LEE"));
        model.addAttribute("map", map);
        return "home";
    }

}
