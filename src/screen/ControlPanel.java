package screen;
import element.City;
import element.Legion;
import element.Squad;
import element.World;
import element.WorldUnit;


public class ControlPanel extends Vobject{
	public int miniMapBorder,miniMapSize;
	public int size = 30;
	
	public int terrianPosX = 1;
	public int terrianPosY = 17;
	
	public int unitPosX = 0;
	public int unitPosY = 2;
	
	public int squadPosX = 1;
	public int squadPosY = 5;
		
	public int topborder,leftborder;

	Minimap minimap;
	
	public ControlPanel(int nx, int ny, int nw, int nh){
		super(nx,ny,nw,nh);
		topborder = 10;
		leftborder = 100;
		miniMapBorder = 16;
		miniMapSize = (height-miniMapBorder*2);
		minimap = new Minimap(x+miniMapBorder,y+miniMapBorder,miniMapSize,miniMapSize);
	}
	
	public void render(Screen screen, Worldviewer wv){
		screen.render(x,y,width,height,"controlPanel");
		minimap.render(screen, wv);
				
		screen.render(5,y,15,"calendar");
		screen.render(21,y+12,wv.getDate());
		
		screen.render(21,y+height-3, wv.getFocusString());
		screen.render(5,y+height-15,15,wv.focusTerrian.getImageString());
	}
		
	public void renderUnitInfo(Screen screen,WorldUnit wu){
		drawString(screen,0,0,wu.player.name + " Family");
		drawImage(screen,4,0,"food");
		drawString(screen,5,0,wu.getFoodNumString());

		drawImage(screen,4,1,"soldier"+wu.player.id);
		drawString(screen,5,1,wu.getSoldierNumString());

		drawImage(screen,4,2,"consumption");
		drawString(screen,5,2,wu.getFoodConsumption()+"");
		
		
		drawImage(screen,1,1,"energy");
		drawString(screen,2,1,wu.energy+"");
		
		
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
			drawImage(screen,0,1,wu.getImageString());
			Legion wl = (Legion)wu;
			drawString(screen,0,2,wl.general.lastName + " Legion");
		}
		else{
			City wc = (City)wu;
			drawImage(screen,0,1,wc.getImageString());
			drawString(screen,0,2,wc.cityName+" City");
	
			drawImage(screen,6,2,"population");
			drawString(screen,7,2,wc.getPopulationNumString());
		}
	}
	
	public void drawImage(Screen screen,double px,double py, String imgString){
		screen.render((int)(px*size)+leftborder,y+(int)(py*size+topborder), size, size, imgString);
	}
	public void drawString(Screen screen,double px,double py, String info){
		screen.render((int)((px+0.2)*size)+leftborder,y+(int)((py+0.6)*size)+topborder,info);
	}
	public void drawString(Screen screen,double px,double py, String info,int Fontsize){
		screen.render((int)((px+0.2)*size)+leftborder,y+(int)((py+0.6)*size)+topborder,info,Fontsize);
	}
	
}
