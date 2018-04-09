package Repository;

import Domain.Artist;
import Domain.Participation;
import Domain.Show;
import javafx.util.Pair;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IParticipationRepository extends  IRepository < Pair<Integer,Integer> , Participation>{

    /**
     *
     * @param resultSet the table which we interrogated
     * @return creates a new object based on @param fields
     * @throws SQLException if in table does not exist such a column
     */


    default Participation buildParticipation(ResultSet resultSet) throws SQLException {

        return new Participation(
                buildArtist(resultSet),
                buildShow(resultSet)
        );

    }

    /**
     *
     * @param resultSet
     * @return the artist which can be built with the information from the resultSet
     * @throws SQLException
     */

    default Artist buildArtist(ResultSet resultSet) throws SQLException {
        return  new Artist(
                resultSet.getInt("codA"),
                resultSet.getString("nume"),
                resultSet.getString("prenume"),
                resultSet.getString("email")
        );
    }

    /**
     *
     * @param resultSet
     * @return a new spectacol entity from the result set
     * @throws SQLException
     */

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


    /**
     * @param day
     * @return a list which contain only those records from database witch have been reserved in the @param
     */

    List<Participation> filterBySpecDay(int day);



    /**
     *
     * @param date
     * @return a list which contain only those records from database which have been reserved on the @param
     */

    List <Participation> filterBySpecDay(Date date);


    Artist getArtistById(int id);

    Show getShowById(int id);
}
