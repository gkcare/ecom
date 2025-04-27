package com.ecom.service;

import com.ecom.entity.Customer;
import com.ecom.entity.Order;
import com.ecom.entity.OrderProduct;
import com.ecom.entity.Product;
import com.ecom.repository.CustomerRepository;
import com.ecom.repository.OrderProductRepository;
import com.ecom.repository.OrderRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.request.OrderRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private CustomerRepository customerRepository;

   /*
    first check customer is exit or not
    if exist then create an order save to get id
    */

    @Transactional
    public Order placeOrder(OrderRequest orderRequest){

        Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // create a new order
        Order order =new Order(customer, LocalDateTime.now(),"PENDING");
        orderRepository.save(order);

        List<OrderProduct> orderProducts=orderRequest.getProducts().stream()
                .map(item->{
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));

                    if(product.getQuantity() < item.getQuantity()){
                        throw new RuntimeException("Insufficient Stock for product: "+product.getName());
                    }

                    product.setQuantity(product.getQuantity()-item.getQuantity());
                    productRepository.save(product);

                    return new OrderProduct(order,product, item.getQuantity(), product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

                }).toList();

        orderProductRepository.saveAll(orderProducts);

        return order;
    }

    public List<Order> getRecentOrder(Integer customerId,int limit){
        Pageable pageable= PageRequest.of(0,limit);
        return orderRepository.findByCustomerIdOrderByCreatedDesc(customerId, pageable);
    }

}


