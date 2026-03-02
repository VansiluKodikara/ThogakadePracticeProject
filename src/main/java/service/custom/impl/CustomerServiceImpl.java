package service.custom.impl;

import db.DBConnection;
import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService{

    @Override
    public boolean addCustomer(Customer customer){
        try {
            Connection connection=DBConnection.getInstance().getConnection();

            PreparedStatement preparedStatement=connection.prepareStatement("INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)");

            preparedStatement.setString(1, customer.getId());
            preparedStatement.setString(2, customer.getTitle());
            preparedStatement.setString(3, customer.getName());
            preparedStatement.setObject(4, customer.getDob());
            preparedStatement.setDouble(5, customer.getSalary());
            preparedStatement.setString(6, customer.getAddress());
            preparedStatement.setString(7, customer.getCity());
            preparedStatement.setString(8, customer.getProvince());
            preparedStatement.setString(9, customer.getPostalCode());

            return preparedStatement.executeUpdate()>0;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public boolean deleteCustomer(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement("DELETE FROM customer WHERE CustID=?");
            psTm.setString(1,id);

            return psTm.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Customer searchCustomerById(String id) {
        try {

            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTM = connection.prepareStatement("SELECT * FROM customer WHERE CustID = ?");

            psTM.setString(1,id);
            ResultSet resultSet = psTM.executeQuery();

            resultSet.next();

            return new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4).toLocalDate(),
                    resultSet.getDouble(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9)
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Customer> getAll() {
        try {
            Connection connection=DBConnection.getInstance().getConnection();

            Statement statement=connection.createStatement();

            ResultSet resultSet=statement.executeQuery("SELECT * FROM Customer");

            ArrayList<Customer> customerArrayList=new ArrayList<>();

            while (resultSet.next()){
                customerArrayList.add(
                        new Customer(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getDate(4).toLocalDate(),
                                resultSet.getDouble(5),
                                resultSet.getString(6),
                                resultSet.getString(7),
                                resultSet.getString(8),
                                resultSet.getString(9)
                        )
                );
            }
            return customerArrayList;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
