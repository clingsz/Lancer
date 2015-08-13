package screen;

import element.World;

public class Minimap extends Vobject{
	public Worldviewer worldviewer;
	public Minimap(int nx,int ny, int nw, int nh, Worldviewer wv){
		super(nx,ny,nw,nh);
		worldviewer = wv;
	}
	
	public void render(Screen screen){
		screen.render(x,y,width,height,worldviewer.getMinimap());
	}
	
	public void click(int mx,int my){
		int nx = (mx-x)*worldviewer.world.Width/width;
		int ny = (my-y)*worldviewer.world.Height/height;
		worldviewer.setFocus(nx, ny);
		System.out.println(nx+","+ny);
	}
}


