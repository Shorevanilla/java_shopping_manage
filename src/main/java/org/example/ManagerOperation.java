package org.example;
//import java.util.List;
//import java.util.ArrayList;
import java.util.UUID;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.* ;
public class ManagerOperation {
void modifySelfPassword(String name,String newpassword){
    try {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");// 连接到 SQLite 数据库文件
        Statement statement = connection.createStatement();
        String query="UPDATE manager SET password = '"+newpassword+"' WHERE name = ' "+name+" ' ";
        statement.executeUpdate(query);
        System.out.println("成功修改密码，请重新登入");
        //System.out.println(query);
              //  resultSet.close();
            statement.close();
            connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("修改密码失败");
            }
}


String resetUserPassword(String name){
    try {
        String password = UUID.randomUUID().toString();
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");// 连接到 SQLite 数据库文件
        Statement statement = connection.createStatement();
        String query="UPDATE user SET password = '"+password+"' WHERE name = '"+name+"'";
        statement.executeUpdate(query);
        //System.out.println("成功重置用户 "+name+" 密码为:");
        //System.out.println(password);
              //  resultSet.close();
            statement.close();
            connection.close();
            return password;
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("重置密码失败");
                return null;
            }
}  

void showClient(){
    try{
     
     Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");// 连接到 SQLite 数据库文件
     Statement statement = connection.createStatement();
     ResultSet resultSet = statement.executeQuery("SELECT * FROM client");
     while (resultSet.next()) {
         int id = resultSet.getInt("id");
         String name = resultSet.getString("name");
         String imformation = resultSet.getString("information");
         System.out.println("客户ID: " + id + ", 客户名称: " + name + ", 客户信息: " + imformation);
     }
      resultSet.close();
      statement.close();
      connection.close();
 }
   catch(SQLException e){
     e.printStackTrace();
     System.out.println("未能成功列出所有客户信息");
   }
 }
 public void insertClient(String name, String information) {
    try {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");
        Statement statement = connection.createStatement();
        
        // 检查是否已存在具有相同名称和信息的商品
        String queryCheck = "SELECT * FROM client WHERE name = '" + name + "' ";
        ResultSet resultSet = statement.executeQuery(queryCheck);
        if (resultSet.next()) {
            System.out.println("该客户已存在");
        } else {
            // 插入新客户
            String queryInsert = "INSERT INTO client (name, information) VALUES ('" + name + "', '" + information + "')";
            statement.executeUpdate(queryInsert);
            System.out.println("客户添加成功");
        }
        
        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("添加客户失败");
    }
}

void searchClient(int id){//根据id唯一搜索
    try{
     Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");// 连接到 SQLite 数据库文件
     Statement statement = connection.createStatement();
     ResultSet resultSet = statement.executeQuery("SELECT * FROM client WHERE id="+Integer.toString(id));
     if (resultSet.next()) {
        String name = resultSet.getString("name");
        String information = resultSet.getString("information");
        System.out.println("根据客户ID: " + id + ", 找到客户：\n客户名称: " + name + ", 客户信息: " + information);
    } else {
        System.out.println("找不到 id "+Integer.toString(id)+"  对应的客户");
    }
      resultSet.close();
      statement.close();
      connection.close();
 }
   catch(SQLException e){
     e.printStackTrace();
     System.out.println("查找客户失败");
   }
 }
public void searchClient(String keyword) {//根据包含的搜索关键字搜索所有相关商品
    try {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");
        Statement statement = connection.createStatement();
        
        String query = "SELECT * FROM client WHERE name LIKE '%" + keyword + "%' OR information LIKE '%" + keyword + "%'";
        ResultSet resultSet = statement.executeQuery(query);
        
        System.out.println("搜索与 "+keyword+" 有关的客户结果如下：");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String information = resultSet.getString("information");
            System.out.println("客户ID: " + id + ", 客户名称: " + name + ", 客户信息: " + information);
        }
        
        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("搜索客户失败");
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

public void insertCommodity(String name, String information) {
    try {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");
        Statement statement = connection.createStatement();
        
        // 检查是否已存在具有相同名称和信息的商品
        String queryCheck = "SELECT * FROM commodity WHERE name = '" + name + "' AND information = '" + information + "'";
        ResultSet resultSet = statement.executeQuery(queryCheck);
        if (resultSet.next()) {
            System.out.println("该商品已存在");
        } else {
            // 插入新商品
            String queryInsert = "INSERT INTO commodity (name, information) VALUES ('" + name + "', '" + information + "')";
            statement.executeUpdate(queryInsert);
            System.out.println("商品添加成功");
        }
        
        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
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
void searchCommodity(int id){//根据id唯一搜索
    try{
     Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");// 连接到 SQLite 数据库文件
     Statement statement = connection.createStatement();
     ResultSet resultSet = statement.executeQuery("SELECT * FROM commodity WHERE id="+Integer.toString(id));
     if (resultSet.next()) {
        String name = resultSet.getString("name");
        String information = resultSet.getString("information");
        System.out.println("根据商品ID: " + id + ", 找到商品：\n商品名称: " + name + ", 商品介绍: " + information);
    } else {
        System.out.println("找不到该 id "+Integer.toString(id)+"  对应的商品");
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
public void searchCommodity(String keyword) {//根据包含的搜索关键字搜索所有相关商品
    try {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");
        Statement statement = connection.createStatement();
        
        String query = "SELECT * FROM commodity WHERE name LIKE '%" + keyword + "%' OR information LIKE '%" + keyword + "%'";
        ResultSet resultSet = statement.executeQuery(query);
        
        System.out.println("找到以下和 "+keyword+" 有关的商品：");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String information = resultSet.getString("information");
            System.out.println("商品ID: " + id + ", 商品名称: " + name + ", 商品介绍: " + information);
        }
        
        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("搜索商品失败");
    }
}


void modifyCommodity(String name, int id){
    try {
Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");// 连接到 SQLite 数据库文件
Statement statement = connection.createStatement();
String query="UPDATE commodity SET name = '"+name+"' WHERE id = "+Integer.toString(id);
statement.executeUpdate(query);
//System.out.println(query);
      //  resultSet.close();
    statement.close();
    connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("修改商品信息失败");
    }
}


}
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






