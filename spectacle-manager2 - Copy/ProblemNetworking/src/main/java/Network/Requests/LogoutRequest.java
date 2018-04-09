package Network.Requests;

public class LogoutRequest extends  Request{

    private String userName;

    public LogoutRequest(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
