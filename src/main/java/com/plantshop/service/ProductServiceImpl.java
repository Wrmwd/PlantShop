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
    public void deleteProductById(Long id){
        productRepo.deleteById(id);
    }
    @Override
    public Product saveProduct(Product product) {
        product.setName(product.getName());
        product.setDescription(product.getDescription());
        product.setImage(product.getImage());
        product.setQuantity(product.getQuantity());
        product.setPrice(product.getPrice());

        return productRepo.save(product);
    }
    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
    @Override
    public Product getProductById(Long id) {
        return productRepo.findById(id).get();
    }

    @Override
    public Product editProduct(Product product, Long id) {
        Product oldProduct = productRepo.findById(id).get();
        oldProduct.setName(product.getName());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setImage(product.getImage());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setQuantity(product.getQuantity());

        return productRepo.save(oldProduct);
    }
}
