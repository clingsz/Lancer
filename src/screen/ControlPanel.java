package screen;
import element.World;
import element.WorldUnit;


public class ControlPanel {
//	public int Width,Height;
	public int miniMapBorder,miniMapSize;
	public int size = 30;
	
	public int terrianPosX = 1;
	public int terrianPosY = 17;
	
	public int unitPosX = 0;
	public int unitPosY = 2;
	
	public int squadPosX = 1;
	public int squadPosY = 5;
		
	public int yoffset,topborder,leftborder;
	
	public void init(Screen screen){
		int h = screen.h-screen.WORLDHEIGHT;
		topborder = 10;
		leftborder = 100;
		miniMapBorder = 16;
		miniMapSize = (h-miniMapBorder*2);
		yoffset = screen.WORLDHEIGHT;
	}
	WorldUnit wu = null;
	public void render(Screen screen, World world){
		int Height = screen.h-screen.WORLDHEIGHT;
		
		screen.render(0,yoffset,screen.w,screen.h-screen.WORLDHEIGHT,"controlPanel");
		screen.render(5, yoffset+miniMapBorder, miniMapSize, world.getMiniMap(screen,this));
		
		screen.render(5,yoffset,15,"calendar");
		screen.render(21,yoffset+12,world.getDate());
		
		screen.render(21,yoffset+Height-3, world.getFocusString());
		screen.render(5,yoffset+Height-15,15,world.getFocusTerrian().getImageString());
		
		wu = world.getUnitAtFocus(); 
		if (wu!=null){
			wu.renderInfo(screen,this);
		}
	}
	
	public void drawImage(Screen screen,int x,int y, String imgString){
		screen.render(x*size+leftborder,yoffset+y*size+topborder, size, size, imgString);
	}
	public void drawImage(Screen screen,double x,double y, String imgString){
		screen.render((int)(x*size)+leftborder,yoffset+(int)(y*size+topborder), size, size, imgString);
	}
	public void drawString(Screen screen,double x,double y, String info){
		screen.render((int)((x+0.2)*size)+leftborder,yoffset+(int)((y+0.6)*size)+topborder,info);
	}
	public void drawString(Screen screen,double x,double y, String info,int Fontsize){
		screen.render((int)((x+0.2)*size)+leftborder,yoffset+(int)((y+0.6)*size)+topborder,info,Fontsize);
	}
	
}
