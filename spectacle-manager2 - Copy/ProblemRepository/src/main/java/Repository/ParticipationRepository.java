package Repository;

import Domain.Artist;
import Domain.Participation;
import Domain.Show;
import Utils.ConnectionBuilder;

import javafx.util.Pair;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipationRepository implements IParticipationRepository {

    private ConnectionBuilder connectionBuilder;

    public ParticipationRepository(String propertyLocation) throws SQLException, IOException, ClassNotFoundException {
        connectionBuilder = new ConnectionBuilder(propertyLocation);
    }

    @Override
    public List <Participation> filterBySpecDay(int day){

        List <Participation> list = new ArrayList<>();

        String sql = "select * from artisti a inner join participari p on a.codA = p.codA " +
                "inner join spectacole s on s.codS = p.codS " +
                "where convert(substring(seTinePe,9,2) , unsigned) = ? ";

        try(Connection connection = connectionBuilder.getConnection()){

            try(PreparedStatement statement = connection.prepareStatement(sql)){
                statement.setInt(1,day);

                try(ResultSet resultSet = statement.executeQuery()) {

                    while (resultSet.next()) list.add(buildParticipation(resultSet));
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List <Participation> filterBySpecDay(Date date){
        List <Participation> list = new ArrayList<>();

        String sql = "select * from artisti a inner join participari p on a.codA = p.codA " +
                "inner join spectacole s on s.codS = p.codS " +
                "where seTinePe = ? ";

        try(Connection connection = connectionBuilder.getConnection()){

            try(PreparedStatement statement = connection.prepareStatement(sql)){
                statement.setDate(1,date);

                try(ResultSet resultSet = statement.executeQuery()) {

                    while (resultSet.next()) list.add(buildParticipation(resultSet));
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Artist getArtistById(int id){

        try(Connection connection = connectionBuilder.getConnection()) {

            try(PreparedStatement statement = connection.prepareStatement("select * from artisti where codA = ?")){

                statement.setInt(1,id);

                try(ResultSet resultSet = statement.executeQuery()){

                    if(!resultSet.next())return null;

                    return buildArtist(resultSet);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Show getShowById(int id){

        try(Connection connection = connectionBuilder.getConnection()) {

            try(PreparedStatement statement = connection.prepareStatement("select * from spectacole where codS = ?")){

                statement.setInt(1,id);

                try(ResultSet resultSet = statement.executeQuery()){

                    if(!resultSet.next())return null;

                    return buildShow(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Participation findById(Pair<Integer,Integer> id) {

        int codA = id.getKey();
        int codS = id.getValue();

        try(Connection connection = connectionBuilder.getConnection()){

            try(PreparedStatement statement = connection.prepareStatement("select * from artisti a inner join participari p " +
                                                                                            "on a.codA = p.codA inner join spectacole s on s.codS = p.codS " +
                                                                                "where p.codS = ? and p.codA = ?")){
                statement.setInt(1,codS);
                statement.setInt(2,codA);

                try(ResultSet resultSet = statement.executeQuery()){

                    if(!resultSet.next())return null;

                    return buildParticipation(resultSet);

                }

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void delete(Pair <Integer,Integer> id) throws Exception {

        int codA = id.getKey();
        int codS = id.getValue();

        if(findById(id) == null)throw  new Exception("The object does not exist in database");

        try(Connection connection = connectionBuilder.getConnection()) {

            try (PreparedStatement statement = connection.prepareStatement("delete from participari where codS = ? and codA = ?")) {
                statement.setInt(1, codS);
                statement.setInt(2, codA);
                statement.execute();
            }

        }
    }

    @Override
    public List<Participation> getAll() {

        List <Participation> list = new ArrayList<>();

        try(Connection connection =  connectionBuilder.getConnection()){

            try(PreparedStatement statement = connection.prepareStatement("select * from artisti a inner join participari p " +
                                                                                    "on a.codA = p.codA inner join spectacole s on s.codS = p.codS ")) {
                try(ResultSet resultSet = statement.executeQuery()){

                    while (resultSet.next())list.add(buildParticipation(resultSet));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void save(Participation newObject) throws Exception {

        if(findById(new Pair<>(newObject.getArtist().getCodA(),newObject.getShow().getCodS())) != null)throw  new Exception("Duplicates are not allowed");

        try(Connection connection = connectionBuilder.getConnection()){

            try(PreparedStatement statement = connection.prepareStatement("insert into  participari values (?,?)")){

                statement.setInt(1,newObject.getArtist().getCodA());
                statement.setInt(2,newObject.getShow().getCodS());

                statement.execute();
            }
        }
    }

    @Override
    public void update(Pair<Integer,Integer> id, Participation object) throws Exception {

        if(findById(id) == null)throw  new Exception("This object does not exists in database");

        try(Connection connection = connectionBuilder.getConnection()){

            try(PreparedStatement preparedStatement = connection.prepareStatement("update participari set codA = ?, codS = ? where codA = ? and codS = ?") ){

                preparedStatement.setInt(1,object.getArtist().getCodA());
                preparedStatement.setInt(2,object.getShow().getCodS());
                preparedStatement.setInt(3,id.getKey());
                preparedStatement.setInt(4,id.getValue());

                preparedStatement.execute();
            }
        }

    }
}
