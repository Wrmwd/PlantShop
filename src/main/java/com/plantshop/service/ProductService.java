package com.plantshop.service;

import com.plantshop.model.Product;
import java.util.List;

public interface ProductService {
    public Product saveProduct(Product product);
    public List<Product> getAllProducts();
    public Product getProductById(Integer id);
    public String deleteProduct(Integer id);
    public Product editProduct(Product product, Integer id);

}
