package org.example;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.* ;
public class ConnectSQLite {
 
  
void initSqlite(){
    try {
        DriverManager.registerDriver(new org.sqlite.JDBC());//启动驱动//jdbc:sqlite:是协议
        
        
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");// 连接到 SQLite 数据库文件
        Statement statement = connection.createStatement();
        // 创建表
        statement.execute("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, name TEXT, password TEXT)");
        statement.execute("CREATE TABLE IF NOT EXISTS manager (id INTEGER PRIMARY KEY, name TEXT, password TEXT)");
        statement.execute("CREATE TABLE IF NOT EXISTS commodity (id INTEGER PRIMARY KEY, name TEXT, information TEXT)");
        statement.execute("CREATE TABLE IF NOT EXISTS shoppingCart (id INTEGER PRIMARY KEY, commodity TEXT)");
        statement.execute("CREATE TABLE IF NOT EXISTS shoppingHistory (id INTEGER PRIMARY KEY, history TEXT)");
        statement.execute("CREATE TABLE IF NOT EXISTS client (id INTEGER PRIMARY KEY, name TEXT,information TEXT,manager TEXT)");
        
        connection.close();// 关闭结果集、语句和连接
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("初始化失败");
    }
}
void showCommodity(){
   try{
    
    Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");// 连接到 SQLite 数据库文件
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM commodity");
    while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String imformation = resultSet.getString("information");
        System.out.println("商品ID: " + id + ", 商品名称: " + name + ", 商品介绍: " + imformation);
    }
     resultSet.close();
     statement.close();
     connection.close();
}
  catch(SQLException e){
    e.printStackTrace();
    System.out.println("展示商品失败");
  }
}
void insertCommodity(String name,String information){
try {
   
    Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");// 连接到 SQLite 数据库文件
   Statement statement = connection.createStatement();
    String query="INSERT INTO commodity ( name , information ) VALUES ('"+name+" ' , ' "+information+"')";
    //System.out.println(query);
    statement.executeUpdate(query);
   
    statement.close();
    connection.close();

} 
catch (SQLException e) {
    e.printStackTrace();
    System.out.println("添加商品失败");
}
}

void delete(String tableName,int id){
    try {
Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");// 连接到 SQLite 数据库文件
Statement statement = connection.createStatement();
String ID= Integer.toString(id);
statement.executeUpdate("DELETE FROM "  +tableName+  " WHERE id = "+ID);
      
    statement.close();
    connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public void searchCommodity(String keyword) {
    try {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");
        Statement statement = connection.createStatement();
        
        String query1 = "SELECT * FROM commodity WHERE name LIKE '%" + keyword + "%'";
        ResultSet resultSet1 = statement.executeQuery(query1);
       // String query2 = "SELECT * FROM commodity WHERE information LIKE '%" + keyword + "%'";
       // ResultSet resultSet2 = statement.executeQuery(query2);
        
        List<ResultSet> resultSets = new ArrayList<>();
        resultSets.add(resultSet1);
       // resultSets.add(resultSet2);
        
        System.out.println("找到以下商品：");
        for (ResultSet resultSet : resultSets) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String information = resultSet.getString("information");
                System.out.println("商品ID: " + id + ", 商品名称: " + name + ", 商品介绍: " + information);
            }
            resultSet.close();
        }
        
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("搜索商品失败");
    }
}
}
/*void modify(){
    try {
Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");// 连接到 SQLite 数据库文件
Statement statement = connection.createStatement();

      //  resultSet.close();
    statement.close();
    connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }


}*/

 /*void aa(){
        try {
    Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");// 连接到 SQLite 数据库文件
   Statement statement = connection.createStatement();

          //  resultSet.close();
        statement.close();
        connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    /*  // 插入数据
        statement.executeUpdate("INSERT INTO users (name, email) VALUES ('John Doe', 'john@example.com')");

        // 查询数据
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email);
        }

        // 更新数据
        statement.executeUpdate("UPDATE users SET name = 'Jane Doe' WHERE id = 1");

        // 删除数据
        statement.executeUpdate("DELETE FROM users WHERE id = 1");

        // 关闭结果集、语句和连接
        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }*/






