import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OTF {

    private String id;
    private String src;
    private ArrayList<String> attribute;

    public OTF(){}

    /*public OTF(String id, String src, ArrayList<String> attribute){
        this.id = id;
        this.src = src;
        this.attribute = attribute;
    } commented but usable to manually create an instance*/

    public String toString(){
        return "je suis " + this.id + " je lance le test"; 
    }
    
    public void setid(String id){
        this.id = id;
    }

    public void setsrc(String src){
        this.src = src;
    }

    public void setattribute(ArrayList<String> attribute){
        this.attribute = attribute;
    }

    public void showAttribute(){
        attribute.forEach(System.out::println);
    }

}
