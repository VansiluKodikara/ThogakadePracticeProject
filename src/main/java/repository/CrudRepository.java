package repository;

import java.sql.SQLException;
import java.util.List;

public interface CrudRepository<Type,ID> extends SuperRepository {
    boolean create(Type type) throws SQLException;
    boolean update(Type type);
    boolean deleteById(ID id);
    Type getById(ID id) throws SQLException;
    List<Type> getAll() throws SQLException;
}
