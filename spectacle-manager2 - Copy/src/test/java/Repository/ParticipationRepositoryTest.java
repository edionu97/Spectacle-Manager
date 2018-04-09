package Repository;

import Domain.Participation;

import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class ParticipationRepositoryTest {

    @Test
    public void getArtistById() throws Exception {
        Assert.assertEquals(repository.getArtistById(1).getNume(),"onu");
        Assert.assertNull(repository.getArtistById(50));
    }

    @Test
    public void getSpectacolById() throws Exception {
        Assert.assertEquals(repository.getShowById(1).getLocatie(),"piatra-neamt");
        Assert.assertNull(repository.getShowById(1555));
    }

    @Test
    public void filterBySpecSDay() throws Exception {
        Assert.assertEquals(repository.filterBySpecDay(Date.valueOf(LocalDate.of(2018,5,5))).size(),1);
    }

    @Test
    public void filterBySpecDay() throws Exception {
        Assert.assertEquals(repository.filterBySpecDay(5).size(),1);
        Assert.assertEquals(repository.filterBySpecDay(15).size(),1);
        Assert.assertEquals(repository.filterBySpecDay(10).size(),0);
    }

    private ParticipationRepository repository = new ParticipationRepository("/ConfigFiles/testProperties.prop");

    public ParticipationRepositoryTest() throws SQLException, IOException, ClassNotFoundException {}

    @Test
    public void findById() throws Exception {
        Assert.assertNotNull(repository.findById(new Pair<>(1,1)));
        Assert.assertNull(repository.findById(new Pair<>(1,3)));
    }

    @Test
    public void delete() throws Exception {
        repository.delete(new Pair<>(1,2));

        Assert.assertNull(repository.findById(new Pair<>(1,2)));

        repository.save(new Participation(1,2));

        Assert.assertNotNull(repository.findById(new Pair<>(1,2)));
    }

    @Test
    public void getAll() throws Exception {
        Assert.assertEquals(repository.getAll().size(),2);
    }

    @Test
    public void save() throws Exception {

        repository.delete(new Pair<>(1,1));

        Assert.assertEquals(repository.getAll().size(),1);

        repository.save(new Participation(1,1));

        Assert.assertEquals(repository.getAll().size(),2);

    }

    @Test
    public void update() throws Exception {
        repository.update(new Pair<>(1,2),new Participation(1,3));
        Assert.assertNull(repository.findById(new Pair<>(1,2)));
        repository.update(new Pair<>(1,3),new Participation(1,2));
        Assert.assertNotNull(repository.findById(new Pair<>(1,2)));
    }
}