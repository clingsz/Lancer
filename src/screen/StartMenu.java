package screen;

import java.util.ArrayList;

import mainrun.GameState;
import mainrun.InputHandler;


public class StartMenu extends Menu {

	ArrayList<Vbutton> btns = new ArrayList<Vbutton>();
	
	public StartMenu(Screen s, InputHandler input, GameState gameState){
		super(0,0,s.w,s.h);
		super.init(input, gameState);
		String[] str = {"NewGame","TestBattle","LoadGame","About","Options","Exit"};
		size = 50;
		for(int i = 0; i < str.length;i++){
			btns.add(new Vbutton(width/3,height/5 + (int)(size*(1.2*i)),size*4,size,str[i],30));
		}
	}
	
	public void render(Screen screen){
		screen.render(x,y, width, height, "message");
		for(Vbutton vb : btns){
			vb.render(screen);
		}
	}
	
	public void click(int mx, int my){
		for(Vbutton vb : btns){
			if (vb.isInRegion(mx,my)){
				doOption(btns.indexOf(vb));
			}
		}
	}
	
	public void doOption(int selection){
		switch(selection){
		case 0: gameState.resetGame(); break;
		case 1: gameState.testBattle(); break; 
		case 5: gameState.endGame(); break;
		}
	}
	
}
