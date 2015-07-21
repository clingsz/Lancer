package screen;
import mainrun.GameState;
import mainrun.InputHandler;
import element.World;


public class Menu {
	
	int selection = 0;
	String choices[];
	int size = 20;
	int h = 0;
	int w = 8*size;
	int xoffset = 100;
	int yoffset = 100;
	
	InputHandler input;
	GameState gameState;
	
	public Menu(){
		
	}
	
	public void init(InputHandler input, GameState gameState){
		this.input = input;
		this.gameState = gameState;
	}
	
	public void setOptions(String newchoice[]){
		choices = newchoice;
		h = (choices.length+3)*size;
	}
	
	public int getSelection(){
		return selection;
	}
	
	public void nextSelection(){
		selection++;
		if (selection>=choices.length) selection -= choices.length;
	}
	
	public void previousSelection(){
		selection--;
		if (selection<0) selection += choices.length;
	}
	
	public void render(Screen screen){
		screen.render(xoffset,yoffset, w, h, "message");
		String showstr = "";
		
		for (int i = 0; i<choices.length; i++){
			showstr = choices[i];
			if (i==selection) showstr = ">" + showstr + "<";
			else showstr = "  " + showstr + "   ";
			screen.render(xoffset+size, yoffset+(i+2)*size, showstr);
		}
//		screen.render(xoffset+size, yoffset+(choices.length+2)*size, selection+"");
	}
	
	
	public void tick(){
		if (input.up.clicked) this.previousSelection();
		if (input.down.clicked) this.nextSelection();
		if (input.enter.clicked || input.select.clicked) doOption();
		if (input.cancel.clicked) doCancel();
	}
	
	public void doOption(){
		
	}
	public void doCancel(){
		
	}
	
}
