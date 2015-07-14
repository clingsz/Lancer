package screen;
import mainrun.GameState;
import mainrun.GameState.State;
import mainrun.InputHandler;
import element.World;


public class StartMenu extends Menu {

	public StartMenu(){
		String[] str = {"New Game","Load Game","About","Options","Exit"};
		super.init(str);
		size = 50;
	}
	
	public void render(Screen screen){
		screen.render(0,0, screen.w, screen.h, "message");
		String showstr = "";
		
		for (int i = 0; i<choices.length; i++){
			showstr = choices[i];
			if (i==selection) showstr = ">" + showstr + "<";
			else showstr = "  " + showstr + "   ";
			screen.render(screen.w/3,screen.h/3 + (int)(size*(1.2*i)), showstr,30);
		}
	}
		
	public void workOnChoice(GameState g){
		if (selection==0){
			g.resetGame();
			g.state = State.worldBrowsing;
		}
	}
	
	public void workOnCancel(){
		
	}
	
}
