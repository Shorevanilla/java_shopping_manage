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
            String queryCreateShoppingCartTable = "CREATE TABLE IF NOT EXISTS " + shoppingCartTableName + " (id INTEGER PRIMARY KEY, commodityID INTEGER, amount INTEGER,totalPrice REAL)";
            statement.execute(queryCreateShoppingCartTable);
            
            // 创建购物历史表
            String queryCreateShoppingHistoryTable = "CREATE TABLE IF NOT EXISTS " + shoppingHistoryTableName + " (id INTEGER PRIMARY KEY, commodityInformation TEXT, payment REAL)";
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


public Boolean addCommodity(int userID, int commodityID, int addAmount) {
    try {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");
        Statement statement = connection.createStatement();
        
        // 检查购物车中是否已存在相同的商品
        String queryCheck = "SELECT * FROM shoppingCart_" + String.valueOf(userID) + " WHERE commodityID = " + String.valueOf(commodityID);
        ResultSet resultSet = statement.executeQuery(queryCheck);
        
        if (resultSet.next()) {
            // 更新已存在的商品行的数量和总价
            int currentAmount = resultSet.getInt("amount");
            int newAmount = currentAmount + addAmount;
            String updateQuery = "UPDATE shoppingCart_" + String.valueOf(userID) + " SET amount = " + newAmount + ", totalPrice = " + newAmount + " * (SELECT price FROM commodity WHERE id = " + String.valueOf(commodityID) + ") WHERE commodityID = " + String.valueOf(commodityID);
            statement.executeUpdate(updateQuery);
            System.out.println("购物车商品数量和总价更新成功");
        } else {
            // 新增一行
            String insertQuery = "INSERT INTO shoppingCart_" + String.valueOf(userID) + " (commodityID, amount, totalPrice) VALUES (" + String.valueOf(commodityID) + ", " + String.valueOf(addAmount) + ", " + addAmount + " * (SELECT price FROM commodity WHERE id = " + String.valueOf(commodityID) + "))";
            statement.executeUpdate(insertQuery);
            System.out.println("商品成功添加到购物车");
        }
        
        resultSet.close();
        statement.close();
        connection.close();
        
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("加入购物车失败");
        return false;
    }
}

