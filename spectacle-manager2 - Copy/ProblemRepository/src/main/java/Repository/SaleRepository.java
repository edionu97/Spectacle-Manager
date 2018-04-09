package Repository;

import Domain.Employee;
import Domain.Buyer;
import Domain.Show;
import Domain.Sale;
import Utils.ConnectionBuilder;
import javafx.util.Pair;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SaleRepository implements ISaleRepository {

    private ConnectionBuilder connectionBuilder;

    public SaleRepository(String propertyLocation) throws SQLException, IOException, ClassNotFoundException {
        connectionBuilder = new ConnectionBuilder(propertyLocation);
    }



    @Override
    public Employee getEmployeeById(int id){

        try(Connection connection = connectionBuilder.getConnection()){

            try(PreparedStatement preparedStatement = connection.prepareStatement("select * from angajati where codAg = ?")){

                preparedStatement.setInt(1,id);

                try(ResultSet resultSet = preparedStatement.executeQuery()){

                    return (resultSet.next())  ? buildEmployee(resultSet) : null;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Buyer getBuyerById(int id){

        try(Connection connection = connectionBuilder.getConnection()){

            try(PreparedStatement preparedStatement = connection.prepareStatement("select * from cumparatori where codC = ?")){

                preparedStatement.setInt(1,id);

                try(ResultSet resultSet = preparedStatement.executeQuery()){

                    return (resultSet.next())  ? buildBuyer(resultSet) : null;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Show getShowById(int id){

        try(Connection connection = connectionBuilder.getConnection()){

            try(PreparedStatement preparedStatement = connection.prepareStatement("select * from spectacole where codS = ?")){

                preparedStatement.setInt(1,id);

                try(ResultSet resultSet = preparedStatement.executeQuery()){

                    return (resultSet.next())  ? buildShow(resultSet) : null;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Buyer getBuyerByName(String name){

        try(Connection connection = connectionBuilder.getConnection()){

            try(PreparedStatement preparedStatement = connection.prepareStatement("select * from cumparatori where nume = ?")){

                preparedStatement.setString(1,name);

                try(ResultSet resultSet = preparedStatement.executeQuery()){

                    return (resultSet.next())  ? buildBuyer(resultSet) : null;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List <Show> getShows(){
        List <Show> list = new ArrayList<>();

        try(Connection connection = connectionBuilder.getConnection()){

            try(PreparedStatement preparedStatement = connection.prepareStatement("select * from spectacole")){

                try(ResultSet resultSet = preparedStatement.executeQuery()) {

                    while (resultSet.next()) list.add(buildShow(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }


    @Override
    public Sale findById(Pair<Integer, Integer> id) {

        try(Connection connection = connectionBuilder.getConnection()) {

            String sql  ="select *" +
                    "from  cumparatori c inner join vanzari v on c.codC = v.codC " +
                    "inner join spectacole s on v.codS = s.codS " +
                    "inner join angajati a on a.codAg = v.codAg" +
                    " where v.codS = ? and v.codC = ?";


            try(PreparedStatement statement = connection.prepareStatement(sql)){

                statement.setInt(1,id.getKey());
                statement.setInt(2,id.getValue());

                try(ResultSet resultSet = statement.executeQuery()){

                    if(!resultSet.next())return null;

                    return buildSale(resultSet);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void delete(Pair<Integer, Integer> id) throws Exception {

        try(Connection connection = connectionBuilder.getConnection()){

            try(PreparedStatement statement = connection.prepareStatement("delete from vanzari where codS = ? and codC = ?")){
                statement.setInt(1,id.getKey());

                statement.setInt(2,id.getValue());

                statement.execute();

                if(statement.getUpdateCount() == 0)throw  new Exception("In database does not exist such an object");
            }
        }

    }

    @Override
    public List<Sale> getAll() {

        String sql  ="select *" +
                "from  cumparatori c inner join vanzari v on c.codC = v.codC " +
                "inner join spectacole s on v.codS = s.codS " +
                "inner join angajati a on a.codAg = v.codAg";

        List <Sale> list = new ArrayList<>();

        try(Connection connection = connectionBuilder.getConnection()){

            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

                try(ResultSet resultSet = preparedStatement.executeQuery()) {

                    while (resultSet.next()) list.add(buildSale(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }

    private void updateSpectacol(int codS,int scade,Connection connection){

        try(PreparedStatement preparedStatement = connection.prepareStatement("update spectacole set vandute = vandute + ? , disp = disp - ? where codS = ?")){

            preparedStatement.setInt(1,scade);
            preparedStatement.setInt(2,scade);
            preparedStatement.setInt(3,codS);

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void save(Sale newObject) throws Exception {

        try(Connection connection = connectionBuilder.getConnection()){

            try(PreparedStatement statement = connection.prepareStatement("insert into vanzari values(?,?,?,?)")){

                statement.setInt(1,newObject.getShow().getCodS());
                statement.setInt(2,newObject.getBuyer().getCodC());
                statement.setInt(3,newObject.getDorite());
                statement.setInt(4,newObject.getEmployee().getCodAg());

                statement.execute();
            }

            updateSpectacol(newObject.getShow().getCodS(),newObject.getDorite(),connection);
        }
    }


    @Override
    public void update(Pair<Integer, Integer> id, Sale object) throws Exception {}
}
