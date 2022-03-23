package generator_app;

import javafx.fxml.FXML;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import app.IGlobalFunctions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

//CLASS------------------------------------------

public class NewThemeGenCTRL implements IGlobalFunctions {
    private Scene scene;
    private Stage stage;
    private static String theme;
    //private Parent root;

    //FXML Buttons attributes --
    @FXML
    private Button nextButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button addButton;

    //image handling
    @FXML
    private Button imgSelector;
    @FXML
    private ImageView imageView;
    private String filePath;


    //FXML Field attributes --
    @FXML
    private TextField objectNameField;
    @FXML
    private TextField themeName;
    @FXML private TextField srcField;

    //FXML Table View --
        //tab
    @FXML
    private TableView<CreatedObject> tView;
        //column
    @FXML
    private TableColumn<CreatedObject, String> objects;

    //CLASS attributes
    private static ObservableList<CreatedObject> list = FXCollections.observableArrayList();

    //Methods--------

        //Retrieve values--------
    public static ObservableList<CreatedObject> getList() {
        return list;
    }

    public static String getTheme() {
        return theme;
    }

        //Objects management--------
    public void saveObjects(ActionEvent event) throws IOException{
        if (themeName.getText() == null || themeName.getText().trim().isEmpty()) {
            System.out.println("error no theme name");
       }
       else{
           theme=themeName.getText();
           switch_scene_title(event, "SetAttributesGen", "Set Attributes", stage, scene);
           
       }
    }

    public void addObject(ActionEvent event){
        if (objectNameField.getText() == null || objectNameField.getText().trim().isEmpty() || filePath.isEmpty() || filePath == null) {
           System.out.println("error no objects id");
        }
        else{

             //we add the new object to the list of CreatedObjects
             CreatedObject coTemp= new CreatedObject(objectNameField.getText(), filePath);
             list.add(coTemp);
             System.out.println();

             //we add the two values to the tab column
             objects.setCellValueFactory(new PropertyValueFactory<CreatedObject, String>("id"));
             objectNameField.clear();
             imageView.setImage(null);
 
             //we add the two values the the tab view
             tView.setItems(list);
        }
    }

    public void selectImage(ActionEvent event){
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);

        if(selectedFile!=null){
            filePath = selectedFile.getAbsolutePath();

            //display image
            Image img = new Image(selectedFile.toURI().toString());
            imageView.setImage(img);
        }
        else{
            System.out.println("Wrong file !");
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