public Boolean addCommodity(int userID, int commodityID) {
    try {
        int addAmount=1;
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");
        Statement statement = connection.createStatement();
        
        // 检查购物车中是否已存在相同的商品
        String queryCheck = "SELECT * FROM shoppingCart_" + String.valueOf(userID) + " WHERE commodityID = " + String.valueOf(commodityID);
        ResultSet resultSet = statement.executeQuery(queryCheck);
        
        if (resultSet.next()) {
            // 更新已存在的商品行的数量和总价
            int currentAmount = resultSet.getInt("amount");
            int newAmount = currentAmount + addAmount;
            String updateQuery = "UPDATE shoppingCart_" + String.valueOf(userID) + " SET amount = " + newAmount + ", totalPrice = " + newAmount + " * (SELECT price FROM commodity WHERE id = " + String.valueOf(commodityID) + ") WHERE commodityID = " + String.valueOf(commodityID);
            statement.executeUpdate(updateQuery);
            System.out.println("购物车商品数量增加成功");
        } else {
            // 新增一行
            String insertQuery = "INSERT INTO shoppingCart_" + String.valueOf(userID) + " (commodityID, amount, totalPrice) VALUES (" + String.valueOf(commodityID) + ", " + String.valueOf(addAmount) + ", " + addAmount + " * (SELECT price FROM commodity WHERE id = " + String.valueOf(commodityID) + "))";
            statement.executeUpdate(insertQuery);
            System.out.println("商品成功添加到购物车");
        }
        
        resultSet.close();
        statement.close();
        connection.close();
        
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("加入购物车失败");
        return false;
    }
}
public Boolean removeCommodity(int userID, int commodityID, int removeAmount) {
    try {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");
        Statement statement = connection.createStatement();
        
        // 检查购物车中是否已存在相同的商品
        String queryCheck = "SELECT * FROM shoppingCart_" + String.valueOf(userID) + " WHERE commodityID = " + String.valueOf(commodityID);
        ResultSet resultSet = statement.executeQuery(queryCheck);
        
        if (resultSet.next()) {
            // 更新已存在的商品行的数量和总价
            int currentAmount = resultSet.getInt("amount");
            int newAmount = currentAmount - removeAmount;
            String updateQuery = "UPDATE shoppingCart_" + String.valueOf(userID) + " SET amount = " + newAmount + ", totalPrice = " + newAmount + " * (SELECT price FROM commodity WHERE id = " + String.valueOf(commodityID) + ") WHERE commodityID = " + String.valueOf(commodityID);
            statement.executeUpdate(updateQuery);
            System.out.println("购物车商品数量和总价更新成功");
        } else {
            
            System.out.println("商品成功不在购物车");
        }
        
        resultSet.close();
        statement.close();
        connection.close();
        
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("减少购物车商品数量失败");
        return false;
    }
}
public Boolean payment(int userID, int commodityID) {
    try {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");
        Statement statement = connection.createStatement();

        // 检查购物车中是否已存在该商品
        String queryCheck = "SELECT * FROM shoppingCart_" + String.valueOf(userID) + " WHERE commodityID = " + String.valueOf(commodityID);
        ResultSet resultSet = statement.executeQuery(queryCheck);

        if (resultSet.next()) {
            // 获取商品信息和支付金额
            String queryCommodity = "SELECT * FROM commodity WHERE id = " + String.valueOf(commodityID);
            ResultSet resultSetCommodity = statement.executeQuery(queryCommodity);
            String commodityName = "";
            int amount = resultSet.getInt("amount");
            float paymentAmount = 0.0f;
            if (resultSetCommodity.next()) {
                commodityName = resultSetCommodity.getString("name");
                paymentAmount = resultSetCommodity.getFloat("price") * amount;
            }
            resultSetCommodity.close();

            // 删除已存在的商品行
            String deleteQuery = "DELETE FROM shoppingCart_" + String.valueOf(userID) + " WHERE commodityID = " + commodityID;
            statement.executeUpdate(deleteQuery);
            System.out.println("支付成功，等待卖家发货");

            // 在购物历史表中插入新行
            String shoppingHistoryTableName = "shoppingHistory_" + userID;
            String insertQuery = "INSERT INTO " + shoppingHistoryTableName + " (commodityInformation, payment) VALUES ('" + commodityName + " x " + amount + "', " + paymentAmount + ")";
            statement.executeUpdate(insertQuery);
        } else {
            System.out.println("购物车不存在该商品");
            return false;
        }

        resultSet.close();
        statement.close();
        connection.close();

        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("支付失败");
        return false;
    }
}



public Boolean deleteCommodity(int userID, int commodityID) {
    try {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");
        Statement statement = connection.createStatement();
        
        // 检查购物车中是否已存在相同的商品
        String queryCheck = "SELECT * FROM shoppingCart_" + String.valueOf(userID) + " WHERE commodityID = " + String.valueOf(commodityID);
        ResultSet resultSet = statement.executeQuery(queryCheck);
        
        if (resultSet.next()) {
            // 删除已存在的商品行
            String deleteQuery = "DELETE FROM shoppingCart_" + String.valueOf(userID) + " WHERE commodityID = " + commodityID;
            statement.executeUpdate(deleteQuery);
            System.out.println("商品已从购物车中删除");
        } else {
            System.out.println("购物车不存在该商品");
            return false;
        }
        
        resultSet.close();
        statement.close();
        connection.close();
        
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("删除购物车商品失败");
        return false;
    }
}


