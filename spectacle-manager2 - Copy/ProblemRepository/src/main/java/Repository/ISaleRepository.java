package Repository;

import Domain.Buyer;
import Domain.Employee;
import Domain.Sale;
import Domain.Show;
import javafx.util.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ISaleRepository extends   IRepository< Pair<Integer,Integer>, Sale >{

    default Sale buildSale(ResultSet resultSet) throws SQLException {

        return new Sale(

                buildBuyer(resultSet),

                buildShow(resultSet),

                buildEmployee(resultSet),

                resultSet.getInt("dorite")

        );
    }

    default Show buildShow(ResultSet resultSet) throws SQLException {

        return  new Show(
                resultSet.getInt("codS"),
                resultSet.getTime("incepeLa"),
                resultSet.getDate("seTinePe"),
                resultSet.getInt("disp"),
                resultSet.getInt("vandute"),
                resultSet.getString("locatie")
        );

    }

    default Buyer buildBuyer(ResultSet resultSet) throws  SQLException{

        return  new Buyer(
                resultSet.getInt("codC"),
                resultSet.getString("nume"),
                resultSet.getString("adresa"),
                resultSet.getString("email")
        );
    }

    default Employee buildEmployee(ResultSet resultSet) throws  SQLException{

        return new Employee(
                resultSet.getInt("codAg"),
                resultSet.getString("userId"),
                resultSet.getString("userPasswd")
        );
    }

    Employee getEmployeeById(int id);

    Buyer getBuyerById(int id);

    Show getShowById(int id);

    Buyer getBuyerByName(String name);

    List<Show> getShows();

}
