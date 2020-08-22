package BankTradin_1;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

/*该类用于处理基本的业务逻辑*/
public class Bank {
    public static void main(String[] args) throws IOException, InterruptedException {
        Bank bank= new Bank();
        bank.menu();//功能菜单
    }

    public void menu() throws IOException, InterruptedException {
        DBUtil dbUtil = new DBUtil();
        Scanner in = new Scanner(System.in);
        LinkedList link = new LinkedList();//构建一个空链表
        Map<String,User> map = new HashMap<>();//创建一个散射链表


       /* for (int i = 0;i<4;i++){
            dbUtil.AddUser(link);
        }*/
        link = dbUtil.readUserDate();/*反序列化操做*/
       /* System.out.println("反序列化成功");
        dbUtil.DisplayAllInfo(link);//读取文件中所有的信息
        System.out.println("反序列化添加到链表成功，进入下一步操做");*/
        System.out.println("请输入银行卡号");
        String card_id = in.nextLine();//获取用户输入的银行卡号
        int address = dbUtil.JudgePwd(link,card_id);//判断登录密码并返回该卡号所在链表中的位置
        System.out.println("请输入您需要进行的操做类型，按回车键结束");
        System.out.println("存款：1 \t 取款：2 \t 余额：3 \t退出：0");
        int choice = in.nextInt();//获取用户输入的选择
        switch (choice){
            case 1:
                dbUtil.Deposit(address,link);//输入存款用户索引地址、链表引用，跳转到存款界面
                break;
            case 2:
                dbUtil.Withdrawal(address,link);//将获取的用户索引地址传入 ,跳转到取款界面
                break;
            case 3:
                dbUtil.DisplayCredit(address,link);//显示账户余额
                break;
            case 4:
                System.exit(0);//让程序正常退出
                break;
            default:
                System.out.println("请输入正确的选项");
        }
        System.out.println("操做完成，即将退出程序！");
        Thread.sleep(1000);//线程休眠100毫秒退出程序
        System.exit(0);
    }
}
