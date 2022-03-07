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
    private TextField valueField;
    @FXML
    private TextField keyField;

    //FXML Table View --
    @FXML
    private TableView<CreatedObject> tView;
    @FXML
    private TableColumn<CreatedObject, String> key;
    @FXML
    private TableColumn<CreatedObject, String> value;

    //CLASS attributes
    private int count=0;
    ObservableList<CreatedObject> list = FXCollections.observableArrayList();

    public void saveKeys(ActionEvent event){
        //TODO to create the JSON file that correspond.
    }

    public void addObject(ActionEvent event){

        //we add the new object to the list of CreatedObjects
        list.add(new CreatedObject(valueField.getText(), keyField.getText()));

        //we add the two values to the tab column
        key.setCellValueFactory(new PropertyValueFactory<CreatedObject, String>("key"));
        value.setCellValueFactory(new PropertyValueFactory<CreatedObject, String>("value"));

        //we add the two values the the tab view
        tView.setItems(list);
        
        //TODO suppress next two lines (used to see the instances)
        System.out.println(list);
        System.out.println(list.size());
        
    }

    public void removeObject(ActionEvent event){
        int row = tView.getSelectionModel().getSelectedIndex();
        
        //Supress table and object because they are linked together
        tView.getItems().remove(row);

        //TODO suppress next two lines (used to see the instances)
        System.out.println(list);
        System.out.println(row);
    }
}