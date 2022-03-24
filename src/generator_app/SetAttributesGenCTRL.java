package generator_app;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
/*import javafx.scene.Parent;
import javafx.scene.Scene;*/
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
//import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
    private Button nextButton;
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
    private TableView<ObservableAttribute> tView;
        //column
    @FXML
    private TableColumn<ObservableAttribute, String> key;
    @FXML
    private TableColumn<ObservableAttribute, String> value;

    @FXML
    private AnchorPane anchPane;

    //CLASS attributes
    private ObservableList<ObservableAttribute> list = FXCollections.observableArrayList();
    private ObservableList<ObservableAttribute> allAttrList = FXCollections.observableArrayList();


//Methods--------

        //Retrieve values--------
    public ObservableList<ObservableAttribute> getList() {
        return list;
    }


        //Objects management--------
    public void nextObject(ActionEvent event){
        obNumber++;
        if(NewThemeGenCTRL.getList().size() > obNumber){
            objectNameText.setText(NewThemeGenCTRL.getList().get(obNumber).getId());
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

            nextButton.setText("Create JSON");

            nextButton.setOnAction( new EventHandler<ActionEvent>() {
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


    public void addAttribute(ActionEvent event){
        //conditions of fields not being empty
        if ((keyField.getText() == null || keyField.getText().trim().isEmpty()) || ((valueField.getText() == null || valueField.getText().trim().isEmpty())))
        {
            //Error handling
           System.out.println("error : keyField or valueField is empty");
        }
        else{
             //we add the new attribute to the list of object of lists
             list.add(new ObservableAttribute(NewThemeGenCTRL.getList().get(obNumber).getId(),keyField.getText(), valueField.getText()));
             
             //we add the attributes to a hashmap in a CreatedObject calss.
             NewThemeGenCTRL.getList().get(obNumber).addAttributes(keyField.getText(), valueField.getText());


             //we add the two values to the tab column
             value.setCellValueFactory(new PropertyValueFactory<ObservableAttribute, String>("value"));
             key.setCellValueFactory(new PropertyValueFactory<ObservableAttribute, String>("key"));
             keyField.clear();
             valueField.clear();
 
             //we are displaying the attribtues
             tView.setItems(list);
        }
    }


    public void removeAttribute(ActionEvent event){

        int row = tView.getSelectionModel().getSelectedIndex();
        if(row>=0){
            NewThemeGenCTRL.getList().get(obNumber).removeAttribute(tView.getItems().get(row).getKey());
            tView.getItems().remove(row);
        }
    }

    public synchronized void createJson(){
        //mouse loading mode :
        nextButton.setCursor(Cursor.WAIT);

        //creating JSON
        System.out.println("fonction JSON activé");
        ObjectMapper mapper= new ObjectMapper();
        File file = new File("files/sheet/gameset.json");

        JsonNode newNode = mapper.createObjectNode();
        try{
            JsonNode rootNode = mapper.readTree(file);
        
            ArrayList<CreatedObject> arr=new ArrayList<>();
            for(CreatedObject co:NewThemeGenCTRL.getList()){
                arr.add(co);
            }

            ArrayNode arrNode = mapper.valueToTree(arr);
            ((ObjectNode)newNode).set("objects", arrNode);

            //adding newNode the the rootNode
            ((ObjectNode)(rootNode).path("theme")).set(NewThemeGenCTRL.getTheme(), newNode);

            mapper.writeValue(Paths.get("files/sheet/gameset1.json").toFile(), rootNode);
            //TODO change the path to "files/sheet/gameset.json" for it can ovewrite gameset.json file.
        }
        catch(IOException e){
            e.printStackTrace();
        }
        nextButton.setCursor(Cursor.DEFAULT);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        objectNameText.setText(NewThemeGenCTRL.getList().get(obNumber).getId());
        
    }
}