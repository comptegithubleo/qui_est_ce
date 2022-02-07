import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OTF {

    private String id;
    private String source;
    private ArrayList<String> attribute;

    public OTF(){}

    /*public OTF(String id, String source, ArrayList<String> attribute){
        this.id = id;
        this.source = source;
        this.attribute = attribute;
    } commented but usable to manually create an instance*/

    public String toString(){
        return "je suis " + this.id + " je lance le test"; 
    }
    
    public void setid(String id){
        this.id = id;
    }

    public void setsource(String source){
        this.source = source;
    }

    public void setattribute(ArrayList<String> attribute){
        this.attribute = attribute;
    }

    public void showAttribute(){
        attribute.forEach(System.out::println);
    }

}
