package Controlers;

import Domain.Participation;
import Service.IParticipationService;
import com.jfoenix.controls.JFXDatePicker;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class SearchController implements Initializable {


    @FXML
    private TableView<Participation> tableView;

    @FXML
    private TableColumn<Participation,String> tableColumnLocation;

    @FXML
    private TableColumn<Participation,String> tableColumnTime;


    @FXML
    private TableColumn<Participation,String> tableColumnAv;

    @FXML
    private TableColumn<Participation,String> tableColumnName;


    @FXML
    private JFXDatePicker datePicker;

    private IParticipationService service;

    private ObservableList <Participation> model = FXCollections.observableArrayList();


    private void filterEvent(ActionEvent event){
        if(service == null)return;
        model.setAll(service.filterByDate(Date.valueOf(datePicker.getValue())));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableView.setItems(model);

        tableColumnName.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getArtist().getNume()));

        tableColumnLocation.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getShow().getLocatie()));

        tableColumnTime.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getShow().getIncepeLa().toString()));

        tableColumnAv.setCellValueFactory(x->new SimpleStringProperty(x.getValue().getShow().getDisponibile()+""));

        datePicker.setOnAction(this::filterEvent);
    }

    public void setService(IParticipationService service) {
        this.service = service;
    }

}
