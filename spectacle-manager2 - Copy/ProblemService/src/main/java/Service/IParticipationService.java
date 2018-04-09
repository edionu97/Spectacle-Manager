package Service;

import Domain.Participation;

import java.sql.Date;
import java.util.List;

public interface IParticipationService {

    List<Participation> filterByDate(Date date);

    List <Participation> getAllParticip();
}
