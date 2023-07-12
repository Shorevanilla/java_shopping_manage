package org.example;
/*import java.util.Scanner;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*
*/ ;
public class Main {
            public static void main(String[] args) {
             ConnectSQLite link=new ConnectSQLite();
             link.initSqlite(); 
             link.showCommodity();
            link.insertCommodity("快乐水", "喝了就会变得快乐");
            link.insertCommodity("黑暗水", "喝了就会变得黑暗");
            link.insertCommodity("bb霜", "补水后将变得快乐");
            link.insertCommodity("偷学宝典", "偷学达到极乐");
             //link.delete("commodity",3);
            
             link.searchCommodity("水");
            }
   
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
