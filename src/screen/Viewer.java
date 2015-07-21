package screen;

import java.awt.Image;
import java.awt.image.BufferedImage;

import element.City;
import element.Legion;
import element.Player;
import element.Terrian;
import element.World;
import element.WorldUnit;

public class Viewer {
	int focusX,focusY;
	int selectX,selectY;
	int screenX, screenY;
	Screen screen;
	ControlPanel controlPanel;
	
//	public Menu legionMenu = new LegionMenu();
//	public Menu cityMenu = new CityMenu();
//	public Menu startMenu = new StartMenu();
//	public Menu worldMenu = new WorldMenu();
	
	int size = 30;
	
	int WORLDHEIGHT;
	int WORLDWIDTH;
	
	int showWidth,showHeight;
	
	public WorldUnit selectedUnit = null;
	public Terrian focusTerrian;
	
	public Viewer(int w, int h){
		screen = new Screen(w,h);
		WORLDHEIGHT = h*3/4;
		WORLDWIDTH = w;
		showWidth = (WORLDWIDTH)/size;
		showHeight = (WORLDHEIGHT)/size;
		controlPanel = new ControlPanel(0,WORLDHEIGHT,w,h/4);
	}
	
	public void init(World w){
		City c = w.players.get(1).cities.get(0);
		setFocus(w,c.x,c.y);
	}
	
	public void renderMenu(Menu menu){
		menu.render(screen);
	}
	
	public void render(World world){
			// render world
			renderTerrian(world);
			renderUnit(world);
			// render controlBoard
			renderControlBoard(world);
			// render controlInfo
			screen.render(getShowX(focusX), getShowY(focusY), size,"choice");
			if (selectedUnit!=null && shouldDisplay(selectedUnit)){
				screen.render(getShowX(selectedUnit.x), getShowY(selectedUnit.y), size,"selected");
			}
	}
	
	public void clearSelection(){
		this.selectedUnit = null;
	}
	
	public Image getMinimap(World w){
		Image temp = w.getMiniMap();
		temp.getGraphics().drawRect(screenX, screenY, showWidth, showHeight);
		return temp;
	}
	
	
	public WorldUnit focusUnit = null;
	
	
	public void renderControlBoard(World world){
		this.controlPanel.render(screen, world, this);
		if (focusUnit!=null){
			controlPanel.renderUnitInfo(screen,focusUnit);
		}
		
	}
	
	
	public void renderTerrian(World world){
		for (int x = 0; x<=WORLDWIDTH/size; x++)
			for (int y = 0; y<=WORLDHEIGHT/size; y++){
				int tx = screenX+x;
				int ty = screenY+y;
				if (world.isInWorld(tx,ty)){
					screen.render(x*size,y*size,size,world.terrian[tx][ty].getImageString());
				}
			}
	}
	
	public void renderUnit(World world){
		for (Player p : world.players){
			for(City c : p.cities){
				if (shouldDisplay(c)){
					renderWorldUnit(c,"city"+p.id);
				}
			}
			
			for(Legion l : p.legions){
				if (shouldDisplay(l)){
					renderWorldUnit(l,"legion"+p.id);
				}
			}
		}
	}
	
	public boolean canSelectFocus(int id){
		if(this.focusUnit!=null && focusUnit.player.id==id) return true;
		return false;
	}
	
	public void renderWorldUnit(WorldUnit c,String imageStr){
		screen.render(getShowX(c.x), getShowY(c.y), size, imageStr);
	}
	
	public int getShowX(int x){
		return (x-screenX)*size;
	}
	
	public int getShowY(int y){
		return (y-screenY)*size;
	}
	
	public boolean shouldDisplay(WorldUnit u){
		return shouldDisplay(u.x,u.y);
	}
	
	public boolean shouldDisplay(int x, int y){
		if (x<screenX || x>screenX+WORLDWIDTH/size || y<screenY || y>screenY+WORLDHEIGHT/size) return false;
		return true;
	}
	
	public BufferedImage getImage(){
		return screen.image;
	}
	
	public void selectUnit(){
		selectedUnit = focusUnit;
	}
	
	public void setFocus(World world,int x, int y){
		if (world.isValid(x,y)) {
			focusX = x; focusY = y;
		}
		resetScreenPosition(world);
	}
	
	public void moveFocus(World world,int dx, int dy){
		if (world.isValid(dx+focusX,focusY)) focusX+=dx;
		if (world.isValid(focusX,focusY+dy)) focusY+=dy;
		resetScreenPosition(world);
	}
	
	public void resetScreenPosition(World world){
		screenX = Math.min(world.Width-showWidth,Math.max(0,focusX - showWidth/2));
		screenY = Math.min(world.Height-showHeight,Math.max(0,focusY - showHeight/2));
		focusTerrian = world.getTerrianAt(focusX, focusY);	
		focusUnit = world.getUnitAt(focusX,focusY);
	}
	
	public String getFocusString(){
		return ("("+focusX+","+focusY+") " + focusTerrian.getTerrianName());
	}
	
	

}
