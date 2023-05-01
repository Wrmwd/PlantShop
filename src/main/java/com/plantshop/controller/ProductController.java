package com.plantshop.controller;

import com.plantshop.model.Product;
import com.plantshop.repository.ProductRepository;
import com.plantshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/")
public class ProductController {
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private ProductService productService;
    @GetMapping("/")
    public String getAllProducts(Model model){
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/home";
    }

    @GetMapping("/addNewProduct")
    public String addNewProduct(){
        return "admin/add_product";
    }
    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute Product product, HttpSession session){
        System.out.println(product);
        Product productDtls = productService.saveProduct(product);
        if(productDtls!=null){
            session.setAttribute("msg", "Товар успешно добавлен");
        }
        else {
            session.setAttribute("msg","Ошибка на сервере");
        }
        return "redirect:/admin/addNewProduct";
    }
}
