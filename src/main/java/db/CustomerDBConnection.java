package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CustomerDBConnection {
    private static CustomerDBConnection instance;
    private Connection connection;
    private CustomerDBConnection() throws SQLException {
         connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade_customer", "root", "12345");




    }
    public Connection getConnection(){
        return connection;

    }

    public static CustomerDBConnection getInstance() throws SQLException {
        return instance==null?instance=new CustomerDBConnection():instance;

    }

}
