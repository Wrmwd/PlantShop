package com.plantshop.service;

import com.plantshop.model.Product;
import com.plantshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepo;
    @Override
    public Product saveProduct(Product product) {
        return productRepo.save(product);
    }
    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
    @Override
    public Product getProductById(Integer id) {
        return productRepo.findById(id).get();
    }
    @Override
    public String deleteProduct(Integer id) {
        Product product = productRepo.findById(id).get();
        if(product!=null){
            productRepo.delete(product);
            return "Продукт успешно удален.";
        }
        return "Ошибка :(";
    }
    @Override
    public Product editProduct(Product product, Integer id) {
        Product oldProduct = productRepo.findById(id).get();
        oldProduct.setName(product.getName());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setQuantity(product.getQuantity());

        return productRepo.save(oldProduct);
    }
}
