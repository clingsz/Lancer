package screen;
import mainrun.GameState;
import mainrun.InputHandler;
import mainrun.GameState.State;
import element.City;
import element.World;


public class CityMenu extends Menu {
	private City city;
	public CityMenu(City city){
		String[] str = {"Recruit","Build Legion","Info","Back"}; 
		super.setOptions(str);
		this.city = city;
	}

	public void doOption(){
		switch(selection){
		case 0: if (city.energy>0) gameState.setMenu(new RecruitMenu(city)); break;
		case 3: doCancel(); break;
		}
	}
	
	public void doCancel(){
		gameState.clearMenu(); gameState.viewer.clearSelection();
	}
}
