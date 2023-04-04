package com.banxedap.cdio3.Services;

import com.banxedap.cdio3.Repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
}
