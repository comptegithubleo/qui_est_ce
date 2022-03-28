package generator_app;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;

import app.IGlobalFunctions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


//CLASS------------------------------------------

public class SetAttributesGenCTRL implements IGlobalFunctions {
    private Scene scene;
    private Stage stage;
    private Parent root;
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
	private Button back_to_menu;
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

    //Other FXML handle
    @FXML
    private AnchorPane anchPane;
    @FXML
    private ImageView obimg;

    //CLASS attributes
    private ObservableList<ObservableAttribute> list = FXCollections.observableArrayList();
    private ObservableList<ObservableAttribute> allAttrList = FXCollections.observableArrayList();


//Methods--------

	public void initialize() {
        Image img = new Image("file:"+NewThemeGenCTRL.getList().get(obNumber).getSrc());
        obimg.setImage(img);
        System.out.println(NewThemeGenCTRL.getList().get(obNumber).getSrc());
		objectNameText.setText(NewThemeGenCTRL.getList().get(obNumber).getId());
		loadAttributes();
	}
        //Retrieve values--------
    public ObservableList<ObservableAttribute> getList() {
        return list;
    }


        //Objects management--------
    public void nextObject(ActionEvent event){
        obNumber++;
        if(NewThemeGenCTRL.getList().size() > obNumber){
            Image img = new Image("file:"+NewThemeGenCTRL.getList().get(obNumber).getSrc());
            obimg.setImage(img);
            objectNameText.setText(NewThemeGenCTRL.getList().get(obNumber).getId());
            allAttrList.addAll(list);
            list.clear();
            tView.setItems(list);
            System.out.println(allAttrList);
			loadAttributes();
        }
        else{

            if(finish){
                allAttrList.addAll(list);
                System.out.println("On y passe !!");
                System.out.println(allAttrList);
                finish=false;
            }

            nextButton.setText("Create JSON");
            nextButton.setStyle("-fx-text-fill: red");

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
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error.");
            alert.setHeaderText(null);
            alert.setContentText("Please, make sure to type something in both key and value fields.");
            
            alert.showAndWait();
        }
        else{
            boolean existingvalue = false;
            for(ObservableAttribute o : list){
                if (keyField.getText().equals(o.getKey())){
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error.");
                    alert.setHeaderText(null);
                    alert.setContentText("Watch out ! \nYou already created this key for this object.");
            
                    alert.showAndWait();
                    existingvalue = true;
                }
            }
            if(!existingvalue){
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

				saveTMP();
            }
        }
    }

    public void removeAttribute(ActionEvent event){

        int row = tView.getSelectionModel().getSelectedIndex();
        if(row>=0){
            NewThemeGenCTRL.getList().get(obNumber).removeAttribute(tView.getItems().get(row).getKey());
            tView.getItems().remove(row);
			
			saveTMP();
        }
    }

	public void loadAttributes()
	{
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode rootNode = mapper.readTree(Paths.get("files/sheet/gen_tmp.json").toFile());
			
			for (JsonNode node : rootNode.path("objects").get(obNumber).get("attributes"))
			{
				list.add(new ObservableAttribute(NewThemeGenCTRL.getList().get(obNumber).getId(),node.textValue(), node.textValue()));
					
				//we add the attributes to a hashmap in a CreatedObject calss.
				NewThemeGenCTRL.getList().get(obNumber).addAttributes(node.textValue(), node.textValue());

				//we add the two values to the tab column
				value.setCellValueFactory(new PropertyValueFactory<ObservableAttribute, String>("value"));
				key.setCellValueFactory(new PropertyValueFactory<ObservableAttribute, String>("key"));
				keyField.clear();
				valueField.clear();

				//we are displaying the attribtues
				tView.setItems(list);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void saveTMP()
	{
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(Paths.get("files/sheet/gen_tmp.json").toFile());

			ArrayList<CreatedObject> arr=new ArrayList<>();
			for(CreatedObject co:NewThemeGenCTRL.getList()){
				arr.add(co);
			}
			
			ArrayNode arrNode = mapper.valueToTree(arr);

			((ObjectNode)(rootNode)).set("objects", arrNode);

			mapper.writeValue(Paths.get("files/sheet/gen_tmp.json").toFile(), rootNode);
		}
		catch(IOException e){
			e.printStackTrace();
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


	public void switchScene_Menu(ActionEvent event) throws IOException {
		list.clear();
		tView.getItems().clear();
		switch_scene(event, "../app/Menu", stage, scene, false);
	}
}