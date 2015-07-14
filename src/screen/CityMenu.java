package screen;
import mainrun.GameState;
import mainrun.InputHandler;
import mainrun.GameState.State;
import element.World;


public class CityMenu extends Menu {

	public CityMenu(){
		String[] str = {"Recruit","Build Legion","Info","Back"}; 
		super.init(str);
	}
	
	
	public void workOnChoice(GameState g){
		if (selection==3){
			workOnCancel(g);
		}
	}
	
	public void workOnCancel(GameState g){
		g.world.clearSelection();
		g.state = State.worldBrowsing;
	}
	
}
