package Controlers;

import Domain.Employee;
import Domain.Participation;
import Network.IClientProxy;
import Service.IParticipationService;
import Utils.Observer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable, Observer{


    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView<Participation> tableView;

    @FXML
    private TableColumn <Participation,String> tableColumnName;

    @FXML
    private TableColumn <Participation,String> tableColumnDate;

    @FXML
    private TableColumn <Participation,String> tableColumnLocation;

    @FXML
    private TableColumn <Participation,String> tableColumnSeatsAv;

    @FXML
    private TableColumn <Participation,String> tableColumnSeatsB;

    @FXML
    private JFXButton buttonSearch;

    @FXML
    private JFXButton buttonSell;

    @FXML
    private JFXButton buttonLogout;

    @FXML
    private Label labelUser;

    @FXML
    private JFXButton buttonClose;

    @FXML
    private StackPane stackPane;

    private Employee employee;


    private JFXDialogLayout layout = new JFXDialogLayout();
    private JFXDialog dialog;

    private PopOver searchPopOver = new PopOver(),sellPopOver = new PopOver();

    private IParticipationService participationService;

    private Stage mainWindowStage = new Stage(),ownerStage;
    private Parent mainWindowView;
    private LoginController loginController;
    private SaleController saleController;

    private  double xOffset,yOffset;


    private ObservableList <Participation> list = FXCollections.observableArrayList();


    private void showOrHideTable(PopOver popOver){

        if(popOver.isShowing()) {
            tableView.setDisable(true);return;
        }

        tableView.setDisable(false);
    }

    private void buttonSearchAction(ActionEvent event){
        searchPopOver.show(buttonSearch);
    }

    private void buttonSellAction(ActionEvent event){
        saleController.setEmployee(employee);
        sellPopOver.show(buttonSell);
        saleController.reloadContent();
    }

    private void logOutAction(ActionEvent event){
        dialog.close();
        closeMainWindow();
        loginController.showLogin();
    }

    private void initAllComponents(){
        mainWindowStage.initOwner(ownerStage);
        mainWindowStage.initStyle(StageStyle.UNDECORATED);
        mainWindowStage.setResizable(false);
        Scene scene = new Scene(mainWindowView);
        scene.getStylesheets().add("/style.css");
        mainWindowStage.setScene(scene);
    }

    private void closeMainWindow(){
        loginController.logout(labelUser.getText());
        mainWindowStage.close();
    }


    void showMainWindow(Employee employee, LoginController loginController){

        this.employee = employee;
        labelUser.setText(employee.getUserName());

        if(this.loginController == null)initAllComponents();

        this.loginController = loginController;

        mainWindowStage.show();

        reloadContent();
    }


    public void setSaleController(SaleController saleController) {
        this.saleController = saleController;
    }

    public void setParticipationService(IParticipationService participationService) {
        this.participationService = participationService;
        ((IClientProxy)participationService).addObserver(this);
    }


    public void setOwnerStage(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

    public void setMainWindowView(Parent mainWindowView) {
        this.mainWindowView = mainWindowView;
    }

    public void setSearchWindow(Parent parent){
        searchPopOver.setContentNode(parent);
    }

    public void setSellWindow(Parent parent){
        sellPopOver.setContentNode(parent);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mainWindowStage.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if(!tableView.isDisable())return;
            tableView.setDisable(false);
        }));

        tableColumnName.setCellValueFactory(
                x-> new SimpleStringProperty(x.getValue().getArtist().getNume())
        );

        tableColumnDate.setCellValueFactory(
                x->new SimpleStringProperty(x.getValue().getShow().getSeTinePe().toString())
        );

        tableColumnLocation.setCellValueFactory(
                x->new SimpleStringProperty(x.getValue().getShow().getLocatie())
        );

        tableColumnSeatsAv.setCellValueFactory(
                x->new SimpleStringProperty(x.getValue().getShow().getDisponibile()+"")
        );

        tableColumnSeatsB.setCellValueFactory(
                x->new SimpleStringProperty(x.getValue().getShow().getVandute()+"")
        );

        tableView.setItems(list);

        tableView.setRowFactory(row->new TableRow<Participation>(){
            @Override
            protected void updateItem(Participation item, boolean empty){
                super.updateItem(item,empty);

                if(empty || item == null || item.getShow().getDisponibile() > 0){
                    setStyle("");
                    return;
                }

                setStyle("-fx-background-color: #ff6976");
            }
        });

        buttonLogout.setOnAction(x->dialog.show());

        buttonSearch.setOnAction(this::buttonSearchAction);

        buttonSell.setOnAction(this::buttonSellAction);

        searchPopOver.setArrowLocation(PopOver.ArrowLocation.LEFT_TOP);

        searchPopOver.setDetachable(false);

        sellPopOver.setArrowLocation(PopOver.ArrowLocation.LEFT_CENTER);

        sellPopOver.setDetachable(false);

        mainWindowStage.setOnCloseRequest(x->{
            searchPopOver.hide(Duration.millis(0));
            sellPopOver.hide(Duration.millis(0));
        });


        sellPopOver.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            showOrHideTable(sellPopOver);
        }));


        searchPopOver.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            showOrHideTable(searchPopOver);
        }));


        anchorPane.setOnMousePressed(event -> {
            xOffset = mainWindowStage.getX() - event.getScreenX();
            yOffset = mainWindowStage.getY() - event.getScreenY();
        });

        anchorPane.setOnMouseDragged(event -> {
            mainWindowStage.setX(event.getScreenX() + xOffset);
            mainWindowStage.setY(event.getScreenY() + yOffset);
        });

        buttonClose.setOnAction(x->this.closeMainWindow());

        dialog = new JFXDialog(stackPane,layout,JFXDialog.DialogTransition.CENTER);

        layout.setHeading(new Text("Question"));

        layout.setBody(new Text("Are you sure you want to log out?"));

        JFXButton buttonYes = new JFXButton("Yes"),buttonNo = new JFXButton("No");

        buttonYes.setOnAction(this::logOutAction);
        buttonNo.setOnAction(x->dialog.close());

        layout.setActions(buttonYes,buttonNo);
    }

    @Override
    public void reloadContent(){
        list.setAll(participationService.getAllParticip());
    }


}