public void showShoppingCart(int userID) {
    try {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");
        Statement statement = connection.createStatement();
        
        // 找到该用户对应的购物车
        String queryCheck = "SELECT * FROM shoppingCart_" + String.valueOf(userID);
        ResultSet rs = statement.executeQuery(queryCheck);
        
        if (rs.next()) {
            System.out.println("购物车内容：");
            do {
                int commodityID = rs.getInt("commodityID");
                int amount = rs.getInt("amount");
                double totalPrice = (double) rs.getFloat("totalPrice");
                String queryCommodity = "SELECT * FROM commodity WHERE id = " + String.valueOf(commodityID);
                ResultSet resultSetCommodity = statement.executeQuery(queryCommodity);
                
                if (resultSetCommodity.next()) {
                    String name = resultSetCommodity.getString("name");
                    String information = resultSetCommodity.getString("information");
                    float price = resultSetCommodity.getFloat("price");
                   
                    System.out.println("商品ID: " + commodityID);
                    System.out.println("商品名称: " + name);
                    System.out.println("商品信息: " + information);
                    System.out.println("商品单价: " + price);
                    System.out.println("数量: " + amount);
                    System.out.println("总价: " + totalPrice);
                    System.out.println("---------------------------");
                }
                
                //resultSetCommodity.close();
            } while (rs.next()); // 移动到下一行
        } else {
            System.out.println("购物车为空");
        }
        
        rs.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("购物车查看失败");
    }
}

public Boolean payForOneCommodity(int userID, int commodityID) {
    try {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");
        Statement statement = connection.createStatement();
        
        // 检查购物车中是否已存在相同的商品
        String queryCheck = "SELECT * FROM shoppingCart_" + String.valueOf(userID) + " WHERE commodityID = " + String.valueOf(commodityID);
        ResultSet resultSet = statement.executeQuery(queryCheck);
        
        if (resultSet.next()) {
            // 删除已存在的商品行
            String deleteQuery = "DELETE FROM shoppingCart_" + String.valueOf(userID) + " WHERE commodityID = " + commodityID;
            
            String histryQueryCheck = "SELECT * FROM shoppingCart_" + String.valueOf(userID) + " WHERE commodityID = " + String.valueOf(commodityID);
           
            statement.executeUpdate(deleteQuery);
            
            System.out.println("商品已从购物车中删除");
        } else {
            System.out.println("购物车不存在该商品");
            return false;
        }
        
        resultSet.close();
        statement.close();
        connection.close();
        
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("删除购物车商品失败");
        return false;
    }
}
public void payOff(int userID,int id) {
    try {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");
        Statement statement = connection.createStatement();
        
        // 找到该用户对应的购物车
        String queryCheck = "SELECT * FROM shoppingCart_" + String.valueOf(userID);
        ResultSet rs = statement.executeQuery(queryCheck);
        
        if (rs.next()) {
            System.out.println("购物车内容：");
            do {
                int commodityID = rs.getInt("commodityID");
                int amount = rs.getInt("amount");
                double totalPrice = (double) rs.getFloat("totalPrice");
                String queryCommodity = "SELECT * FROM commodity WHERE id = " + String.valueOf(commodityID);
                ResultSet resultSetCommodity = statement.executeQuery(queryCommodity);
                
                if (resultSetCommodity.next()) {
                    String name = resultSetCommodity.getString("name");
                    String information = resultSetCommodity.getString("information");
                    float price = resultSetCommodity.getFloat("price");
                   
                    System.out.println("商品ID: " + commodityID);
                    System.out.println("商品名称: " + name);
                    System.out.println("商品信息: " + information);
                    System.out.println("商品单价: " + price);
                    System.out.println("数量: " + amount);
                    System.out.println("总价: " + totalPrice);
                    System.out.println("---------------------------");
                }
                
                //resultSetCommodity.close();
            } while (rs.next()); // 移动到下一行
        } else {
            System.out.println("购物车为空");
        }
        
        rs.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("购物车查看失败");
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








