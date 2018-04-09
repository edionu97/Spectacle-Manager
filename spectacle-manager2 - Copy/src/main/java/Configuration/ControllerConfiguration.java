package Configuration;

import Config.ConfigurationClass;
import Network.ClientProxy;
import Service.*;
import Controlers.LoginController;
import Controlers.MainWindowController;
import Controlers.SearchController;
import Controlers.SaleController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ControllerConfiguration {

    private ApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationClass.class);
    private IParticipationService participationService = context.getBean(ClientProxy.class);
    private ISaleService saleService = context.getBean(ClientProxy.class);


    private MainWindowController mainWindowController() throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/sellWindow.fxml"));

        Parent sellWindow = fxmlLoader.load();

        SaleController controller = fxmlLoader.getController();
        controller.setSaleService(saleService);


        /**
         * Creating the search window controller and loading the search window
         */

        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/Views/searchView.fxml"));

        Parent search = loader2.load() ;

        SearchController controller2 = loader2.getController();

        controller2.setService(participationService);

        /*
         * Creating the main window controller and  loading the main window
         */

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/mainWindowView.fxml"));

        Parent mainWindow = loader.load();

        MainWindowController mainWindowController = loader.getController();

        mainWindowController.setOwnerStage(new Stage());
        mainWindowController.setParticipationService(participationService);
        mainWindowController.setMainWindowView(mainWindow);
        mainWindowController.setSearchWindow(search);
        mainWindowController.setSellWindow(sellWindow);
        mainWindowController.setSaleController(controller);

        return mainWindowController;
    }

    @Bean
    public LoginController loginController() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/loginWindow.fxml"));

        Parent loginWindow = loader.load();

        LoginController controller = loader.getController();

        controller.setLoginWindow(loginWindow);
        controller.setPrimaryStage(new Stage());
        controller.setService(context.getBean(ClientProxy.class));
        controller.setController(mainWindowController());

        return controller;
    }
}
