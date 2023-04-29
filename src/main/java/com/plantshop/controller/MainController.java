package com.plantshop.controller;

import javax.servlet.http.HttpSession;

import com.plantshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.plantshop.model.UserDtls;
import com.plantshop.service.UserService;

import java.security.Principal;

@Controller
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepo;
    @ModelAttribute
    private void userDetails(Model m, Principal p){
       if(p!=null){
           String email = p.getName();
           UserDtls user = userRepo.findByEmail(email);
           m.addAttribute("user", user);
       }
    }
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/signin")
    public String login(){
        return "/login";
    }
    @GetMapping("/register")
    public String register(){
        return "/register";
    }
    @PostMapping("/createUser")
    public String createUser(@ModelAttribute UserDtls user, HttpSession session) {
        System.out.println(user);
        boolean f = userService.checkEmail(user.getEmail());
        if (f) {
            session.setAttribute("msg","User with this email already exists");
        } else {
            UserDtls userDtls = userService.createUser(user);
            if (userDtls != null) {
                session.setAttribute("msg","Register successfully");
            } else {
                session.setAttribute("msg","Something went wrong on server");
            }
        }
            return "redirect:/register";
    }
}
