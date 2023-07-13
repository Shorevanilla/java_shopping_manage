package org.example;
//import java.util.List;
//import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.* ;
public class Login {
    Boolean accountVerify(String tableName,String name,String password){
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");
            Statement statement = connection.createStatement();
            String queryCheck = "SELECT * FROM "+ tableName+" WHERE name = '" + name + "' AND password = '" + password + "'";
            ResultSet resultSet = statement.executeQuery(queryCheck);
            if (resultSet.next()) {
               resultSet.close();
            statement.close();
            connection.close();
                return true;
            } else {
                resultSet.close();
                statement.close();
                connection.close();
                return false;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    } 
}
