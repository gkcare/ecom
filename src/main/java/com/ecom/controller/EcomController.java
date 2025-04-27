package com.ecom.controller;

import com.ecom.entity.Order;
import com.ecom.entity.Product;
import com.ecom.request.OrderRequest;
import com.ecom.service.OrderService;
import com.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class EcomController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String keyword){
        return productService.searchProducts(keyword);
    }

    @PostMapping("/placeOrder")
    public Order placeOrder(@RequestBody OrderRequest orderRequest){
        return orderService.placeOrder(orderRequest);
    }

    @GetMapping("/recent/{customerId}/{count}")
    public List<Order> getRecentOrder(@PathVariable("customerId") Integer customerId,@PathVariable("count") int count){
        return orderService.getRecentOrder(customerId,count);
    }


}
