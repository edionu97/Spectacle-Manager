package Repository;

import Domain.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IEmployeeRepository extends  IRepository<Integer, Employee> {

    /**
     *
     * @param resultSet
     * @return a new angajat with fields completed from info resulted in the resultSet
     * @throws SQLException
     */

    default Employee buildEmployee(ResultSet resultSet ) throws SQLException {

        return new Employee(
                resultSet.getInt("codAg"),
                resultSet.getString("userId"),
                resultSet.getString("userPasswd")
        );

    }

    Employee findByUserIdAndPassword(String user, String password);
}
