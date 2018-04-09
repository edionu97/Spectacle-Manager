package Network.Requests;

public class SaleRequest extends  Request {

    private String clientName;
    private int desiredSeats, showCode, codAg;

    public SaleRequest(String clientName, int desiredSeats, int showCode, int codAg) {
        this.clientName = clientName;
        this.desiredSeats = desiredSeats;
        this.showCode = showCode;
        this.codAg = codAg;
    }

    public String getClientName() {
        return clientName;
    }

    public int getDesiredSeats() {
        return desiredSeats;
    }

    public int getShowCode() {
        return showCode;
    }

    public int getCodAg() {
        return codAg;
    }
}
