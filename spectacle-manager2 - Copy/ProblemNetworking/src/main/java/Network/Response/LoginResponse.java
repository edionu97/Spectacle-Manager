package Network.Response;

public class LoginResponse  extends  Response{

    private String loginMessage;

    public LoginResponse(String loginMessage) {
        this.loginMessage = loginMessage;
    }

    public void setLoginMessage(String loginMessage) {
        this.loginMessage = loginMessage;
    }

    public String getLoginMessage() {
        return loginMessage;
    }
}
