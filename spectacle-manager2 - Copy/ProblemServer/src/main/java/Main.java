import Config.ConfigurationClass;
import Server.Server;
import Service.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String ...args) throws IOException, SQLException, ClassNotFoundException {

        ApplicationContext context =  new AnnotationConfigApplicationContext(ConfigurationClass.class);

        Server server = new Server(context.getBean(LoginService.class),context.getBean(SaleService.class),context.getBean(ParticipationService.class),"/properties.prop");

        server.start();
    }
}
