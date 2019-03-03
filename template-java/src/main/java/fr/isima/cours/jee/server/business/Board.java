import java.util.Scanner;

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
		
		// public Directions()
		
		public boolean hasAtLeastOne(){
			return UP || UP_RIGHT || RIGHT || DOWN_RIGHT || DOWN || DOWN_LEFT || LEFT || UP_LEFT;
		}
	}
	
	
    private State[][] grid;
	
	public Board(){
        grid = new State[8][8];
		
		for(int i = 0; i < 8; ++i){
            for(int j = 0; j < 8; ++j){
                grid[i][j] = State.EMPTY;
            }
        }

        grid[3][3] = State.WHITE;
        grid[4][4] = State.WHITE;
        grid[3][4] = State.BLACK;
        grid[4][3] = State.BLACK;
		
		Scanner sc = new Scanner(System.in);
		State player = State.BLACK;
		while(true){
			print_possible(player);
			System.out.println("Enter coordinates:");
			if(play(sc.nextInt(), sc.nextInt(), player))
				player = player.opposite();
		}
		
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
	
	public void print_possible(State st){

		for(int j = 0; j < 8; j++){
			
			for(int i = 0; i < 8; i++){
				if(grid[i][j] == State.WHITE)
					System.out.print("W  ");
				else if (grid[i][j] == State.BLACK)
					System.out.print("B  ");
				else
					System.out.print(isPlayable(i,j,st) ? ".  " : "#  ");
			}
			System.out.println("");
		}
		System.out.println("--------------------------------");
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
	public boolean play(int x, int y, State st){
		if(get(x, y) == State.EMPTY)
		{
			Directions dirs = computeChangingDirections(x, y, st);
			if(dirs.hasAtLeastOne())
			{
				State opp = st.opposite();
				int step;
				
				set(x,y,st);
				
				if(dirs.UP)
					for(step = 1; opp == up(x, y, step, st); step++){}

				if(dirs.UP_RIGHT)
					for(step = 1; opp == up_right(x, y, step, st); step++){}
						
				if(dirs.RIGHT)
					for(step = 1; opp == right(x, y, step, st); step++){}
						
				if(dirs.DOWN_RIGHT)
					for(step = 1; opp == down_right(x, y, step, st); step++){}
						
				if(dirs.DOWN)
					for(step = 1; opp == down(x, y, step, st); step++){}
						
				if(dirs.DOWN_LEFT)
					for(step = 1; opp == down_left(x, y, step, st); step++){}
						
				if(dirs.LEFT)
					for(step = 1; opp == left(x, y, step, st); step++){}
						
				if(dirs.UP_LEFT)
					for(step = 1; opp == up_left(x, y, step, st); step++){}
				
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