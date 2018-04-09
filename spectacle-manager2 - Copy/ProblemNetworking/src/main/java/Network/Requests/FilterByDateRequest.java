package Network.Requests;

import java.sql.Date;

public class FilterByDateRequest extends  Request {

    private Date date;

    public FilterByDateRequest(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
