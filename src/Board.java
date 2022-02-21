import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Board {

    private ArrayList<OTF> board = new ArrayList<OTF>();
    private String theme;
    private OTF ITF;
    private int nbrofOTF;
    private List<Integer> size;
    
    public Board(List<OTF> givenboard, int nbrofOTF, int sizex, int sizey, String theme){

        this.nbrofOTF = nbrofOTF;
        this.theme = theme;
        this.size = Arrays.asList(sizex,sizey);
        
        ArrayList<OTF> save = new ArrayList<OTF>();
        for(OTF i : givenboard){
            save.add(i);
        }

        for(int i=0; i<nbrofOTF ; i++){
            Random rand = new Random();
            int nbr = rand.nextInt(save.size());
            this.board.add(save.get(nbr));
            save.remove(nbr);

            
        }

        Random rand = new Random();
        this.ITF = board.get(rand.nextInt(board.size()));
    }

    
    public String getTheme(){
        return theme;
    }

    public OTF getITF(){
        return ITF;
    }

    public int getNbrofOTF(){
        return nbrofOTF;
    }

    public List<Integer> getSize(){
        return size;
    }

    public ArrayList<OTF> getBoard(){
        return board;
    }



    public void printBoard(){
        System.out.println(board);
    
    }
}
