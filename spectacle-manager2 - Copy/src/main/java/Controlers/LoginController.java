package Controlers;

import Domain.Employee;
import Service.ILoginService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton buttonExit;

    @FXML
    private JFXPasswordField textFieldPassword;

    @FXML
    private JFXTextField textFieldUser;

    @FXML
    private Label labelWrong;

    private ILoginService service;
    private Stage primaryStage,loginStage;
    private Parent loginWindow;

    private double xOffset,yOffset;


    public void setController(MainWindowController controller) {
        this.mainWindowController = controller;
    }

    private MainWindowController mainWindowController;

    private void constructStage(){
        loginStage = new Stage();
        loginStage.initOwner(primaryStage);
        loginStage.initStyle(StageStyle.UNDECORATED);

        Scene loginScene = new Scene(loginWindow);
        loginScene.getStylesheets().add("/style.css");

        loginStage.setScene(loginScene);
    }

    private void startTheApp(Employee employee){
        close();
        mainWindowController.showMainWindow(employee,this);
    }

    private void loginButtonPressed(ActionEvent event){
        String userName = textFieldUser.getText();
        String password = textFieldPassword.getText();

        if(!service.canLogin(userName,password)){

            labelWrong.setVisible(true);
            return;
        }

        startTheApp(service.getInfoAfterLog(userName,password));
    }

    private  void exitButtonPressed(ActionEvent event){
        loginStage.close();
    }

    private void close(){
        loginStage.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loginButton.setOnAction(this::loginButtonPressed);
        buttonExit.setOnAction(this::exitButtonPressed);
        labelWrong.setVisible(false);
        loginButton.setDefaultButton(true);

        RequiredFieldValidator validator1 = new RequiredFieldValidator(),validator2 = new RequiredFieldValidator();

        validator1.setMessage("You should insert a user");
        validator2.setMessage("You should insert a password");

        textFieldUser.getValidators().add(validator1);

        textFieldUser.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue)return;
            textFieldUser.validate();
            labelWrong.setVisible(false);
        });

        textFieldPassword.getValidators().add(validator2);

        textFieldPassword.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue)return;
            labelWrong.setVisible(false);
            textFieldPassword.validate();
        });

        textFieldUser.textProperty().addListener(((observable, oldValue, newValue) -> {
            labelWrong.setVisible(false);
        }));

        textFieldPassword.textProperty().addListener(((observable, oldValue, newValue) -> {
            labelWrong.setVisible(false);
        }));

        anchorPane.setOnMousePressed(event->{
            xOffset = loginStage.getX() - event.getScreenX();
            yOffset = loginStage.getY() - event.getScreenY();
        });

        anchorPane.setOnMouseDragged(event ->{
            loginStage.setX(event.getScreenX() + xOffset);
            loginStage.setY(event.getScreenY() + yOffset);
        });
    }

    public LoginController(){
    }

    public void showLogin(){
        if(loginStage == null)constructStage();
        textFieldPassword.setText("");
        labelWrong.setVisible(false);
        loginStage.show();
        textFieldUser.requestFocus();
    }

    public void setService(ILoginService service) {
        this.service = service;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setLoginWindow(Parent loginWindow) {
        this.loginWindow = loginWindow;
    }

    public void logout(String  username){
        service.logout(username);
    }
}
