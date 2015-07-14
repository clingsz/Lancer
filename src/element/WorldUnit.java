package element;

import java.awt.Point;
import java.util.ArrayList;

import mainrun.InputHandler;
import mainrun.NumberFormatter;


import screen.ControlPanel;
import screen.Screen;


public abstract class WorldUnit {
	public int x,y;
	public Player player;
	public int energy = 10;
	public ArrayList<Squad> squads = new ArrayList<Squad>();
	public int food = 1000;
	public WorldUnit(Player p, Point np){
		x = np.x;
		y = np.y;
		player = p;
		energy = 10;
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
		cp.drawString(screen,0,0,player.name + " Family");
		
		cp.drawImage(screen,4,0,"food");
		cp.drawString(screen,5,0,NumberFormatter.format(food));
		
		cp.drawImage(screen,4,1,"soldier"+player.id);
		cp.drawString(screen,5,1,NumberFormatter.format(this.getSoldierNum()));
		
		cp.drawImage(screen,4,2,"energy");
		cp.drawString(screen,5,2,energy+"");
		
		
		int c = 0;
		int COL = 6;
		for (Squad s:squads){
			int x = c%COL;
			double y = (c/COL);
			c++;
			cp.drawImage(screen,10+x*1.2,y,s.getImageString());
			cp.drawString(screen,10.5+x*1.2,y+0.3,s.getSoldierNum()+"");
		}
	}
	
	public void renderSelected(Screen screen, World w){
		screen.render(w.getShowX(x), w.getShowY(y), w.size, "selected");
	}
}
