// package fr.isima.cours.jee.server.business;

// import org.springframework.stereotype.Component;

// @Component
public class Game {


    private Board board;
    private boolean player = true; // true = white, false = boolean

    public Game() {
        init();
    }
	public static void main(String[] args){
		Game g = new Game();
	}

    public void init(){
		board = new Board();
    }


    public String getTab(){
        StringBuilder sb = new StringBuilder();
        sb.append("<table>");
        for(int i = 0; i < 8; ++i){
            sb.append("<tr>");
            for(int j = 0; j < 8; ++j){
                sb.append("<td>").append(board.get(i,j).toString()).append("</td>");
            }
            sb.append("</tr>");
        }
        return sb.toString();
    }
}
