package GuiController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import  Server.Server;

import java.net.URL;
import java.util.ResourceBundle;

public class ServerController implements Initializable {

    @FXML
    private JFXListView<String> listViewServer;

    @FXML
    private JFXButton buttonClose;

    private ObservableList <String> list = FXCollections.observableArrayList();


    private Stage windowStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listViewServer.setItems(list);
        buttonClose.setOnAction(x->windowStage.close());

    }



    public void setWindowStage(Stage windowStage) {
        this.windowStage = windowStage;
    }


    public void setText(String text){
        list.add(text);
    }
}
