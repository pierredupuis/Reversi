package fr.isima.cours.jee.server.business;

import java.io.*;
import java.time.LocalDateTime;

public class Board{
	
	private class Directions {
		boolean UP			= false;
		boolean UP_RIGHT	= false;
		boolean RIGHT		= false;
		boolean DOWN_RIGHT	= false;
		boolean DOWN		= false;
		boolean DOWN_LEFT	= false;
		boolean LEFT		= false;
		boolean UP_LEFT		= false;
		
		public boolean hasAtLeastOne(){
			return UP || UP_RIGHT || RIGHT || DOWN_RIGHT || DOWN || DOWN_LEFT || LEFT || UP_LEFT;
		}
	}
	
	
    private State[][] grid;
	private State player;
	private int nb_turns = 0;
	private String filename = LocalDateTime.now().getNano() + "game.txt";

	public Board(){
		init();
	}
	public void init(){
        grid = new State[8][8];
		player = State.WHITE;

		for(int i = 0; i < 8; ++i){
            for(int j = 0; j < 8; ++j){
                grid[i][j] = State.EMPTY;
            }
        }

        grid[3][3] = State.WHITE;
        grid[4][4] = State.WHITE;
        grid[3][4] = State.BLACK;
        grid[4][3] = State.BLACK;

		nb_turns = 0;
	}
	
	// Get a square's state. If out of the board, returns State.OUT
	public State get(int x, int y){
		if(0 <= x && x < 8 && 0 <= y && y < 8)
			return grid[x][y];
		else
			return State.OUT;
	}
	
	// Set a square's state, returning the old value. Does nothing if out of the board and returns State.OUT 
	private State set(int x, int y, State st){
		State old = get(x, y);
		if(0 <= x && x < 8 && 0 <= y && y < 8 && st != State.OUT)
			grid[x][y] = st;
		return old;
	}
	
	// One method for each direction. Returns the state of the ((i,j) + nb steps in the given direction) square
	public State up(int i, int j, int nb){
		return get(i, j-nb);
	}
	public State up_right(int i, int j, int nb){
		return get(i+nb, j-nb);
	}
	public State right(int i, int j, int nb){
		return get(i+nb, j);
	}
	public State down_right(int i, int j, int nb){
		return get(i+nb, j+nb);
	}
	public State down(int i, int j, int nb){
		return get(i, j+nb);
	}
	public State down_left(int i, int j, int nb){
		return get(i-nb, j+nb);
	}
	public State left(int i, int j, int nb){
		return get(i-nb, j);
	}
	public State up_left(int i, int j, int nb){
		return get(i-nb, j-nb);
	}
	
	// One method for each direction. Sets the state of the ((i,j) + nb steps in the given direction) square
	public State up(int i, int j, int nb, State st){
		return set(i, j-nb, st);
	}
	public State up_right(int i, int j, int nb, State st){
		return set(i+nb, j-nb, st);
	}
	public State right(int i, int j, int nb, State st){
		return set(i+nb, j, st);
	}
	public State down_right(int i, int j, int nb, State st){
		return set(i+nb, j+nb, st);
	}
	public State down(int i, int j, int nb, State st){
		return set(i, j+nb, st);
	}
	public State down_left(int i, int j, int nb, State st){
		return set(i-nb, j+nb, st);
	}
	public State left(int i, int j, int nb, State st){
		return set(i-nb, j, st);
	}
	public State up_left(int i, int j, int nb, State st){
		return set(i-nb, j-nb, st);
	}
	
