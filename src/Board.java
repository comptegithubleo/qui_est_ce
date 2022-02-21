import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Board {

    private ArrayList<OTF> board = new ArrayList<OTF>();
    private OTF ITF;
    private int size;
    
    public Board(List<OTF> givenboard, int size){

        this.size = size;   
        
        ArrayList<OTF> save = new ArrayList<OTF>();
        for(OTF i : givenboard){
            save.add(i);
        }

        for(int i=0; i<size ; i++){
            Random rand = new Random();
            int nbr = rand.nextInt(save.size());
            this.board.add(save.get(nbr));
            save.remove(nbr);

            
        }

        Random rand = new Random();
        this.ITF = board.get(rand.nextInt(board.size()));
    }

    

    public void printBoard(){
        System.out.println(board);
    
    }
}
