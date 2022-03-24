package generator_app;

import java.util.HashMap;

public class CreatedObject {

    private String id;
    private String src;
    HashMap<String, String> attributes= new HashMap<>();

    //GETTERS AND SETTERS
    public String getSrc() {
        return src;
    }
    public void setSrc(String src) {
        this.src = src;
    }
    public CreatedObject(String id, String src){
        this.id=id;
        this.src=src;
    }
    public String getId() {
      return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }
    public HashMap<String, String> getAttributes() {
        return attributes;
    }
    //METHODS
    public void addAttributes(String key, String value) {
        this.attributes.put(key, value);
    }

    public void removeAttribute(String key){
        this.attributes.remove(key);
    }

    //TO STRING METHOD
    @Override
    public String toString() {
        return "id : "+id+ " attributes : "+this.attributes;
    }

}
