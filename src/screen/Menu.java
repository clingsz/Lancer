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
	public Menu(){
		
	}
	
	public void init(String newchoice[]){
		choices = newchoice;
		h = (choices.length+3)*size;
	}
	public void tick(GameState g,InputHandler input){
		if (input.up.clicked) selection--;
		if (input.down.clicked) selection++;
		if (selection<0) selection += choices.length;
		if (selection>=choices.length) selection -= choices.length;
		if (input.select.clicked) workOnChoice(g);
		if (input.cancel.clicked) workOnCancel(g); 
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
	
	public void workOnChoice(GameState g){
		
	}
	
	public void workOnCancel(GameState g){
		
	}
	
}
