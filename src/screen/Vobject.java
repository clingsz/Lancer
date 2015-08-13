package screen;

public class Vobject {
	public int x,y,width,height;
	public boolean visible = false;
	
	public Vobject(int nx,int ny, int nw, int nh){
		x = nx;
		y = ny;
		width = nw;
		height = nh;
	}
		
	public boolean isInRegion(int mx,int my){
		if (mx>x && mx<x+width && my>y && my<y+height) return true;
		return false;
	}

	public void click(int mx, int my){
		
	}
	
	
}
