package screen;
import mainrun.GameState;
import mainrun.InputHandler;
import mainrun.GameState.State;
import element.Legion;
import element.World;


public class LegionMenu extends Menu {

	public LegionMenu(){
		String[] str = {"Move","Stay","Info","Back"}; 
		super.init(str);
	}
	
	
	public void workOnChoice(GameState g){
		Legion l = (Legion) g.world.selectedUnit;
		if (selection==0){
			
		}
		else if (selection==1){
			if (g.checkOK()){
				l.stay();
			}
			else{
				workOnCancel(g);
			}
		}
		else if (selection==2){
			
		}
		if (selection==3){
			workOnCancel(g);
		}
	}
	
	public void workOnCancel(GameState g){
		g.world.clearSelection();
		g.state = State.worldBrowsing;
	}
	
}
