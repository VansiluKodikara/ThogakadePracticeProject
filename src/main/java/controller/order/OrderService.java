package controller.order;

import model.Customer;
import model.Item;
import model.Order;

import java.util.Date;
import java.util.List;

public interface OrderService {
    boolean addCustomer(Order order);
    boolean updateCustomer(Order order);
    boolean deleteCustomer(String id);
    Order searchCustomerById(String id);

    List<Order> getAll();
}
