
public class Menu {
	
	int selection = 0;
	String choices[];
	int size = 20;
	int h = 0;
	int w = 5*size;
	int xoffset = 100;
	int yoffset = 100;
	public Menu(){
		
	}
	
	public void init(String newchoice[]){
		choices = newchoice;
		h = (choices.length+2)*size;
	}
	public void tick(InputHandler input, World world){
		if (input.up.clicked) selection--;
		if (input.down.clicked) selection++;
		if (selection<0) selection += choices.length;
		if (selection>=choices.length) selection -= choices.length;
		if (input.select.clicked) workOnChoice(world);
		if (input.cancel.clicked) workOnCancel(world); 
	}
	
	public void render(Screen screen){
		screen.render(xoffset,yoffset, w, h, "message");
		String showstr = "";
		
		for (int i = 0; i<choices.length; i++){
			showstr = choices[i];
			if (i==selection) showstr = ">" + showstr + "<";
			else showstr = "  " + showstr + "   ";
			screen.render(xoffset+size, yoffset+(i+1)*size, showstr);
		}
//		screen.render(xoffset+size, yoffset+(choices.length+2)*size, selection+"");
		
	}
	
	public void workOnChoice(World world){
		
	}
	
	public void workOnCancel(World world){
		
	}
	
}
