package screen;


public class StartMenu extends Menu {

	public StartMenu(){
		String[] str = {"New Game","Load Game","About","Options","Exit"};
		super.setOptions(str);
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
	
	public void doOption(){
		switch(selection){
		case 0: gameState.resetGame(); break;
		case 4: gameState.endGame(); break;
		}
	}
	
}
