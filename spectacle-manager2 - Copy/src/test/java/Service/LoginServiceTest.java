package Service;


import Config.ConfigurationClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LoginServiceTest {

    private LoginService service= new AnnotationConfigApplicationContext(ConfigurationClass.class).getBean(LoginService.class);

    @Test
    public void canLogin() throws Exception {
    }

}