package com.videoSite.controller;

import cn.bob27.service.IpCountService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.videoSite.common.dto.SignUpDto;
import com.videoSite.entity.User;
import com.videoSite.service.UserService;
import com.videoSite.utils.MD5utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class Login_RegisterController {
    @Autowired
    IpCountService ipCountService;
    @Autowired
    UserService userService;
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/userLogin")
    public String login(){
        return "login";
    }

    @PostMapping ("/save")
    public String save(SignUpDto signUpDto, ModelMap modelMap){//dto数据传输对象
        User user = userService.getOne(new QueryWrapper<User>().eq("username",signUpDto.getUsername()));
        if (user != null) {
            modelMap.addAttribute("repeated_username",signUpDto.getUsername());
            return "register";
        }
        User email = userService.getOne(new QueryWrapper<User>().eq("email",signUpDto.getEmail()));
        if(email != null){
            modelMap.addAttribute("repeated_email",signUpDto.getEmail());
            return "register";
        }
        user = new User();
        BeanUtils.copyProperties(signUpDto, user);
        user.setPassword(MD5utils.getMD5(signUpDto.getPassword()));
        user.setStatus(1);
        userService.save(user);
        modelMap.addAttribute("newUsername", user.getUsername());
        modelMap.addAttribute("newPassword", signUpDto.getPassword());
        return "login";
    }
    @GetMapping("/logOut")
    @ResponseBody
    public String logOut(){
        return "login";
    }
}
