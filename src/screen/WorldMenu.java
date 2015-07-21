package screen;
import mainrun.GameState;
import mainrun.GameState.State;
import mainrun.InputHandler;
import element.World;


public class WorldMenu extends Menu {

	public WorldMenu(){
		String[] str = {"End Today","Back","Reset Game","Save Game","Load Game","About","Options","Exit"};
		super.setOptions(str);
		size = 20;
	}
			
	public void doOption(){
		switch(selection){
		case 0: gameState.world.nextDay(); gameState.clearMenu(); break;
		case 1: doCancel(); break;
		case 2: gameState.resetGame(); break;
		case 7: gameState.endGame(); break; 
		}
	}
	
	public void doCancel(){
		gameState.clearMenu();
	}
	
}
