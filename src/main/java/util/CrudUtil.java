package util;

import db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
    public static <Type>Type execute(String sql,Object...args) throws SQLException{
        PreparedStatement preparedStatement= DBConnection.getInstance().getConnection().prepareStatement(sql);

        for (int i=0; i<args.length; i++){
            preparedStatement.setObject((i+1),args[i]);
        }

        if (sql.startsWith("SELECT")||sql.startsWith("select")){
            return (Type) preparedStatement.executeQuery();
        }

        return (Type) (Boolean) (preparedStatement.executeUpdate()>0);

    }
}
