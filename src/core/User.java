package core;

/**
 * Use:用户类
 * @author AprShine
 */
public class User {
    private final String account;
    private final String pwd;
    private String nickName;
    public User(String account,String pwd){
        this.account=account;
        this.pwd=pwd;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAccount() {
        return account;
    }

    public String getPwd() {
        return pwd;
    }

    public String getNickName() {
        return nickName;
    }
}
