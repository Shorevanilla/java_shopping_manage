package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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
       // flush(scanner);
        
    }
   
}
