package Network.Response;

import Domain.Participation;

import java.util.List;

public class FilterByDateResponse extends  Response {

    private List<Participation> filteredList;


    public FilterByDateResponse(List<Participation> filteredList) {
        this.filteredList = filteredList;
    }

    public List<Participation> getFilteredList() {
        return filteredList;
    }

}
