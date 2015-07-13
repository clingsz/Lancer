import java.util.ArrayList;


public class GameState {
	public static GameState worldbrowse = new GameState(1);
	public static GameState unitchoice = new GameState(2);
	public static GameState citychoice = new GameState(3);
	
	public class State{
		boolean in;
		public void add(){
			states.add(this);
		}
		public void set(){
			for(State s : states) s.clear();
			in = true;
		}
		public void clear(){
			in = false;
		}
	}
	
	public ArrayList<State> states = new ArrayList<State>();
	int id;
	
	public boolean paintWorld = true;
	public boolean paintMenu = true;
	public boolean showSelectedUnit = true;
	
	Player currentPlayer;
	public GameState(int id){
		this.id = id;
	}
	
	public State startMenu = new State();
	public State worldBrowsing = new State();
	public State legionMenu = new State();
	public State legionMove = new State();
	public State cityMenu = new State();
	public State cityRecruit = new State();
	public State battleBrowsing = new State();
	
	public void init(){
		startMenu.set(); 
	}
	
	public void tick(){
		if (startMenu.in){
			
		}
		else if()
		
		
	}
	
	public void render(Screen s){
		
	}
	
	public void setCurrentPlayer(Player nowPlayer){
		currentPlayer = nowPlayer;
	}
	
	
	
	
//	enum State{
//		worldBrowsing,
//		legionMenu,
//		legionMove,
//		legionInfo,
//		cityMenu,
//		cityMove,
//		cityRecruit,
//		cityInfo,
//		battleMenu,
//		gameMenu
//	};
	
}
