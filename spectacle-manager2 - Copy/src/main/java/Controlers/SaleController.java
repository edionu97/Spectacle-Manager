package Controlers;

import Domain.Employee;
import Domain.Show;
import Network.IClientProxy;
import Service.ISaleService;
import Utils.Observer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class SaleController implements Initializable, Observer {

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXTextField textFieldName;

    @FXML
    private JFXTextField textFieldSeats;

    @FXML
    private JFXButton buttonFinish;

    @FXML
    private TableView <Show> tableViewShows;

    @FXML
    private TableColumn<Show,String> tableColumnDate;

    @FXML
    private TableColumn <Show,String> tableColumnStart;

    @FXML
    private TableColumn <Show,String> tableColumnLoc;

    @FXML
    private TableColumn <Show,String> tableColumnAv;

    @FXML
    private TableColumn <Show,String> tableColumnSol;

    @FXML
    private TableColumn <Show,String> tableColumnId;


    private ObservableList <Show> model = FXCollections.observableArrayList();
    private JFXDialogLayout layout = new JFXDialogLayout();
    private ISaleService saleService;
    private Employee employee;
    private JFXDialog dialog;

    private void finishAction(ActionEvent event){

        Show show = tableViewShows.getSelectionModel().getSelectedItem();

        if(show == null){
            layout.setBody(new Text("You must select a show in order to finish the transaction"));
            dialog.show();
            return;
        }

        if(show.getDisponibile() <= 0){
            layout.setBody(new Text("There are no available  places anymore to the selected show"));
            dialog.show();
            return;
        }

        try{

            saleService.addVanzare(textFieldName.getText(),Integer.parseInt(textFieldSeats.getText()), show.getCodS(), employee.getCodAg());

            layout.setBody(new Text("Transaction successfully effectuated.\n" +
                    "The customer " + textFieldName.getText() + "" +
                    " has rented " +  textFieldSeats.getText() +
                    " seats")
            );

            layout.setHeading(new Text("Information"));

            dialog.show();
        }catch (Exception e){
            layout.setHeading(new Text("Error"));
            layout.setBody(new Text(e.getMessage()));
            dialog.show();
        }
    }

    public void setSaleService(ISaleService saleService) {
        this.saleService = saleService;
        ((IClientProxy)saleService).addObserver(this);
    }

    public void setEmployee(Employee employee){
        this.employee = employee;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableColumnDate.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getSeTinePe().toString()));
        tableColumnStart.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getIncepeLa().toString()));
        tableColumnLoc.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getLocatie()));
        tableColumnAv.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getDisponibile()+""));
        tableColumnSol.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getVandute()+""));
        tableColumnId.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getCodS()+""));

        dialog = new JFXDialog(stackPane,layout,JFXDialog.DialogTransition.CENTER);

        layout.setHeading(new Text("Error"));

        JFXButton button = new JFXButton("Okay");

        button.setOnAction(x->dialog.close());

        layout.setActions(button);

        buttonFinish.setVisible(false);

        tableViewShows.setRowFactory(tv->new TableRow<Show>(){
            @Override
            protected  void updateItem(Show item, boolean empty){

                super.updateItem(item,empty);

                if(empty || item == null || item.getDisponibile()  > 0){
                    setStyle("");
                    return;
                }
                setStyle("-fx-background-color: #ff6976");
            }
        });

        tableViewShows.setItems(model);

        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

        requiredFieldValidator.setMessage("Empty field");

        NumberValidator numberValidator = new NumberValidator();

        numberValidator.setMessage("Not number");

        textFieldName.getValidators().add(requiredFieldValidator);

        textFieldSeats.getValidators().addAll(numberValidator);

        textFieldSeats.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if(!newValue)textFieldSeats.validate();

            if( textFieldSeats.getText().equals("") || textFieldName.getText().equals("") == true ){
                buttonFinish.setVisible(false);
                return;
            }

            buttonFinish.setVisible(true);
        }));

        textFieldName.focusedProperty().addListener(((observable, oldValue, newValue) -> {
           if(!newValue) textFieldName.validate();

           if(textFieldName.getText().equals("") || textFieldSeats.getText().equals("")){
               buttonFinish.setVisible(false);
               return;
           }

           buttonFinish.setVisible(true);

        }));

        buttonFinish.setOnAction(this::finishAction);
    }

    @Override
    public void reloadContent(){
        model.setAll(saleService.getSpec());
    }
}
