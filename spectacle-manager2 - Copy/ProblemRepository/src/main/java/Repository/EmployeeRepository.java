package Repository;

import Domain.Employee;
import Utils.ConnectionBuilder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements  IEmployeeRepository{

    private ConnectionBuilder connectionBuilder ;

    public EmployeeRepository(String fileLocation) throws SQLException, IOException, ClassNotFoundException {
        connectionBuilder = new ConnectionBuilder(fileLocation);
    }


    @Override
    public Employee findByUserIdAndPassword(String user, String password){

        try(Connection connection =connectionBuilder.getConnection()){

            try(PreparedStatement statement = connection.prepareStatement("select * from angajati where userId = ? and userPasswd = ? ")){

                statement.setString(1,user);
                statement.setString(2,password);

                try(ResultSet resultSet = statement.executeQuery()){

                    if(!resultSet.next())return null;

                    return buildEmployee(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Employee findById(Integer id) {

        try(Connection connection = connectionBuilder.getConnection()){

            try(PreparedStatement statement = connection.prepareStatement("select * from angajati where codAg = ?")){

                statement.setInt(1,id);

                try(ResultSet resultSet = statement.executeQuery()){

                    if(!resultSet.next())return null;
                    return buildEmployee(resultSet);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Employee> getAll() {

        List <Employee> list = new ArrayList<>();

        try(Connection connection = connectionBuilder.getConnection()) {

            try(PreparedStatement statement = connection.prepareStatement("select * from angajati")){

                try(ResultSet resultSet = statement.executeQuery()){

                    while(resultSet.next())list.add(buildEmployee(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void save(Employee newObject) throws Exception {}

    @Override
    public void update(Integer id, Employee object) throws Exception {}

    @Override
    public void delete(Integer id) throws Exception {}
}
