package screen;
import mainrun.GameState;
import mainrun.InputHandler;
import mainrun.GameState.State;
import element.Legion;
import element.World;


public class LegionMenu extends Menu {
	private Legion legion;
	public LegionMenu(Legion legion){
		String[] str = {"Move","Stay","Info","Back"}; 
		super.setOptions(str);
		this.legion = legion;
	}

	public void doOption(){
		switch(selection){
		case 0: if (legion.canMove()) gameState.moveLegion(legion); break;
		case 1: break;
		case 3: doCancel(); break;
		}
	}
	
	public void doCancel(){
		gameState.clearMenu(); gameState.viewer.clearSelection();
	}
}