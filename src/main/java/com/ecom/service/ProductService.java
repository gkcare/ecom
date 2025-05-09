package com.ecom.service;

import com.ecom.entity.Product;
import com.ecom.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> searchProducts(String keyword){
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }
}
