
import Configuration.ControllerConfiguration;
import Controlers.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {

    public static void main(String... args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        new AnnotationConfigApplicationContext(ControllerConfiguration.class).getBean(LoginController.class).showLogin();
    }
}
