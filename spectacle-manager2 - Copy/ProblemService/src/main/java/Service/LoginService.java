package Service;

import Domain.Employee;
import Repository.IEmployeeRepository;
import Utils.Validators.Validator;

import java.util.LinkedList;
import java.util.List;


public class LoginService implements ILoginService {

    private IEmployeeRepository repository;
    private Validator <Employee> validator;
    private List <Employee> logInUsers = new LinkedList<>();


    public LoginService(IEmployeeRepository repository, Validator<Employee> validator){
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public boolean canLogin(String userName,String password){
        return repository.findByUserIdAndPassword(userName,password) != null;
    }

    @Override
    public Employee getInfoAfterLog(String userName, String password){
       Employee a= repository.findByUserIdAndPassword(userName,password);
       logInUsers.add(a);
       return a;
    }

    @Override
    public boolean isLoggedIn(String username,String password){
        return logInUsers.stream().anyMatch(
                x->x.getPassword().equals(password) && x.getUserName().equals(username)
        );
    }

    @Override
    public List<Employee> getAllUsers(){
        return repository.getAll();
    }

    @Override
    public  void logout(String userName){

    }

}
