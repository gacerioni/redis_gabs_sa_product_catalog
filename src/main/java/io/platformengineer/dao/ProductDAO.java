package io.platformengineer.dao;

import io.platformengineer.model.Product;

import java.util.List;

public interface ProductDAO {
    Product createProduct(Product product);
    Product getProductById(Long id);
    void updateProduct(Product product);
    void deleteProduct(Long id);
    List<Product> findAll();
}
