package repository.customer;

import model.Order;
import repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order,String> {
}
