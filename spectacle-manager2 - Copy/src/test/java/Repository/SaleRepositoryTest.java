package Repository;

import Domain.Sale;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

public class SaleRepositoryTest {

    private SaleRepository repository = new SaleRepository("/ConfigFiles/testProperties.prop");

    @Test
    public void getAngajatById() throws Exception {
        Assert.assertEquals(repository.getEmployeeById(1).getUserName(),"ana_are_mere");
        Assert.assertNull(repository.getEmployeeById(55));
    }

    @Test
    public void getCumparatorById() throws Exception {
        Assert.assertEquals(repository.getBuyerById(1).getNume(),"tudor nan");
        Assert.assertNull(repository.getBuyerById(55));
    }

    @Test
    public void getSpectacolById() throws Exception {
        Assert.assertEquals(repository.getShowById(1).getLocatie(),"piatra-neamt");
        Assert.assertNull(repository.getShowById(22));
    }

    @Test
    public void getCumparatatorByName() throws Exception {
        Assert.assertEquals(repository.getBuyerByName("maria").getEmail(),"maria_adresa2");
        Assert.assertNull(repository.getBuyerByName("dada"));
    }


    @Test
    public void delete() throws Exception {

        repository.delete(new Pair<>(1,1));

        Assert.assertEquals(repository.getAll().size(),1);

        repository.save(new Sale(1,1,1,3));

        Assert.assertEquals(repository.getAll().size(),2);
    }

    @Test
    public void getAll() throws Exception {
        Assert.assertEquals(repository.getAll().size(),2);
    }

    @Test
    public void save() throws Exception {
        repository.save(new Sale(3,1,1,5));
        Assert.assertEquals(repository.getAll().size(),3);
        repository.delete(new Pair<>(3,1));
        Assert.assertEquals(repository.getAll().size(),2);
    }

    @Test
    public void findById() throws Exception {
        Assert.assertNotNull(repository.findById(new Pair<>(1,1)));
        Assert.assertNull(repository.findById(new Pair<>(1,8)));
    }

    public SaleRepositoryTest() throws SQLException, IOException, ClassNotFoundException {}
}