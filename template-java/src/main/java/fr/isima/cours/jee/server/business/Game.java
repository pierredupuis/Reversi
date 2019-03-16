package fr.isima.cours.jee.server.business;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Game {


    private Board board;
    private boolean player = true; // true = white, false = boolean

    public Game() {
        init();
    }
	public static void main(String[] args){
		Game g = new Game();
		g.console_play();
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
                sb.append("<td><a>").append(board.get(i,j).toString()).append("</a></td>");
            }
            sb.append("</tr>");
        }
        return sb.toString();
    }

    public void console_play(){

        Scanner sc = new Scanner(System.in);
        sc.useDelimiter(",|\\n");
        State player = State.BLACK;

        while(true){
            board.print(player);
            System.out.print("Enter coordinates for " + player.getName() + "s: ");
            if(board.play(sc.nextInt(), sc.nextInt()))
                player = player.opposite();
        }
    }
}
