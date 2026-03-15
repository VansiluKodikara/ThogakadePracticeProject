package repository.customer;

import model.Item;
import repository.CrudRepository;

import java.sql.SQLException;
import java.util.List;

public interface ItemRepository extends CrudRepository<Item,String> {
    List<String> getItemCodes() throws SQLException;
}
