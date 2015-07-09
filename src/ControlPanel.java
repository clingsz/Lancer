
public class ControlPanel {
	public int Width,Height;
	public int miniMapBorder,miniMapSize;
	public int size = 22;
	
	public int terrianPosX = 1;
	public int terrianPosY = 17;
	
	public int unitPosX = 0;
	public int unitPosY = 2;
	
	public int squadPosX = 1;
	public int squadPosY = 5;
	
	
	public void init(int w, int h){
		Width = w;
		Height = h;
		miniMapBorder = 30;
		miniMapSize = (w-miniMapBorder*2);
	}
	WorldUnit wu = null;
	public void render(Screen screen, World world){
		screen.render(0,0,Width,Height,"controlPanel");
		screen.render(miniMapBorder, Height - Width + miniMapBorder, miniMapSize, world.getMiniMap(screen,this));
		
		drawImage(screen,0,0,"calendar");
		drawString(screen,1,0,world.getDate());
		
		drawString(screen,3,terrianPosY, world.getFocusString());
		drawImage(screen,0,terrianPosY,world.getFocusTerrian().getImageString());
		drawString(screen,1,terrianPosY, world.getFocusTerrian().getTerrianName());
		
		wu = world.getUnitAtFocus(); 
		if (wu!=null){
			wu.renderInfo(screen,this);
		}
	}
	
	public void drawImage(Screen screen,int x,int y, String imgString){
		screen.render(x*size+size/2,y*size+size/2, size, size, imgString);
	}
	public void drawImage(Screen screen,double x,double y, String imgString){
		screen.render((int)(x*size+size/2),(int)(y*size+size/2), size, size, imgString);
	}
	public void drawString(Screen screen,double x,double y, String info){
		screen.render((int)((x+0.2)*size+size/2),(int)((y+0.6)*size+size/2),info);
	}
	public void drawString(Screen screen,double x,double y, String info,int Fontsize){
		screen.render((int)((x+0.2)*size+size/2),(int)((y+0.6)*size+size/2),info,Fontsize);
	}
	
}
