package org.example.kiosk.spring.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.example.kiosk.spring.api.controller.order.request.OrderCreateRequest;
import org.example.kiosk.spring.api.service.order.response.OrderResponse;
import org.example.kiosk.spring.domain.order.Order;
import org.example.kiosk.spring.domain.order.OrderRepository;
import org.example.kiosk.spring.domain.product.Product;
import org.example.kiosk.spring.domain.product.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        Order order = Order.create(products, registeredDateTime);
        Order savedOrder = orderRepository.save(order);
        return OrderResponse.of(savedOrder);
    }

}