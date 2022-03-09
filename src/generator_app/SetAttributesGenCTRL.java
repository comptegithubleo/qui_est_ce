package generator_app;

//IMPORTS
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.text.FieldView;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import app.IGlobalFunctions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

//CLASS------------------------------------------

public class SetAttributesGenCTRL {

    @FXML
    Button addButton;
    @FXML
    Button removeButton;

    @FXML
    TextField keyField;
    @FXML
    TextField valueField;

    

    public void addObjects(){

    }
}