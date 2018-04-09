package Network.Response;

import Domain.Show;

import java.util.List;

public class GetSpecResponse extends  Response {

    private List<Show> list;

    public GetSpecResponse(List<Show> list) {
        this.list = list;
    }

    public List<Show> getList() {
        return list;
    }

    public void setList(List<Show> list) {
        this.list = list;
    }
}
