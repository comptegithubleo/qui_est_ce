package generator_app;

import javafx.fxml.FXML;
//import javafx.scene.Parent;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.OTF;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
	@FXML
	private Button back_to_menu;

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

	public void initialize() throws IllegalArgumentException, IOException
	{
		//if new gen, delete old tmp save. Else, load save
		if (SelectionGeneratorController.getIsNewGen())
		{
			File file = new File("files/sheet/gen_tmp.json");
			file.delete();
			System.out.println("deleted");
		}
		else {
			loadTMP();
		}
	}

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
		if (filePath == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Object has no image, please provide one");
			alert.showAndWait();
        }
		else if (objectNameField.getText() == null)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Object has no name, please provide one");
			alert.showAndWait();
		}
        else{

             //we add the new object to the list of CreatedObjects
             CreatedObject coTemp= new CreatedObject(objectNameField.getText(), filePath);
             list.add(coTemp);
             System.out.println("added");

             //we add the two values to the tab column
             objects.setCellValueFactory(new PropertyValueFactory<CreatedObject, String>("id"));
             objectNameField.clear();
             imageView.setImage(null);
 
             //we add the two values the the tab view
             tView.setItems(list);

			 saveTMP();
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
        
		saveTMP();
    }

	public void loadTMP() throws IllegalArgumentException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		JsonNode tmp_save = mapper.readTree(Paths.get("files/sheet/gen_tmp.json").toFile());
		List <OTF> otfs = Arrays.asList(mapper.treeToValue(tmp_save.get("objects"), OTF[].class));
		for (OTF otf : otfs)
		{
			CreatedObject coTemp= new CreatedObject(otf.getid(), otf.getsrc());
			NewThemeGenCTRL.list.add(coTemp);
			objects.setCellValueFactory(new PropertyValueFactory<CreatedObject, String>("id"));
		}
		tView.getItems().addAll(NewThemeGenCTRL.list);
		themeName.setText(tmp_save.at("/theme").asText());
	}

	public void saveTMP()
	{
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode jsonNode = mapper.createObjectNode();
		
		((ObjectNode)jsonNode).put("theme" , this.themeName.getText());

		ArrayNode listConvert = mapper.valueToTree(NewThemeGenCTRL.list);
		((ObjectNode)jsonNode).set("objects", listConvert);

		try {
			mapper.writeValue(Paths.get("files/sheet/gen_tmp.json").toFile(), jsonNode);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void switchScene_Menu(ActionEvent event) throws IOException {
		list.clear();
		switch_scene(event, "../app/Menu", stage, scene, false);
	}
}