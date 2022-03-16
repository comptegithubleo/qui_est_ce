package generator_app;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
/*import javafx.scene.Parent;
import javafx.scene.Scene;*/
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
//import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import app.IGlobalFunctions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

//CLASS------------------------------------------

public class SetAttributesGenCTRL implements IGlobalFunctions,Initializable {
    /*private Scene scene;
    private Stage stage;
    private Parent root;*/
    private int obNumber=0;

    //FXML Buttons attributes --
    @FXML
    private Button create;
    @FXML
    private Button removeButton;
    @FXML
    private Button addButton;
    @FXML
    private Text objectNameText;

    //FXML Field attributes --
    @FXML
    private TextField keyField;
    @FXML
    private TextField valueField;

    //FXML Table View --
        //tab
    @FXML
    private TableView<CreatedAttr> tView;
        //column
    @FXML
    private TableColumn<CreatedAttr, String> key;
    @FXML
    private TableColumn<CreatedAttr, String> value;

    //CLASS attributes
    private ObservableList<CreatedAttr> list = FXCollections.observableArrayList();


//Methods--------

        //Retrieve values--------
    public ObservableList<CreatedAttr> getList() {
        return list;
    }


        //Objects management--------
    public void createTheme(ActionEvent event){
        //TODO change name of button by next button
        obNumber++;
        if(NewThemeGenCTRL.getList().size() > obNumber){
            objectNameText.setText(NewThemeGenCTRL.getList().get(obNumber).getName());
            list.clear();
            tView.setItems(list);
        }
        else{
            //TODO create the JSON thanks to all the entered values and keys
            create.setText("Create JSON");
            System.out.println("Fin de list");
        }
        
    }


    public void addObject(ActionEvent event){
        //conditions of fields not being empty
        if ((keyField.getText() == null || keyField.getText().trim().isEmpty()) || ((valueField.getText() == null || valueField.getText().trim().isEmpty())))
        {
            //Error handling
           System.out.println("error : keyField or valueField is empty");
        }
        else{
             //we add the new object to the list of CreatedObjects
             list.add(new CreatedAttr(NewThemeGenCTRL.getList().get(obNumber).getName(),keyField.getText(), valueField.getText()));

             //we add the two values to the tab column
             value.setCellValueFactory(new PropertyValueFactory<CreatedAttr, String>("value"));
             key.setCellValueFactory(new PropertyValueFactory<CreatedAttr, String>("key"));
             keyField.clear();
             valueField.clear();
 
             //we add the two values the the tab view
             tView.setItems(list);
        }
    }


    public void removeObject(ActionEvent event){
        int row = tView.getSelectionModel().getSelectedIndex();
        if(row>=0){
            //Supress table and object (because they are linked together)
            tView.getItems().remove(row);

        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        objectNameText.setText(NewThemeGenCTRL.getList().get(obNumber).getName());
        
    }
}