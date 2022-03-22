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

import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import app.IGlobalFunctions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


//CLASS------------------------------------------

public class SetAttributesGenCTRL implements IGlobalFunctions,Initializable {
    /*private Scene scene;
    private Stage stage;
    private Parent root;*/
    private int obNumber=0;
    private boolean finish=true;

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
    private ObservableList<CreatedAttr> allAttrList = FXCollections.observableArrayList();


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
            allAttrList.addAll(list);
            list.clear();
            tView.setItems(list);
            System.out.println("coucou");
            System.out.println(allAttrList);

        }
        else{

            if(finish){
                allAttrList.addAll(list);
                System.out.println("On y passe !!");
                System.out.println(allAttrList);
                finish=false;
            }
            
            //TODO create the JSON thanks to all the entered values and keys
            create.setText("Create JSON");

            create.setOnAction( new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Object source = e.getSource();
                    if (source instanceof Button) {
                        //new method name
                        createJson();
                    }
                }
            });

            System.out.println("Fin de list");
            addButton.setDisable(true);
            removeButton.setDisable(true);
            
        }
        
    }

    public void createJson(){
        

    }


    public void addObject(ActionEvent event){
        //TODO change name to "addAttribute" !
        //conditions of fields not being empty
        if ((keyField.getText() == null || keyField.getText().trim().isEmpty()) || ((valueField.getText() == null || valueField.getText().trim().isEmpty())))
        {
            //Error handling
           System.out.println("error : keyField or valueField is empty");
        }
        else{
             //we add the new attribute to the list of object of lists
             list.add(new CreatedAttr(NewThemeGenCTRL.getList().get(obNumber).getName(),keyField.getText(), valueField.getText()));
             
             //we add the attributes to a hashmap in a CreatedObject calss.
             NewThemeGenCTRL.getList().get(obNumber).addAttributes(keyField.getText(), valueField.getText());


             //we add the two values to the tab column
             value.setCellValueFactory(new PropertyValueFactory<CreatedAttr, String>("value"));
             key.setCellValueFactory(new PropertyValueFactory<CreatedAttr, String>("key"));
             keyField.clear();
             valueField.clear();
 
             //we are displaying the attribtues
             tView.setItems(list);
        }
    }


    public void removeObject(ActionEvent event){
        int row = tView.getSelectionModel().getSelectedIndex();
        if(row>=0){
            //Supress table and object (because they are linked together)
            System.out.println("avant supp : " + NewThemeGenCTRL.getList().get(row).toString() );
            NewThemeGenCTRL.getList().get(row).removeAttribute(tView.getItems().get(row).getKey());
            tView.getItems().remove(row);
            System.out.println("après supp : " + NewThemeGenCTRL.getList().get(row).toString() );

            
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        objectNameText.setText(NewThemeGenCTRL.getList().get(obNumber).getName());
        
    }
}