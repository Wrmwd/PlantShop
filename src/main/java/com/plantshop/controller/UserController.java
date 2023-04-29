package com.plantshop.controller;

import com.plantshop.model.User;
import com.plantshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @ModelAttribute
    private void userDetails(Model m, Principal p){
        String email = p.getName();
        User user = userRepo.findByEmail(email);
        m.addAttribute("user", user);
    }
    @GetMapping("/")
    public String home(){
        return "user/home";
    }
    @GetMapping("/changPass")
    public String loadChangePassword(){
        return "user/change_password";
    }
    @PostMapping("/updatePassword")
    public String changePassword(Principal p, @RequestParam("oldPass") String oldPass, @RequestParam("newPass") String newPass, HttpSession session){
       String email = p.getName();
       User loginUser = userRepo.findByEmail(email);
        boolean f = passwordEncoder.matches(oldPass, loginUser.getPassword());
        if(f){
           loginUser.setPassword(passwordEncoder.encode(newPass));
           User updatePasswordUser = userRepo.save(loginUser);
           if(updatePasswordUser!=null){
               session.setAttribute("msg", "Password Change Successful");
           }
           else{
               session.setAttribute("msg", "Something went wrong on server");
           }
        }
        else{
            session.setAttribute("msg", "Old password is incorrect");
        }
        return "redirect:/user/changPass";
    }
}
