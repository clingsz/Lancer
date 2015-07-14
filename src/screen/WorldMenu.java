package screen;
import mainrun.GameState;
import mainrun.GameState.State;
import mainrun.InputHandler;
import element.World;


public class WorldMenu extends Menu {

	public WorldMenu(){
		String[] str = {"End Today","Back","Save Game","Load Game","About","Options","Exit"};
		super.init(str);
		size = 20;
	}
			
	public void workOnChoice(GameState g){
		if (selection==0){
			g.world.nextDay();	
			g.state = State.worldBrowsing;
		}
		else if (selection==1){
			g.state = State.worldBrowsing;
		}
	}
	
	public void workOnCancel(){
		
	}
	
}
