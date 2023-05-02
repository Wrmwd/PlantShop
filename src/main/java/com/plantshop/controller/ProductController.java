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
    public String getAllProducts(Model model, @RequestParam(name="method",required = false) String method,
                                 @RequestParam(name = "id", required = false) Long id, HttpSession session){

        if(method!=null && id!=null && method.equals("delete")){
            System.out.println("Deleting Product by Id: "+id);
            productService.deleteProductById(id);
            session.setAttribute("msg", "Товар успешно удален.");
            model.addAttribute("products", productService.getAllProducts());
            return "redirect:/admin/";
        }
        model.addAttribute("products", productService.getAllProducts());
        return "/admin/home";
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
    /*
    @GetMapping("/editProduct/{id}")
    public String editProduct(@PathVariable Long id, Model m){
        Product product = productService.getProductById(id);
        m.addAttribute("product", product);
        return "edit_product";
    }
    @PostMapping("/updateProduct")
    public String editProduct(@ModelAttribute Product product, HttpSession session){
        productService.saveProduct(product);
        session.setAttribute("msg", "Изменения сохранены");
        return "redirect:/admin/home";
    }
     @GetMapping("/deleteProd/{id}")
    public String deleteProduct(@PathVariable("id") Long id, HttpSession session){
         productService.deleteProductById(id);
         return "redirect:/admin/home";
    }
    */
}
