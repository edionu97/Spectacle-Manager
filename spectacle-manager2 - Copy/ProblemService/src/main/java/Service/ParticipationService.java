package Service;

import Domain.Participation;
import Repository.IParticipationRepository;
import Utils.Validators.Validator;

import java.sql.Date;
import java.util.List;

public class ParticipationService implements  IParticipationService {

    private IParticipationRepository repository;

    private Validator<Participation> validator;

    public ParticipationService(IParticipationRepository repository, Validator<Participation> validator){
        this.repository = repository;
        this.validator = validator;
    }


    @Override
    public List <Participation> filterByDate(Date date){
        return repository.filterBySpecDay(date);
    }

    @Override
    public List <Participation> getAllParticip(){
        return repository.getAll();
    }
}