	public int count(State st){
		int nb = 0;
		for(int i = 0; i < 8; ++i){
            for(int j = 0; j < 8; ++j){
                if(grid[i][j] == st)
					nb++;
            }
        }
		return nb;
	}
	private void saveGame(String s){
		File file = new File(System.getProperty("user.dir") + "//"+filename);
		FileWriter fr = null;
		try {
			fr = new FileWriter(file);
			fr.write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			//close resources
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String print(State st){
		StringBuilder sb=new StringBuilder();
		sb.append("  | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |\n");
		for(int j = 0; j < 8; j++){
			sb.append("--+---+---+---+---+---+---+---+---+\n");
			sb.append(Integer.toString(j) + " |");
			for(int i = 0; i < 8; i++){
				if(grid[i][j] == State.WHITE)
					sb.append(" W |");
				else if (grid[i][j] == State.BLACK)
					sb.append(" B |");
				else
					sb.append("   |");
			}
			sb.append("\n");
		}
		sb.append("--+---+---+---+---+---+---+---+---+\n");
		return sb.toString();
}
	
	public boolean isPlayable(int x, int y, State st){
		if(get(x, y) == State.EMPTY){
			Directions dirs = computeChangingDirections(x, y, st);
			return dirs.hasAtLeastOne();
		}
		else {
			return false;
		}
	}
	public State getPlayer(){
		return player;
	}


	public String getWinner(){
		if(count(State.BLACK) > count(State.WHITE) )
			return State.BLACK.getName();
		else
			return State.WHITE.getName();
	}
	public String getScore(){
		return "White: " + count(State.WHITE) + " - Black: " + count(State.BLACK);
	}
	public boolean hasEnded(){
		return nb_turns >= 60;
	}
	public void skipTurn(){
		player = player.opposite();
	}
	public void reset(){
		init();
	}

	// Makes a move
	public boolean play(int x, int y){

		if(get(x, y) == State.EMPTY)
		{
			Directions dirs = computeChangingDirections(x, y, player);
			if(dirs.hasAtLeastOne())
			{
				State opp = player.opposite();
				int step;

				// place the pawn
				set(x,y,player);

				// Make changes to the board
				/*
				* if(direction has been marked as 'to modify'){
				*   change color of pawns that direction until it is not an enemy pawn
				* */

				if(dirs.UP)
					for(step = 1; opp == up(x, y, step, player); step++){}

				if(dirs.UP_RIGHT)
					for(step = 1; opp == up_right(x, y, step, player); step++){}

				if(dirs.RIGHT)
					for(step = 1; opp == right(x, y, step, player); step++){}
						
				if(dirs.DOWN_RIGHT)
					for(step = 1; opp == down_right(x, y, step, player); step++){}
						
				if(dirs.DOWN)
					for(step = 1; opp == down(x, y, step, player); step++){}
						
				if(dirs.DOWN_LEFT)
					for(step = 1; opp == down_left(x, y, step, player); step++){}
						
				if(dirs.LEFT)
					for(step = 1; opp == left(x, y, step, player); step++){}
						
				if(dirs.UP_LEFT)
					for(step = 1; opp == up_left(x, y, step, player); step++){}
				saveGame(print(player));
				player = player.opposite();
				nb_turns++;

				return true;
			}
			else{
				return false;
			}
		}
		else {
			return false;
		}
	}

	/* Given a move (coordinates and color), computes which directions will have to be changed once the move is done.
	 * Iterates in all directions. Mark a direction true if the given move enclose an opposing set of pawns.
	 *
	 * It is useful for two things :
	 *  - Checking that a move is valid, meaning that he produces a change in at least one direction
	 *  - Actually making the move and determining (without re-iterating) which directions are to be changed
	 */
	public Directions computeChangingDirections(int x, int y, State st){
		if(get(x, y) == State.EMPTY){
			State opp = st.opposite();
			int step;
			Directions dirs = new Directions();
			
			// UP			
			// While we find opposite color pawns
			for(step = 1; opp == up(x, y, step); step++){}
			
			// If we have our color at more than 1 square of distance, it means we can play here
			if(up(x, y, step) == st && step > 1)
				dirs.UP = true;
			
			// up_right
			for(step = 1; opp == up_right(x, y, step); step++){}
			
			if(up_right(x, y, step) == st && step > 1)
				dirs.UP_RIGHT = true;
			
			// right
			for(step = 1; opp == right(x, y, step); step++){}
				
			if(right(x, y, step) == st && step > 1)
				dirs.RIGHT = true;
			
			// down_right
			for(step = 1; opp == down_right(x, y, step); step++){}
			
			if(down_right(x, y, step) == st && step > 1)
				dirs.DOWN_RIGHT = true;
			
			// down
			for(step = 1; opp == down(x, y, step); step++){}
			
			if(down(x, y, step) == st && step > 1)
				dirs.DOWN = true;
			
			// down_left
			for(step = 1; opp == down_left(x, y, step); step++){}
			
			if(down_left(x, y, step) == st && step > 1)
				dirs.DOWN_LEFT = true;
			
			// left
			for(step = 1; opp == left(x, y, step); step++){}
			
			if(left(x, y, step) == st && step > 1)
				dirs.LEFT = true;
			
			// up_left
			for(step = 1; opp == up_left(x, y, step); step++){}
			
			if(up_left(x, y, step) == st && step > 1)
				dirs.UP_LEFT = true;
			
			return dirs;
		}
		else
		{
			return new Directions();
		}
	}
	
}