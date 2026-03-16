package service.custom.impl;

import model.Customer;
import service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface CustomerService extends SuperService {
    boolean addCustomer(Customer customer) throws SQLException;
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(String id);
    Customer searchCustomerById(String id) throws SQLException;

    List<Customer> getAll();
}
