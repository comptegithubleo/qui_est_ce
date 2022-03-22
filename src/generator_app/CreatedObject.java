package generator_app;

import java.util.ArrayList;
import java.util.HashMap;

public class CreatedObject {

    //first window fields
    private String theme;
    private String name;
    HashMap<String, String> attributes= new HashMap<>();

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

    public void addAttributes(String key, String value) {
        this.attributes.put(key, value);
    }

    public void removeAttribute(String key){
        this.attributes.remove(key);
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "theme :"+ theme + "name : "+name+ " attributes : "+this.attributes;
    }

}
