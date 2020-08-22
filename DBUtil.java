package BankTradin_1;

import javax.jws.soap.SOAPBinding;
import java.io.*;
import java.util.*;

/*
* 模拟银行中的账户人信息，相当于数据库功能*/
public class DBUtil {
    /*添加用户到文件中
     * 序列化内容在https://www.cnblogs.com/liaoweipeng/p/4474680.html中模仿而来
     * 未能搞明白其中原理，比如说截掉aced 005
     * 上文原因已经搞定：写入文件时会填加一个头部标志和尾部表示，而每次写入的时候都会添加，而这个标识是无法被识别的
     * 注意，此处能实现尾加
     * 功能运转调试成功*/
    public void AddUser(LinkedList<User> link) throws IOException {
        /*初始化user*/
        Scanner scanner = new Scanner(System.in);//用于获取用户输入
        User user = new User();
        System.out.println("设置银行卡卡号");
        user.setCarId(scanner.nextLine());
        System.out.println("设置银行卡密码");
        user.setCardPwd(scanner.nextLine());
        System.out.println("设置银行卡账户姓名");
        user.setUserName(scanner.nextLine());
        System.out.println("设置手机号码");
        user.setCall(scanner.nextLine());
        System.out.println("设置银行卡存款金额");
        user.setAccount(scanner.nextDouble());
        link.add(user);//将user写入链表
        //打开文件
        File file = new File("D:\\JAVA\\project\\BankTradingSystem\\src\\BankTradin_1", "UserDate.txt");
        boolean isexist = false;//定义一个用来判断文件是否需要截掉aced 005的
        if (file.exists()) { //文件是否存在
            isexist = true;
            FileOutputStream FileOut = new FileOutputStream(file, true);
            ObjectOutputStream objectOut = new ObjectOutputStream(FileOut);
            long pos = 0;
            if (isexist) {
                pos = FileOut.getChannel().position() - 4;//追加时去掉头部aced 005
                FileOut.getChannel().truncate(pos);
            }
            objectOut.writeObject(user);//将user序列化文件中
            System.out.println("成功追加第");


        } else {//文件不存在
            file.createNewFile();
            FileOutputStream FileOut = new FileOutputStream(file);
            ObjectOutputStream objectOut = new ObjectOutputStream(FileOut);
            objectOut.writeObject(user);//进行序列化
            objectOut.close();//关闭文件
            System.out.println("首次序列化成功");
        }


    }

