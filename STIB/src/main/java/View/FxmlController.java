/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.controlsfx.control.SearchableComboBox;

import Model.StationNameDto;
import Presenter.Presenter;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.VBox;

import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 *
 * @author 56035
 */
public class FxmlController implements Initializable{
    
    @FXML
    private SearchableComboBox origin_box;
    
    @FXML
    private Label label_origin;
    
    @FXML
    private Label label_dest;
    
    @FXML
    private SearchableComboBox dest_box;
    
    @FXML 
    private TableView table;
    
    @FXML
    private ImageView logo;
    
    @FXML
    private ImageView plan;
    
    private Presenter presenter;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        
        InputStream streamlogo = this.getClass().getResourceAsStream("/images/logo.png");
        Image logoimage = new Image(streamlogo);
        this.logo.setImage(logoimage);
        
        InputStream streammetro = this.getClass().getResourceAsStream("/images/metro.gif");
        Image metroimage = new Image(streammetro);
        this.plan.setImage(metroimage);
        

        table.getColumns().clear();
        
        TableColumn<TableRow, String> column1 = new TableColumn<>("Station");   
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getColumns().add(column1);
        TableColumn<TableRow, String> column2 = new TableColumn<>("Lignes");   
        column2.setCellValueFactory(new PropertyValueFactory<>("stops"));
        table.getColumns().add(column2);

        this.presenter = new Presenter(this);
        List<StationNameDto> names = this.presenter.getNames();
        for(StationNameDto s : names){
            this.table.getItems().add(new TableRow(s.getName(), s.getLignes()));
        }
        
        ObservableList<String> namesString= FXCollections.observableArrayList();
        for(StationNameDto s : names){
            namesString.add(s.getName());
        }
        //this.origin_box = new SearchableComboBox(namesString);
        this.origin_box.setItems(namesString);
        this.dest_box.setItems(namesString);
        

        
       
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
        if(this.origin_box.getValue() == null || this.dest_box.getValue() == null){
            System.out.println("Veuillez entrer des valeurs");
            return;
        }
        List<StationNameDto> shortestWay = this.presenter.makeWays(this.origin_box.getValue().toString(), this.dest_box.getValue().toString());
        Stage popUp = new Stage();
        VBox texts = new VBox(20);
        texts.getChildren().add(new Text("The shortest way has " + shortestWay.size() + " stops. Here they are in order :"));
        String string ="";
        for(int i = 0; i<shortestWay.size(); i++){
            if(i%4==0 && i!=0){
                texts.getChildren().add(new Text(string));
                string = "";
            }
            string += " => " + shortestWay.get(i).getName();
          
        }
        texts.getChildren().add(new Text(string));
        
        
        Scene popUpScene = new Scene(texts, 600, 100 + shortestWay.size()*10);
        popUp.setScene(popUpScene);
        popUp.show();

    }
    
    public FxmlController(){
        
    }
    
    public void initialize(){
        
    }
}
