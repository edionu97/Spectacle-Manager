package Network.Response;

public class SaleResponse extends  Response {
    private String error;

    public SaleResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
