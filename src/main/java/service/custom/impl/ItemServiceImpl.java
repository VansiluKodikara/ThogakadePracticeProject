package service.custom.impl;

import db.DBConnection;
import model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemServiceImpl implements ItemService{
    public boolean addItem(Item item) {
        try {

            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement("INSERT INTO item VALUES(?,?,?,?,?)");

            psTm.setString(1, item.getItemCode());
            psTm.setString(2, item.getDescription());
            psTm.setString(3, item.getPackSize());
            psTm.setObject(4, item.getUnitPrice());
            psTm.setDouble(5, item.getQtyOnHand());

            return psTm.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public boolean updateItem(Item item) {
        return false;

    }

    @Override
    public boolean deleteItem(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement("DELETE FROM item WHERE ItemCode=?");
            psTm.setString(1, id);

            return psTm.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Item searchItemById(String id) {
        try {

            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTM = connection.prepareStatement("SELECT * FROM item WHERE ItemCode = ?");

            psTM.setString(1, id);
            ResultSet resultSet = psTM.executeQuery();

            resultSet.next();

            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5)
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Item> getAll() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM item");

            ArrayList<Item> itemArrayList = new ArrayList<>();

            while (resultSet.next()) {
                itemArrayList.add(
                        new Item(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getDouble(4),
                                resultSet.getInt(5)
                        )
                );
            }

            return itemArrayList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
