package com.plantshop.controller;

import com.plantshop.model.User;
import com.plantshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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

    @GetMapping ("/editProfile")
    public String loadEditProfile() {
            return "user/edit_profile";
    }
    @PostMapping("/updateProfile")
    public String editProfile(Model model, Authentication authentication, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String password,
                               HttpSession session, String email, String mobileNumber) throws IOException {
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        email = auth.getName();
        User user = userRepo.findByEmail(email);
        boolean f = passwordEncoder.matches(password, user.getPassword());
        if(f) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setMobileNumber(mobileNumber);

            userRepo.save(user);

            model.addAttribute("user", user);
            session.setAttribute("msg", "Изменения сохранены");
        }else{
            session.setAttribute("msg", "Неверный пароль");
        }
        return "redirect:/user/editProfile";
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
               session.setAttribute("msg", "Новый пароль принят");
           } else{
               session.setAttribute("msg", "Ошибка на сервере");
           }
        } else{
            session.setAttribute("msg", "Неверный старый пароль");
        }
        return "redirect:/user/changPass";
    }

    /*
     @GetMapping("/products")
    public String showProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "redirect:/user/products";
    }

    @GetMapping("/cart")
    public String showCart(Model model) {
        List<Order> orders = orderService.getOrdersForCurrentUser();
        model.addAttribute("orders", orders);
        return "cart";
    }

    @PostMapping("/checkout")
    public String checkout() {
        orderService.checkout();
        return "redirect:/user/cart?success";
    }
     */
}
