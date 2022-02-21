import java.util.ArrayList;
import java.util.Random;


public class Plateau {

    private ArrayList<OTF> plateau;
    private OTF ITF;
    
    public Plateau(){

    }

    public void chooseITF(){
        Random rand = new Random();
        this.ITF = plateau.get(rand.nextInt(plateau.size()));
    }
    

    
}
