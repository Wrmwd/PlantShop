package com.plantshop.controller;

import com.plantshop.model.Product;
import com.plantshop.model.User;
import com.plantshop.repository.ProductRepository;
import com.plantshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/")
public class ProductController {
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
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
    @GetMapping("/{id}/edit")
    public String productEdit(@PathVariable(value = "id") Long id, Model model){
        if(!productRepo.existsById(id)){
            return "redirect:/admin/";
        }
        Optional<Product> product = productRepo.findById(id);
        ArrayList<Product> res =new ArrayList<>();
        product.ifPresent(res::add);
        model.addAttribute("product", res);
        return "admin/product-edit";
    }
    @PostMapping("/{id}/edit")
        public String productPostUpdate(@PathVariable(value = "id") Long id, @RequestParam String name,
                                        @RequestParam String description, @RequestParam String image,
                                        @RequestParam Double price, @RequestParam int quantity){
        //Product oldProduct = productService.getProductById(id);
        Product product = new Product(name, description, image, price, quantity);
        productService.editProduct(product, id);
        return "redirect:/admin/";
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
