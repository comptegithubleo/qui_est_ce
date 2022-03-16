package generator_app;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CreatedObject {

    //first window fields
    private String theme;
    private String name;

    public CreatedObject(String name){
        this.name=name;
    }


    public String getName() {
        return name;
    }

    public String getTheme() {
        return theme;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

}
