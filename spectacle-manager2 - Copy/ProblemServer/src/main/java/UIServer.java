import Config.ConfigurationClass;
import GuiController.ServerController;
import Server.Server;
import Service.LoginService;
import Service.ParticipationService;
import Service.SaleService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class UIServer extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        ApplicationContext context =  new AnnotationConfigApplicationContext(ConfigurationClass.class);

        Server server = new Server(context.getBean(LoginService.class),context.getBean(SaleService.class),context.getBean(ParticipationService.class),"/properties.prop");




        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ServerGui.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        scene.getStylesheets().add("/serverStyle.css");

        primaryStage.setScene(scene);

        primaryStage.initStyle(StageStyle.UNDECORATED);

        //primaryStage.setIconified(true);

        primaryStage.getIcons().add(new Image(getClass().getResource("/Images/server.png").toExternalForm()));


        ServerController controller = fxmlLoader.getController();

        controller.setWindowStage(primaryStage);

        server.setBinder(controller);

        primaryStage.show();

        new Thread(() -> {
            try {
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static  void main(String ...args){
        launch(args);
    }
}
