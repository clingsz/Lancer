
public class GameState {
	public static GameState worldbrowse = new GameState(1);
	public static GameState unitchoice = new GameState(2);
	public static GameState citychoice = new GameState(3);
	
	int id;
	Player currentPlayer;
	public GameState(int id){
		this.id = id;
	}
	
	public void setCurrentPlayer(Player nowPlayer){
		currentPlayer = nowPlayer;
	}
	
	public void setState(GameState g){
		this.id = g.id;
	}
}
