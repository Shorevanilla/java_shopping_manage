package org.example;
//import java.util.List;
//import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.* ;
public class UserOperation {
   

Boolean accountNameCheck(String name){
    try {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");
        Statement statement = connection.createStatement();
        
        // 检查是否已存在具有相同名称的用户
        String queryCheck = "SELECT * FROM user WHERE name = '" + name + "' ";
        ResultSet resultSet = statement.executeQuery(queryCheck);
        if (resultSet.next()) {
            System.out.println("用户名重复");
            resultSet.close();
            statement.close();
            connection.close();
            return false;
        } else{
            resultSet.close();
            statement.close();
            connection.close();
            return true;
        } 
        
        
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("用户名重复性查询失败");
        return false;
    }
}
Boolean isPasswordSame(String password1,String password2){
   if(password1!=password2){
    return false;
   }
   else return true;
   
}
Boolean isPasswordLengthValid(String password){
    if(password.length()<6){
     return false;
    }
    else return true;
 }

void modifySelfPassword(String name,String newpassword){
    try {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");// 连接到 SQLite 数据库文件
        Statement statement = connection.createStatement();
        String query="UPDATE user SET password = '"+newpassword+"' WHERE name = ' "+name+" ' ";
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


/* 
public Boolean register(String name, String password) {
    try {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");
        Statement statement = connection.createStatement();
        
        // 插入用户信息到 user 表
        String queryInsertUser = "INSERT INTO user (name, password) VALUES ('" + name + "', '" + password + "')";
        statement.executeUpdate(queryInsertUser);
        
        // 获取刚插入的用户的 ID
        ResultSet generatedKeys = statement.getGeneratedKeys();
        int userId = -1;
        if (generatedKeys.next()) {
            userId = generatedKeys.getInt(1);
        }
        generatedKeys.close();
        
        if (userId != -1) {
            // 检查该用户的购物车是否已存在
            String queryCheckShoppingCart = "SELECT * FROM shoppingCart WHERE id = " + userId;
            ResultSet resultSet = statement.executeQuery(queryCheckShoppingCart);
            
            if (!resultSet.next()) {
                // 创建对应用户的购物车行
                String queryInsertShoppingCart = "INSERT INTO shoppingCart (id, commodity) VALUES (" + userId + ", '')";
                statement.executeUpdate(queryInsertShoppingCart);
                
                // 创建对应用户的购物历史行
                String queryInsertShoppingHistory = "INSERT INTO shoppingHistory (id, history) VALUES (" + userId + ", '')";
                statement.executeUpdate(queryInsertShoppingHistory);
                
                System.out.println("用户注册成功");
                resultSet.close();
                statement.close();
                connection.close();
                return true;
            } else {
                System.out.println("该用户的购物车已存在");
                resultSet.close();
                statement.close();
                connection.close();
                return false;
            }
        } else {
            System.out.println("获取用户ID失败");
            statement.close();
            connection.close();
            return false;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("用户注册失败");
        return false;
    }
}
*/
public Boolean register(String name, String password) {
    try {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");
        Statement statement = connection.createStatement();
        
        // 插入用户信息到 user 表
        String queryInsertUser = "INSERT INTO user (name, password) VALUES ('" + name + "', '" + password + "')";
        statement.executeUpdate(queryInsertUser);
        
        // 获取刚插入的用户的 ID
        ResultSet generatedKeys = statement.getGeneratedKeys();
        int userId = -1;
        if (generatedKeys.next()) {
            userId = generatedKeys.getInt(1);
        }
        generatedKeys.close();
        
        if (userId != -1) {
            // 创建唯一对应的购物车表和购物历史表
            String shoppingCartTableName = "shoppingCart_" + userId;
            String shoppingHistoryTableName = "shoppingHistory_" + userId;
            
            // 创建购物车表
            String queryCreateShoppingCartTable = "CREATE TABLE IF NOT EXISTS " + shoppingCartTableName + " (id INTEGER PRIMARY KEY, commodityID INTEGER, amount INTEGER)";
            statement.execute(queryCreateShoppingCartTable);
            
            // 创建购物历史表
            String queryCreateShoppingHistoryTable = "CREATE TABLE IF NOT EXISTS " + shoppingHistoryTableName + " (id INTEGER PRIMARY KEY, commodityID INTEGER, payMentHistory TEXT)";
            statement.execute(queryCreateShoppingHistoryTable);
            
            System.out.println("用户注册成功");
            
            statement.close();
            connection.close();
            
            return true;
        } else {
            System.out.println("获取用户ID失败");
            statement.close();
            connection.close();
            return false;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("用户注册失败");
        return false;
    }
}


 public Boolean addCommodity(int userID, int commodityID) {
    try {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");
        Statement statement = connection.createStatement();
        
        
           // resultSet.close();
        statement.close();
        connection.close();
            return false;
        }
        
        
     catch (SQLException e) {
        e.printStackTrace();
        System.out.println("购物车添加商品失败");
        return false;
    }
}

void  showShoppingCart(int userID) {
    try {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");
        Statement statement = connection.createStatement();
        
        // 找到该用户对应的购物车
        String queryCheck = "SELECT * FROM shoppingCart WHERE id = " + userID;
        ResultSet resultSet = statement.executeQuery(queryCheck);
        
        if (resultSet.next()) {
            String commodity = resultSet.getString("commodity");
            
            // 如果购物车已经有商品，添加商品 ID，并使用逗号分隔
            if (!commodity.isEmpty()) {
                //System.out.println("购物车");
              String array[]=commodity.split(",");
                for(int i=0;i<array.length;i++){
               resultSet = statement.executeQuery("SELECT * FROM commodity WHERE id="+array[i]);
             if (resultSet.next()) {
             String name = resultSet.getString("name");
             String information = resultSet.getString("information");
              System.out.println("商品ID: " + array[i] +   ", 商品名称: " + name + ", 商品介绍: " + information);
                            }
                   }
      
          }
            } else {  
                 resultSet.close();
                 statement.close();
                connection.close();
                System.out.println("购物车为空");
            }
            
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("购物车查看失败");
        
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








