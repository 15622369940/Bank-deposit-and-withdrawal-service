package BankTradin_1;

import java.io.Serializable;
import java.util.LinkedList;

/*
* 该类主要用于描述用户信息
* Serializable接口实现，使得User可序列化
* */
public class User implements Serializable {
    /*序列化的id，只要加了版本号，在反序列化的时候，不论你的类属性是否改变，只要版本号不变那么尽可能的兼容版本
    * 如果版本号变了，那么序列化的过程就会抛出异常
    * 内容来自https://blog.csdn.net/lyb1832567496/article/details/52738136*/
    private static final long serialVersionUID = 6871740251451383067L;
    private String carId;//银行卡号
    private String cardPwd;//银行卡密码
    private String userName;//账户名称
    private String call;//手机号码
    private Double account;//账户余额

    /*录入银行卡卡号*/
    public void setCarId(String carId) {
        this.carId = carId;
    }
    /*设置银行卡密码*/
    public void setCardPwd(String cardPwd) {
        this.cardPwd = cardPwd;
    }
    /*设置银行卡账户名称*/
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /*设置银行卡手机号码*/
    public void setCall(String call) {
        this.call = call;
    }
    /*设置银行卡账户余额*/
    public void setAccount(double account){
        this.account = account;
    }

    public String getCarId() {
        return carId;
    }

    public String getCardPwd() {
        return cardPwd;
    }

    public String getUserName() {
        return userName;
    }

    public String getCall() {
        return call;
    }

    public Double getAccount() {
        return account;
    }
    public void getUserInfo(User user){
          System.out.println("银行卡号"+user.getCarId()+" 密码"+user.getCardPwd()+
                " 账户名"+user.getUserName()+" 手机号"+user.getCall()+
                " 存款余额"+user.getAccount());
    }
}
