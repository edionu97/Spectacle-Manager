package Repository;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;


public class EmployeeRepositoryTest {

    private EmployeeRepository repository = new EmployeeRepository("/ConfigFiles/testProperties.prop");

    public EmployeeRepositoryTest() throws SQLException, IOException, ClassNotFoundException {}

    @Test
    public void findByUserIdAndPassword() throws Exception {
        Assert.assertNotNull(repository.findByUserIdAndPassword("user","passwd"));
        Assert.assertNull(repository.findByUserIdAndPassword("s","dad"));

    }

    @Test
    public void findById() throws Exception {
        Assert.assertNotNull(repository.findById(1));
        Assert.assertNull(repository.findById(20));
    }

    @Test
    public void getAll() throws Exception {
        Assert.assertEquals(repository.getAll().size(),3);
    }
}