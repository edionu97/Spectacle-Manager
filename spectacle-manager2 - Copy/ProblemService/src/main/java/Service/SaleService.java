package Service;

import Domain.Buyer;
import Domain.Show;
import Domain.Sale;
import Repository.ISaleRepository;

import Utils.Validators.Validator;

import java.util.List;



public class SaleService implements  ISaleService{

    private ISaleRepository repository;

    private Validator<Sale> validator;

    public SaleService(ISaleRepository repository, Validator<Sale> validator){
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public void addVanzare(String clientName,int desiredSeats,int showCode,int codAg) throws  Exception{

        Buyer client = repository.getBuyerByName(clientName);

        if(client == null)throw  new Exception("In database does not exist  a client with the name = " + clientName);

        Show show = repository.getShowById(showCode);

        if(show.getDisponibile() - desiredSeats < 0)throw  new Exception("The number of free seats is less than " + desiredSeats);

        repository.save(new Sale(client, show,repository.getEmployeeById(codAg),desiredSeats));

    }

    @Override
    public List <Show> getSpec(){
        return repository.getShows();
    }

    @Override
    public List<Sale> getAllSales(){
        return repository.getAll();
    }

}
