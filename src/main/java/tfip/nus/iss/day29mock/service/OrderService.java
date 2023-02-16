package tfip.nus.iss.day29mock.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfip.nus.iss.day29mock.model.Order;
import tfip.nus.iss.day29mock.repository.OrderRepo;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    public void insertOrder(Order order) {
        String commentId = UUID.randomUUID().toString().substring(0, 8);
        order.setOrderId(commentId);
        orderRepo.insertOrder(order);
    }

}
