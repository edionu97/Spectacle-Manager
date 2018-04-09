package Network.Response;

import Domain.Employee;

public class UserResponse extends Response {

    private Employee employee;

    public UserResponse(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
