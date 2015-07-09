import java.awt.Point;
import java.util.ArrayList;


public abstract class WorldUnit {
	public int x,y;
	public Player player;
	public int energy = 1;
	public ArrayList<Squad> squads = new ArrayList<Squad>();
	public int food = 1000;
	public WorldUnit(Player p, Point np){
		x = np.x;
		y = np.y;
		player = p;
		energy = 1;
	}
	public String getImageString(){
		return null;
	}
	public void nextDay(){
		food-=getSoldierNum();
		if (food<0) food=0;
		energy = 1;
	}
	
	public int getSoldierNum(){
			int temp = 0;
			for(Squad s : squads){
				temp+=s.getSoldierNum();
			}
			return temp;
	}
	
	public void tick(InputHandler input, World world){
		
	}
	
	public void renderInfo(Screen screen, ControlPanel cp){
		cp.drawString(screen,cp.unitPosX,cp.unitPosY-1,player.name + " Family");
		

		cp.drawImage(screen,cp.unitPosX,cp.unitPosY+1,"food");
		cp.drawString(screen,cp.unitPosX+0.8,cp.unitPosY+1,NumberFormatter.format(food),15);
		
		cp.drawImage(screen,cp.unitPosX+2,cp.unitPosY+1,"infantry"+player.id);
		cp.drawString(screen,cp.unitPosX+2.8,cp.unitPosY+1,NumberFormatter.format(this.getSoldierNum()),15);
		
		
		int c = 0;
		int COL = 6;
		for (Squad s:squads){
			int x = c%COL;
			double y = (c/COL)*1.2;
			c++;
			cp.drawImage(screen,cp.unitPosX+x,cp.unitPosY+5+y,s.getImageString());
			cp.drawString(screen,cp.unitPosX+x+0.3,cp.unitPosY+5+y+0.3,s.getSoldierNum()+"",12);
		}
	}
}
