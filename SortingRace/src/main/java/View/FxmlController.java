
package View;
import Model.LogsDto;
import java.net.URL;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ProgressBar;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import Model.Model;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;



public class FxmlController implements Initializable{

    
    @FXML
    private ChoiceBox sortChoice;
    
    @FXML
    private Button start;
    
    @FXML
    private Spinner threadSpinner;
    
    @FXML
    private ChoiceBox configurationChoice;
    
    @FXML
    private ProgressBar progressBar;
    
    @FXML
    private LineChart chart;
    
    @FXML
    private TableView table;
    
    @FXML
    private Label leftStatus;
    
    @FXML
    private Label rightStatus;
    private Model model;


    /**
     * What to do on click, calls the model methods and gives it the inputs that the user used.
     * @param event 
     */
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
        this.progressBar.setProgress(0);
        String sort = "";
        if(sortChoice.getValue() == null){
            sort = "Bubble sort";
        }
        else{
            sort = (String) sortChoice.getValue();
        }
        

        int numberThread = (int)threadSpinner.getValue();
        
        leftStatus.setText(String.valueOf(numberThread));
        rightStatus.setText(String.valueOf(java.time.LocalDateTime.now()));
        if(configurationChoice.getSelectionModel().getSelectedItem()==null){
            this.model.start(numberThread, 100, sort);
        }
        else{
            

            String numberElems = configurationChoice.getSelectionModel().getSelectedItem().toString();
            int numberElemsInt = 0;
            if(numberElems.charAt(0) == 'V'){
                numberElemsInt = 100;
            }
            else if (numberElems.charAt(0) == 'E'){
                numberElemsInt = 1000;
            }
            else if (numberElems.charAt(0) == 'M'){
                numberElemsInt = 10000;
            }
            else if (numberElems.charAt(0) == 'H'){
                if (numberElems.charAt(1) == 'a'){
                    numberElemsInt = 100000;
                }
                else if (numberElems.charAt(1) == 'e'){
                    numberElemsInt = 1000000;
                }
            }


            this.model.start(numberThread, numberElemsInt, sort);
        }
}

    public FxmlController() {
        System.out.println("");
       
    }

    /**
     * Initializes everything we will need during the view
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        start.setText("Calculate");
        sortChoice.getItems().add("Bubble sort");
        sortChoice.getItems().add("Merge sort");
        
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        threadSpinner.setValueFactory(valueFactory);
        
        configurationChoice.getItems().add("Very Easy : 0 - 100");
        configurationChoice.getItems().add("Easy : 0 - 1 000");
        configurationChoice.getItems().add("Medium : 0 - 10 000");
        configurationChoice.getItems().add("Hard : 0 - 100 000");
        configurationChoice.getItems().add("Hell : 0 - 1 000 000");
        this.model = new Model();
        this.model.subscribe(this);
        
        table.getColumns().clear();
        
        TableColumn<TableRow, String> column1 = new TableColumn<>("Tri");   
        column1.setCellValueFactory(new PropertyValueFactory<>("tri"));
        table.getColumns().add(column1);
        TableColumn<TableRow, String> column2 = new TableColumn<>("Taille");   
        column2.setCellValueFactory(new PropertyValueFactory<>("taille"));
        table.getColumns().add(column2);
        TableColumn<TableRow, String> column3 = new TableColumn<>("Operations");   
        column3.setCellValueFactory(new PropertyValueFactory<>("operation"));
        table.getColumns().add(column3);
        TableColumn<TableRow, String> column4 = new TableColumn<>("Durée (millis)");   
        column4.setCellValueFactory(new PropertyValueFactory<>("duree"));
        table.getColumns().add(column4);
       
    }

    public void initialize() {

        
        System.out.println("");
    }
    
    /**
     * Updates the view depending on what the model gave us
     * @param operations
     * @param duree
     * @param tri
     * @param taille 
     */
    public void update(List<Integer> operations, List<Integer> duree, String tri, int[] taille){
        XYChart.Series series =  new XYChart.Series();
        series.setName(tri);
        int min_size = 0;
        if(operations.size()>duree.size()){
            min_size = duree.size();
        }
        else{
            min_size = operations.size();
        }
        
        for(int i = 0; i<min_size;i++){
            series.getData().add(new XYChart.Data(taille[i],operations.get(i)));
           
            table.getItems().add(new TableRow(tri, taille[i], operations.get(i), duree.get(i)));
        }
        this.chart.getData().add(series);
    }
    
    public void updateProgress(int progress){
        this.progressBar.setProgress(progress*10);
    }
    
    /**
     * Used to make a pop up and fill it's tableView with informations about the logs
     */
    @FXML
    public void printLogs(){
        List<LogsDto> dto = this.model.getLogs(); //pas propre du tout mais pas le temps de faire autrement
        
        
        Stage popUp = new Stage();
        TableView tableLogs = new TableView();
        
        TableColumn<LogsDto, String> column1 = new TableColumn<>("Timestamp");   
        column1.setCellValueFactory(new PropertyValueFactory<>("time"));
        tableLogs.getColumns().add(column1);
        TableColumn<LogsDto, String> column2 = new TableColumn<>("Sort Type");   
        column2.setCellValueFactory(new PropertyValueFactory<>("sort"));
        tableLogs.getColumns().add(column2);
        TableColumn<LogsDto, String> column3 = new TableColumn<>("Max Size");   
        column3.setCellValueFactory(new PropertyValueFactory<>("size"));
        tableLogs.getColumns().add(column3);
       
        for(LogsDto l : dto){
            tableLogs.getItems().add(new LogsDto(l.getTime(), l.getSort(), l.getSize()));
        }
        
        Scene popUpScene = new Scene(tableLogs, 450, 600);
        popUp.setScene(popUpScene);
        popUp.show();
    }
}
