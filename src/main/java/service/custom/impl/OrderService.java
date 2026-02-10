package service.custom.impl;

import model.Order;

import java.util.List;

public interface OrderService {
    boolean addOrder(Order order);
    boolean updateOrder(Order order);
    boolean deleteOrder(String id);
    Order searchOrderById(String id);

    List<Order> getAll();
}
