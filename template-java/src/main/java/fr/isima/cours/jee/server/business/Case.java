

public class Case{
	// -1 = out, 0 = empty, 1 = white, 2 = Black
	private int state;
	private int x;
	private int y;
	
	public Case(int x, int y){
		this.state = 0;
		this.x = x;
		this.y = y;
	}
	
	public boolean isEmpty(){
		return state == 0;
	}
	
	public boolean isWhite(){
		return state == 1;
	}
	
	public boolean isBlack(){
		return state == 2;
	}
	
}