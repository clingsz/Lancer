package screen;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import element.City;
import element.Legion;
import element.Player;
import element.Terrian;
import element.World;
import element.WorldUnit;

public class Worldviewer extends Vobject{
	int focusX =-1,focusY =-1;
	int selectX =-1,selectY =-1;
	public int targetX =-1,targetY=-1;
	
	int screenX, screenY;
	int size = 40;
	int showHeight,showWidth;
	public boolean renderTarget = false;
	
	
	public boolean renderEnemyTarget = false;
	public ArrayList<WorldUnit> wus;

	
	public WorldUnit focusUnit = null;
	
	public ArrayList<Point> possibleMove;
	public boolean renderPossibleMove = false;
	

	public WorldUnit selectedUnit = null;
	public Terrian focusTerrian;
	
	public Screen screen;
	
	World world;
	
	public Worldviewer(int nx, int ny, int nw, int nh,Screen ns) {
		super(nx, ny, nw, nh);
		showHeight = (int)Math.ceil(nh/size);
		showWidth = (int)Math.ceil(nw/size);
		screen = ns;
	}
	
	public void init(World w){
		world = w;
		City c = w.players.get(1).cities.get(0);
		setFocus(c.x,c.y);
	}
		
	public void render(){
		if (world!=null){
			renderWorld();
		}
	}
	
	public void renderWorld(){
		// render world
		renderTerrian();
		renderUnit();
		if (renderPossibleMove){
			renderFog();
		}
		// render controlInfo
		screen.render(getShowX(focusX), getShowY(focusY), size,"choice");
		
		if (selectedUnit!=null && shouldDisplay(selectedUnit)){
			screen.render(getShowX(selectedUnit.x), getShowY(selectedUnit.y), size,"selected");
		}
		
		if (renderTarget){
			screen.render(getShowX(targetX), getShowY(targetY), size,"target");
		}
		
		if (renderEnemyTarget){
			for (WorldUnit w : wus){
				if (shouldDisplay(w)){
					renderWorldUnit(w,"target");
				}
			}
		}
		
	}
	
	public void showLegionPossibleMove(Legion legion){
		possibleMove = world.getPossibleMove(legion);
		renderPossibleMove = true;
	}
		
	public void clearSelection(){
		this.selectedUnit = null;
		this.renderPossibleMove = false;
		this.renderTarget = false;
	}
			
	public Image getMinimap(){
		Image temp = world.getMiniMap();
		temp.getGraphics().drawRect(screenX, screenY, showWidth, showHeight);
		return temp;
	}
		

	public boolean selectTarget(){
		if (this.possibleMove.contains(new Point(focusX,focusY))){
			targetX = focusX;
			targetY = focusY;
			renderTarget = true;
			return true;
		}
		else{
			return false;
		}
	}
	
	

	public void renderTerrian(){
		for (int x = 0; x<=width/size; x++)
			for (int y = 0; y<=height/size; y++){
				int tx = screenX+x;
				int ty = screenY+y;
				if (world.isInWorld(tx,ty)){
					screen.render(x*size,y*size,size,world.terrian[tx][ty].getImageString());
				}
			}
	}
	
	public void renderFog(){
		for (int x = 0; x<=width/size; x++)
			for (int y = 0; y<=height/size; y++){
				int tx = screenX+x;
				int ty = screenY+y;
				Point tp = new Point(tx,ty);
				if (!this.possibleMove.contains(tp)){
					screen.render(x*size,y*size,size,"fog");
				}
			}
	}
	
	public void renderUnit(){
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
		if (x<screenX || x>screenX+width/size || y<screenY || y>screenY+height/size) return false;
		return true;
	}

	

	public void setFocus(int x, int y){
		if (world.isValid(x,y)) {
			focusX = x; focusY = y;
		}
		resetScreenPosition();
	}
	
	public void moveFocus(int dx, int dy){
		if (world.isValid(dx+focusX,focusY)) focusX+=dx;
		if (world.isValid(focusX,focusY+dy)) focusY+=dy;
		resetScreenPosition();
	}
	
	public void resetScreenPosition(){
		screenX = Math.min(world.Width-showWidth,Math.max(0,focusX - showWidth/2));
		screenY = Math.min(world.Height-showHeight,Math.max(0,focusY - showHeight/2));
		focusTerrian = world.getTerrianAt(focusX, focusY);	
		focusUnit = world.getUnitAt(focusX,focusY);
	}
	
	public String getFocusString(){
		return ("("+focusX+","+focusY+") " + focusTerrian.getTerrianName());
	}
	
	public boolean findAttackTarget(Legion legion) {
		wus = world.findAttackTarget(legion);
		if (wus.size()>0){
			renderEnemyTarget = true;
			return true;
		}
		return false;
	}

	public boolean selectAttackTarget(Legion legion) {
		return (wus.contains(world.getUnitAt(focusX, focusY)));
	}

	public WorldUnit getAttackTarget() {
		return world.getUnitAt(focusX, focusY);
	}
	
	public void selectUnit(){
		selectedUnit = focusUnit;
	}
	
	public String getDate(){
		return world.getDate();
	}

	
}
