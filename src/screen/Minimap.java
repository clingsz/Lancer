package screen;

import element.World;

public class Minimap extends Vobject{
	public Minimap(int nx,int ny, int nw, int nh){
		super(nx,ny,nw,nh);
	}
	
	public void render(Screen screen, Worldviewer wv){
		screen.render(x,y,width,height,wv.getMinimap());
	}
}
