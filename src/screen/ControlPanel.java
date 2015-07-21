package screen;
import element.City;
import element.Legion;
import element.Squad;
import element.World;
import element.WorldUnit;


public class ControlPanel {
	public int miniMapBorder,miniMapSize;
	public int size = 30;
	
	public int terrianPosX = 1;
	public int terrianPosY = 17;
	
	public int unitPosX = 0;
	public int unitPosY = 2;
	
	public int squadPosX = 1;
	public int squadPosY = 5;
		
	public int topborder,leftborder;
	public int xos,yos,Width,Height;
	public ControlPanel(int nx, int ny, int nw, int nh){
		xos = nx;
		yos = ny;
		Width = nw;
		Height = nh;
		
		topborder = 10;
		leftborder = 100;
		miniMapBorder = 16;
		miniMapSize = (Height-miniMapBorder*2);
	}
	
	public void render(Screen screen, World world, Viewer v){
		
		screen.render(xos,yos,Width,Height,"controlPanel");
		screen.render(5, yos+miniMapBorder, miniMapSize, v.getMinimap(world));
		
		screen.render(5,yos,15,"calendar");
		screen.render(21,yos+12,world.getDate());
		
		screen.render(21,yos+Height-3, v.getFocusString());
		screen.render(5,yos+Height-15,15,v.focusTerrian.getImageString());
		
	}
	
	public void renderUnitInfo(Screen screen,WorldUnit wu){
		drawString(screen,0,0,wu.player.name + " Family");
		drawImage(screen,4,0,"food");
		drawString(screen,5,0,wu.getFoodNumString());

		drawImage(screen,4,1,"soldier"+wu.player.id);
		drawString(screen,5,1,wu.getSoldierNumString());

		drawImage(screen,4,2,"energy");
		drawString(screen,5,2,wu.energy+"");
		
		
		int c = 0;
		int COL = 6;
		for (Squad s:wu.squads){
			int x = c%COL;
			double y = (c/COL);
			c++;
			drawImage(screen,10+x*1.2,y,s.getImageString());
			drawString(screen,10.5+x*1.2,y+0.3,s.getSoldierNum()+"");
		}
		
		if (wu instanceof Legion){
			drawImage(screen,1,1,wu.getImageString());
			Legion wl = (Legion)wu;
			drawString(screen,0,2,wl.general.lastName + " Legion");
		}
		else{
			City wc = (City)wu;
			drawImage(screen,1,1,wc.getImageString());
			drawString(screen,0,2,wc.cityName+" City");
	
			drawImage(screen,6,2,"population");
			drawString(screen,7,2,wc.getPopulationNumString());
		}
	}
	
	public void drawImage(Screen screen,int x,int y, String imgString){
		screen.render(x*size+leftborder,yos+y*size+topborder, size, size, imgString);
	}
	public void drawImage(Screen screen,double x,double y, String imgString){
		screen.render((int)(x*size)+leftborder,yos+(int)(y*size+topborder), size, size, imgString);
	}
	public void drawString(Screen screen,double x,double y, String info){
		screen.render((int)((x+0.2)*size)+leftborder,yos+(int)((y+0.6)*size)+topborder,info);
	}
	public void drawString(Screen screen,double x,double y, String info,int Fontsize){
		screen.render((int)((x+0.2)*size)+leftborder,yos+(int)((y+0.6)*size)+topborder,info,Fontsize);
	}
	
}
