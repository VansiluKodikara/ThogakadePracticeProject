package repository.customer.impl;

import model.Item;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemRepositoryImpl {
    public boolean create(Item item) throws SQLException{
        return CrudUtil.execute("INSERT INTO item VALUES(?,?,?,?,?)",
                item.getItemCode(),
                item.getDescription(),
                item.getPackSize(),
                item.getUnitPrice(),
                item.getQtyOnHand()
                );
    }

    public boolean update(Item item){
        return false;
    }

    public boolean deleteById(String id){
        return false;
    }
/*
    public Item getById(String code) throws SQLException{

        ResultSet resultSet=CrudUtil.execute("SELECT * FROM item WHERE ItemCode=?",code);

        resultSet.next();
        //Item item = new Item();

        item.setItemCode(resultSet.getString(1));
        item.setDescription(resultSet.getString(2));
        item.setPackSize(resultSet.getString(3));
        item.setUnitPrice(resultSet.getDouble(4));
        item.setPackSize(resultSet.getString(5));

        return item;
    }

 */
}
