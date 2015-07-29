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
		
	public boolean isInRegion(int tx,int ty){
		if (tx>x && tx<x+width && ty>y && ty<y+height) return true;
		return false;
	}

	public void click(){
		
	}
	
	public void render(Screen screen){
		
	}
}