    /*读取文件中的用户信息
    * 反序列化操做，将文件中的数据全部存入到链表中
    * 注意文件写入是否正确https://www.cnblogs.com/ouhaitao/p/7683514.html，抛出异常原因及解决方法
    * 功能运转调试成功*/
    public LinkedList<User> readUserDate() throws IOException {
        File file = new File("D:\\JAVA\\project\\BankTradingSystem\\src\\BankTradin_1", "UserDate.txt");
        LinkedList<User> link = new LinkedList<User>();//创建一个链表对象
        if (file.exists()) {
            ObjectInputStream objInt;
            try {
                FileInputStream fileIn = new FileInputStream(file);
                objInt = new ObjectInputStream(fileIn);
                while (fileIn.available()>0){//文件还有内容
                    User user =(User)objInt.readObject();//从流中读取对象
                    link.add(user);//将读取出来的文件存入到链表中
                  /*  System.out.println("银行卡号"+user.getCarId()+" 密码"+user.getCardPwd()+
                                        " 账户名"+user.getUserName()+" 手机号"+user.getCall()+
                                        " 存款余额"+user.getAccount());*/
                }
                objInt.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return link;//将链表引用返回
    }
    /*删除银行用户*/
    public void DelUser(LinkedList<User> link){

    }

    /*根据银行卡号比对密码
    * 比对成功则返回该用户
    * 返回目标卡号的索引
    * 代码调试运行成功*/
    public int JudgePwd(LinkedList<User>link,String card_id) throws IOException, InterruptedException {
        ListIterator<User>DisplayUserInfo = link.listIterator();//链表迭代器，用来访问列表中的元素
        Scanner in = new Scanner(System.in);
        int address = 0;
       /* System.out.println("根据银行卡号比对密码");
         *//* System.out.println("链表大小"+link.size());
        System.out.println(DisplayUserInfo.hasNext());*/
        User user = null;
        while (DisplayUserInfo.hasNext()){//如果迭代器还有多个供访问的元素则返回true
            address = DisplayUserInfo.nextIndex();//返回下一次调用next方法时将返回的元索引
            user = DisplayUserInfo.next();//访问元素
            if (card_id.equals(user.getCarId())){
                break;//如果存在此卡号，跳出循环
            }
            if (!DisplayUserInfo.hasNext()){ //如果遍历完所有链表依旧没有输入的卡号
                System.out.println("不存在该账户！请输入正确的卡号");
                new Bank().menu();
                return 0;
            }
        }
        System.out.println("请输入银行卡密码：");
        String pwd = in.nextLine();
        assert user != null;
        if (pwd.equals(user.getCardPwd())){
            System.out.println("登录成功呢！欢迎"+user.getUserName()+"先生");
            return address;//返回地址
           /* System.out.println("银行卡号"+user.getCarId()+ " 账户名"+user.getUserName()+
                               " 手机号"+user.getCall()+ " 存款余额"+user.getAccount());*/
        }else {
            System.out.println("密码错误！");
            new Bank().menu();//返回到主菜单
            return 0;
        }
    }

    /*显示出文件中所有的用户信息
    * 功能运转调试成功*/
    public void DisplayAllInfo(LinkedList<User>link){
        ListIterator<User>DisplayInfo = link.listIterator();//创建一个迭代器用于访问链表中的所有数据
        /*  System.out.println("通过访问链表，来显示用户信息");
        System.out.println("链表大小"+link.size());
        System.out.println(DisplayUserInfo.hasNext());*/
        User user = null;
        //访问元素
        while (DisplayInfo.hasNext()){//如果迭代器还有多个供访问的元素则返回true
            user = DisplayInfo.next();//读取链表中的数据对象
            System.out.println("银行卡号" + user.getCarId() + " 密码" + user.getCardPwd() +
                    " 账户名" + user.getUserName() + " 手机号" + user.getCall() +
                    " 存款余额" + user.getAccount());
        }
    }

    /*将链表内容重新写入文件
    * 抛出异常的原因，主要是更改文件后版本的序列号遭到了修改
    * 每个serializable对象的类都被编码
    * 抛出异常解决办法 https://www.cnblogs.com/ouhaitao/p/7683514.html
    * 更新确定问题出在数据存入上，没有将每次的值赋值给user直接copy过来存入是没有引用，所以会报错
    * @第二阶段，存入文件和反序列化正确，但是原有数据未能被清除，可能是删除文件出现了问题
    * 文件调试成功，各各项功能正常*/
    public void SaveFile(LinkedList<User> link)throws IOException{
        ListIterator<User>DisplayInfo = link.listIterator();//创建一个迭代器用于访问链表中的所有数据
        User user = null;
        //打开文件
        File file = new File("D:\\JAVA\\project\\BankTradingSystem\\src\\BankTradin_1", "UserDate.txt");
       /*将文件删除，这样便于重写该文件*/
        try {
            if(file.delete()){
                System.out.println("文件删除成功！");
            }else
                System.out.println("文件删除失败！");
        }catch (Exception e){
            e.printStackTrace();
        }
        /*将修改后的链表存入文件中*/
        while (DisplayInfo.hasNext()){ //如果链表中还有内容，则不停的添加
            /*注意，还要确定是否是第一次存入，如果是第一次存入，需要删除文件进行尾加*/
            user = DisplayInfo.next();//链表中的对象赋值给user主要是为了后文好表示
            boolean isexist = false;//定义一个用来判断文件是否需要截掉aced 005的
            if (file.exists()) { //文件是否存在

                isexist = true;
                FileOutputStream FileOut = new FileOutputStream(file,true);
                ObjectOutputStream objectOut = new ObjectOutputStream(FileOut);
                long pos = 0;
                if (isexist) {
                    pos = FileOut.getChannel().position() - 4;//追加时去掉头部aced 005
                    FileOut.getChannel().truncate(pos);
                }
                objectOut.writeObject(user);//将user序列化文件中
                objectOut.flush();//刷新缓冲区域
                System.out.println("成功追加第");
                objectOut.close();//关闭
                FileOut.close();//关闭流

            } else {//文件不存在则创建一个新的文件，然后将链表数据存入文件
                file.createNewFile();
                FileOutputStream FileOut = new FileOutputStream(file);
                ObjectOutputStream objectOut = new ObjectOutputStream(FileOut);
                objectOut.writeObject(user);//进行序列化
                objectOut.close();//关闭文件
                System.out.println("首次序列化成功");
            }

        }


    }

    /*修改链表中的账户余额，实现取款功能
    * 功能调试运转正常，能实现相应的功能*/
    public void Withdrawal(int address,LinkedList<User>link) throws IOException {
        ListIterator<User>DisplayInfo = link.listIterator(address);//创建一个迭代器用于访问链表中的所有数据
        Scanner in = new Scanner(System.in);
        User user = link.listIterator(address).next();
        System.out.println("账户姓名"+user.getUserName());
        System.out.println("请输入取款金额");
        double money = in.nextDouble();//获取用户要取款的金额
        if (money <= user.getAccount()){//只有当账户余额足够时才能取款
            user.setAccount(user.getAccount()-money);//完成取款后剩下的余额
            System.out.println("账户余额"+user.getAccount());
            DisplayInfo.next();
            DisplayInfo.set(user);//用新元素取代next或previous上次访问的元素
            System.out.println("存入链表后显示用户信息"+DisplayInfo.next().getAccount());
            SaveFile(link);
        }else
            System.out.println("余额不足，取款失败");
    }

    /*实现对储存文件的删除操做，便于重写文件，在Save方法中也用来删除文件的操做，主要就是有利于重写文件*/
    public void DelFile(){
        File file = new File("D:\\JAVA\\project\\BankTradingSystem\\src\\BankTradin_1", "UserDate.txt");
        /*将文件删除，这样便于重写该文件*/
        try {
            if(file.delete()){
                System.out.println("文件删除成功！");
            }else
                System.out.println("文件删除失败！");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*实现存款业务
    * 程序调试成功，程序运转正常*/
    public void Deposit(int address,LinkedList<User>link)throws IOException{
        ListIterator<User>DisplayInfo = link.listIterator(address);//创建一个迭代器用于访问链表中的所有数据
        Scanner in = new Scanner(System.in);
        User user = link.listIterator(address).next();
        System.out.println("账户姓名"+user.getUserName());
        System.out.println("请输入存款金额");
        double money = in.nextDouble();//获取用户要取款的金额
        if (money > 0){//只有当存款大于零的时候才能生效
            user.setAccount(user.getAccount()+money);//完成存款后账户的金额增加
            System.out.println("账户余额"+user.getAccount());
            DisplayInfo.next();
            DisplayInfo.set(user);//用新元素取代next或previous上次访问的元素，修改链表中账户存款的金额
            System.out.println("存入链表后显示用户信息"+DisplayInfo.next().getAccount());
            SaveFile(link);//将修改后的链表存入到文件中，确保信息的永久储存
        }else
            System.out.println("存款金额非法");

    }

    /*展示登录用户的账户余额
    * 传入用户在链表中的地址，还有链表的索引
    * 程序调试成功，代码运转正常*/
    public void DisplayCredit(int address,LinkedList<User>link){
        ListIterator<User>DisplayInfo = link.listIterator(address);//创建一个迭代器用来访问链表特定位置的数据
        User user = DisplayInfo.next();//将链表中该账户的信息索引赋值给user，用user来访问
        System.out.println("账户余额"+user.getAccount());
    }
}
