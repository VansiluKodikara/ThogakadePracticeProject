package controller.order;

import db.DBConnection;
import model.Order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService{
    public boolean addOrder(Order order){
        try {

            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement("INSERT INTO orders VALUES(?,?,?)");

            psTm.setString(1, order.getOrderId());
            psTm.setObject(2, order.getOrderDate());
            psTm.setString(3, order.getCustId());

            return psTm.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateOrder(Order order){
        return false;
    }

    public boolean deleteOrder(String id){
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement("DELETE FROM orders WHERE OrderID=?");
            psTm.setString(1,id);

            return psTm.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Order searchOrderById(String id) {
        try {

            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTM = connection.prepareStatement("SELECT * FROM orders WHERE OrderID = ?");

            psTM.setString(1, id);
            ResultSet resultSet = psTM.executeQuery();

            resultSet.next();

            return new Order(
                    resultSet.getString(1),
                    resultSet.getDate(2).toLocalDate(),
                    resultSet.getString(3)
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Order> getAll(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");

            ArrayList<Order> orderArrayList = new ArrayList<>();

            while (resultSet.next()) {
                orderArrayList.add(
                        new Order(
                                resultSet.getString(1),
                                resultSet.getDate(2).toLocalDate(),
                                resultSet.getString(3)
                        )
                );
            }

            return orderArrayList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
