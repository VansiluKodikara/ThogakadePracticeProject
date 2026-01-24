package controller.order;

import model.Customer;
import model.Item;
import model.Order;

import java.util.Date;
import java.util.List;

public interface OrderService {
    boolean addOrder(Order order);
    boolean updateOrder(Order order);
    boolean deleteOrder(String id);
    Order searchOrderById(String id);

    List<Order> getAll();
}
