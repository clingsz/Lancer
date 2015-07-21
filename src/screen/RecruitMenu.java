package screen;
import mainrun.GameState;
import mainrun.InputHandler;
import mainrun.GameState.State;
import element.City;
import element.Squad;
import element.World;


public class RecruitMenu extends Menu {
	private City city;
	public RecruitMenu(City city){
		String[] str = Squad.types; 
		super.setOptions(str);
		this.city = city;
//		xoffset = 300;
	}

	public void doOption(){
		if (city.energy>0)
			city.recruit(selection);
	}
	
	public void doCancel(){
		gameState.clearMenu();
		gameState.viewer.clearSelection();
	}
}
