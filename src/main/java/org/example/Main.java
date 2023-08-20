package org.example;
//import java.util.Scanner;
//import java.io.File;
import java.sql.Connection;
//import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class Main {
    public static void initSqlite(){
        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());//启动驱动//jdbc:sqlite:是协议
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/org/example/mydatabase.db");// 连接到 SQLite 数据库文件
            Statement statement = connection.createStatement();
            // 创建表
            statement.execute("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY, name TEXT, password TEXT)");
            statement.execute("CREATE TABLE IF NOT EXISTS manager (id INTEGER PRIMARY KEY, name TEXT, password TEXT)");
            statement.execute("CREATE TABLE IF NOT EXISTS commodity (id INTEGER PRIMARY KEY, name TEXT, information TEXT,price REAL)");
        
            statement.execute("CREATE TABLE IF NOT EXISTS client (id INTEGER PRIMARY KEY, name TEXT,information TEXT,manager TEXT)");
            
               
                    String queryInsert = "INSERT INTO manager (name, password) VALUES ('admin','admin')";
                    statement.executeUpdate(queryInsert);
    
                statement.close();
                connection.close();
            }
             catch (SQLException e) {
                e.printStackTrace();
                System.out.println("初始化失败");
            }
        }
            public static void main(String[] args) {
            Login login=new Login();
             ManagerOperation link=new ManagerOperation();
            UserOperation userop=new UserOperation();

             initSqlite(); 
        /*   link.modifySelfPassword("admin", "admin");
            if( login.accountVerify("manager","admin", "admin"))
           { 
            System.out.println("登入成功");
            link.modifySelfPassword("admin", "123456");
            if(login.accountVerify("manager","admin", "123456"))
            System.out.println("修改成功");
            }
             else  System.out.println("账号或密码错误");
            link.insertClient("溜溜梅", "您没事吧？");
            link.insertClient("纯真集团", "扎不多德勒");
            link.insertClient("下头影视", "家人们懂得都懂");
            link.insertClient("杏花微雨", "究竟是错付了");
            link.showClient();
            link.searchClient("下头");
            link.searchClient("杏花");
*/
           // link.delete("client", 1);
            
             
            link.showCommodity();
            link.insertCommodity("快乐水", "喝了就会变得快乐",4);
            link.insertCommodity("黑暗水", "喝了就会变得黑暗",10);
            link.insertCommodity("bb霜", "补水后将变得快乐",15);
            link.insertCommodity("偷学宝典", "偷学达到极乐",50.5);
            link.modifyCommodity(12, 15);

             //link.delete("commodity",3);
            
             //link.searchCommodity("水");
            // link.modifyCommodity("神仙玉女水", 1);
             //link.searchCommodity(1);
             
 
            String name1="彭于晏";
            String name2="吴彦祖";
            String name3="TalarSwift";
            String password="123456";
            
             if(userop.accountNameCheck(name1)  && userop.isPasswordLengthValid(password)&&  userop.isPasswordSame(password, password)  )
             if(userop.register(name1, password)) System.out.println("用户注册成功");
            
            if(userop.accountNameCheck(name2)  && userop.isPasswordLengthValid(password)&&  userop.isPasswordSame(password, password)  )
            if(userop.register(name2, password) )System.out.println("用户注册成功");
            
            if(userop.accountNameCheck(name3)  && userop.isPasswordLengthValid(password)&&  userop.isPasswordSame(password, password)  )
             if (userop.register(name3, password) )System.out.println("用户注册成功");
           if( login.accountVerify("user", name3, password))
           System.out.println("用户登入成功");
           else  System.out.println("用户名或密码错误");
          String newpassword= link.resetUserPassword(name3);


          
        
        
          if(newpassword!=null)
          {
            System.out.println("成功重置用户 "+name3+" 密码，新密码为：\n"+newpassword);
        }
             userop.addCommodity(1,3);
             userop.addCommodity(1,1);
             userop.addCommodity(1,2);
             userop.showShoppingCart(1);
       
       
            }
         

   
   
   
   
   
   
   
   
        /*
   if (!ConnectSQLite.has_connect )  ConnectSQLite.connect(); 
     
createNewDatabase("/workspace/java_shopping_manage/src/main/java/org/database/create-db.db");


        
 System.out.println("欢迎使用购物管理系统，您可以输入 help 查看指令");
        Scanner scanner = new Scanner(System.in);
        String userInput = "";

        
         

    
        while(true) {
            
            System.out.print("你当前在第一级目录下 >");
            System.out.println("请输入你的指令, exit退出:");
            userInput = scanner.nextLine();
            if(userInput.equals("exit")) {
                break;
            }

            System.out.println("你刚才输入的是:" + userInput);

            if (userInput.equals("help")) {
                Main.help();
            }
            else if (userInput.equals("ulogin")) {
                Main.uEnter();}
             else  if (userInput.equals("mlogin")) {
                Main.mEnter();
                }

        }
        
        scanner.close();
        System.out.println("Done.");
    
    
    }

    private static void help() {
        System.out.println("欢迎进入帮助子菜单");

        Scanner scanner = new Scanner(System.in);
        String userInput = "";

        while(true) {
            System.out.println("**********************help**********************");
            System.out.println("请输入你的指令，输入 q 返回上一级:");
            userInput = scanner.nextLine();
            
            if (userInput.equals("q")) {
                break;
            }

            System.out.println("其实吧，这个也就是做个样子给你看看，让你知道怎么做二级界面罢了");
        }
       // flush(scanner);
        
    }
    private static void uEnter() {
        System.out.println("********************用户登入********************");

        Scanner scanner = new Scanner(System.in);
        String userInput = "";

        while(true) {
            System.out.println("请输入你的指令,q 退出:");
            System.out.print("你当前在 uEnter 的二级子目录下 >");
            userInput = scanner.nextLine();

            if (userInput.equals("q")) {
                break;
            }

            System.out.println("其实吧，这个也就是做个样子给你看看，让你知道怎么做二级界面罢了");
        }
       // flush(scanner);
        
    }
    private static void mEnter() {
        System.out.println("********************管理员登入********************");

        Scanner scanner = new Scanner(System.in);
        String userInput = "";
       

      


        while(true) {
            System.out.println("请输入你的指令,q 退出");
            System.out.print("你当前在 mEnter 的二级子目录下 >");
            userInput = scanner.nextLine();

            if (userInput.equals("q")) {
                break;
            }

            System.out.println("其实吧，这个也就是做个样子给你看看，让你知道怎么做二级界面罢了");
        }
      
        
    }

   
    

        public static void createNewDatabase(String fileName) {
    
            String url = "jdbc:sqlite:" + fileName;
    
            try {
                Connection conn = DriverManager.getConnection(url);
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    System.out.println("The driver name is " + meta.getDriverName());
                    System.out.println("A new database has been created.");
                }
    
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        
       
    
      
 
  
} */
}
