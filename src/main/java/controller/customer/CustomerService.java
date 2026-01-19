package controller.customer;

import model.Customer;

import java.util.List;

public interface CustomerService {
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(String id);
    Customer searchCustomerById(String id);

    List<Customer> getAll();
}
