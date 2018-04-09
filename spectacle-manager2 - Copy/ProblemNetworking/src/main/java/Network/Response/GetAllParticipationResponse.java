package Network.Response;

import Domain.Participation;

import java.util.List;

public class GetAllParticipationResponse extends  Response {

    private List<Participation> list;

    public GetAllParticipationResponse(List<Participation> list) {
        this.list = list;
    }


    public List<Participation> getList() {
        return list;
    }

}
