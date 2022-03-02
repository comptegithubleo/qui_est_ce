package app;

public class Transfer {

    private String difficulty;
    private String theme;
    private String save;
    private boolean newGame;
    
    public Transfer(String difficulty){
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    
    public boolean getIsNewGame()
    {
        return this.newGame;
    }
    public void setIsNewGame(boolean newGame)
    {
        this.newGame = newGame;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }


}

