package generator_app;


import javafx.fxml.FXML;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.controlsfx.control.textfield.TextFields;

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
	private ImageView image;

    //CLASS attributes
    private ObservableList<ObservableAttribute> list = FXCollections.observableArrayList();
    private HashMap<String, String> allAttrList = new HashMap<String, String>();


//Methods--------

	public void initialize() {
		objectNameText.setText(NewThemeGenCTRL.getList().get(obNumber).getId());
		updateImage();
		populateAttributes();
		loadAttributes();
	}

	public void updateImage()
	{
		String imagePath = NewThemeGenCTRL.getList().get(obNumber).getSrc();
		setImageView(imagePath, 300, 300, image);
	}
        //Retrieve values--------
    public ObservableList<ObservableAttribute> getList() {
        return list;
    }


        //Objects management--------
    public void nextObject(ActionEvent event){
        obNumber++;
        if(NewThemeGenCTRL.getList().size() > obNumber){
            objectNameText.setText(NewThemeGenCTRL.getList().get(obNumber).getId());
            for (ObservableAttribute o : list)
			{
				allAttrList.put(o.getKey(), o.getValue());
			}
			TextFields.bindAutoCompletion(keyField, allAttrList.keySet());
            list.clear();
            tView.setItems(list);
            System.out.println(allAttrList);
			updateImage();
			loadAttributes();
        }
        else{

            if(finish){
                for (ObservableAttribute o : list)
				{
					allAttrList.put(o.getKey(), o.getValue());
				}
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
						nextButton.setDisable(true);
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

	public void populateAttributes()
	{
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(Paths.get("files/sheet/gen_tmp.json").toFile());
			
			for (int i = 0; i < NewThemeGenCTRL.getList().size(); i++)
			{
				rootNode.path("objects").get(i).at("/attributes").fields().forEachRemaining(attribute -> {
					allAttrList.put(attribute.getKey(), attribute.getValue().asText());
				});
			}
			TextFields.bindAutoCompletion(keyField, allAttrList.keySet());

		} catch (IOException e) {
			e.printStackTrace();
		}

		keyField.textProperty().addListener(e -> {
			TextFields.bindAutoCompletion(valueField, allAttrList.get(keyField.getText()));
		});
	}
	
	public void loadAttributes()
	{
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode rootNode = mapper.readTree(Paths.get("files/sheet/gen_tmp.json").toFile());

			rootNode.path("objects").get(obNumber).at("/attributes").fields().forEachRemaining(attribute -> {
					list.add(new ObservableAttribute(NewThemeGenCTRL.getList().get(obNumber).getId(), attribute.getKey(), attribute.getValue().asText()));
					//we add the attributes to a hashmap in a CreatedObject calss.
					NewThemeGenCTRL.getList().get(obNumber).addAttributes(attribute.getKey(), attribute.getValue().asText());
					
					//we add the two values to the tab column
					value.setCellValueFactory(new PropertyValueFactory<ObservableAttribute, String>("value"));
					key.setCellValueFactory(new PropertyValueFactory<ObservableAttribute, String>("key"));
					keyField.clear();
					valueField.clear();
					
					//we are displaying the attribtues
					tView.setItems(list);
			});
					
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

            mapper.writeValue(Paths.get("files/sheet/gameset.json").toFile(), rootNode);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        nextButton.setCursor(Cursor.DEFAULT);

    }

	public void switchScene_Menu(ActionEvent event) throws IOException {
		NewThemeGenCTRL.getList().clear();
		tView.getItems().clear();
		switch_scene(event, "/app/Menu", stage, scene, false);
	}
}