package service.custom.impl;

import model.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemService {
    boolean addItem(Item item) throws SQLException;
    boolean updateItem(Item item);
    boolean deleteItem(String id);
    Item searchItemById(String id);

    List<Item> getAll();
}
