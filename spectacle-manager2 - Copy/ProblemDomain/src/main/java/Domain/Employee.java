package Domain;

import java.io.Serializable;

public class Employee implements Serializable {

    private int codAg;
    private String userName;
    private String password;

    public Employee(){}

    public Employee(int codAg, String userName, String password) {
        this.codAg = codAg;
        this.userName = userName;
        this.password = password;
    }

    public int getCodAg() {
        return codAg;
    }

    public void setCodAg(int codAg) {
        this.codAg = codAg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        return codAg + " " + userName  + " "+ password;
    }
}
