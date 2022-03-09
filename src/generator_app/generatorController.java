package generator_app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.text.FieldView;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import app.IGlobalFunctions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

//CLASS------------------------------------------

public class generatorController implements IGlobalFunctions {

    //FXML Buttons attributes --
    @FXML
    private Button saveButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button addButton;

    //FXML Field attributes --
    @FXML
    private TextField objectNameField;
    @FXML
    private TextField themeName;

    //FXML Table View --
        //tab
    @FXML
    private TableView<CreatedObject> tView;
        //column
    @FXML
    private TableColumn<CreatedObject, String> objects;

    //CLASS attributes
    protected ObservableList<CreatedObject> list = FXCollections.observableArrayList();


    //Methods--------

        //Retrieve values--------
    public ObservableList<CreatedObject> getList() {
        return list;
    }
    public String getThemeName() {
        return themeName.getText();
    }


        //Objects management--------
    public void saveObjects(ActionEvent event){
        if (themeName.getText() == null || themeName.getText().trim().isEmpty()) {
            System.out.println("error no theme name");
       }
    }

    public void addObject(ActionEvent event){
        if (objectNameField.getText() == null || objectNameField.getText().trim().isEmpty()) {
           System.out.println("error no objects name");
        }
        else{
             //we add the new object to the list of CreatedObjects
             list.add(new CreatedObject(objectNameField.getText()));

             //we add the two values to the tab column
             objects.setCellValueFactory(new PropertyValueFactory<CreatedObject, String>("name"));
             objectNameField.clear();
 
             //we add the two values the the tab view
             tView.setItems(list);
        }
    }

    public void removeObject(ActionEvent event){
        int row = tView.getSelectionModel().getSelectedIndex();
        
        if(row>=0){
            //Supress table and object because they are linked together
            tView.getItems().remove(row);
        }
        

    }
}