package Service;

import Domain.Employee;

import java.util.List;

public interface ILoginService {

    boolean canLogin(String userName,String password);

    Employee getInfoAfterLog(String userName, String password);

    boolean isLoggedIn(String username,String password);

    List<Employee> getAllUsers();

    void logout(String username);
}
