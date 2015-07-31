package screen;

import mainrun.InputHandler;

public class Vbutton extends Vobject{

	public String caption;
	public String imageString;
	public int fontSize;
	public Vbutton(int nx, int ny, int nw, int nh, String cap, int f) {
		super(nx, ny, nw, nh);
		caption = cap;
		fontSize = f;
	}
	
	public boolean onFocus = false;
	public void render(Screen screen){
		screen.render(x+(width-caption.length()*fontSize/2)/2,y+fontSize,caption,fontSize);
//		if (onFocus){
			screen.g.drawRect(x, y, width, height);
//		}
	}
	
	public void tick(InputHandler input){
		if (input.mouse.clicked && this.isInRegion(input.clickX,input.clickY)){
			
		}
	}
}
