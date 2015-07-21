package element;

import java.awt.Point;
import java.util.ArrayList;


import mainrun.InputHandler;
import mainrun.NumberFormatter;


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
		food-=getFoodConsumption();
//		if (food<0) food=0;
		energy = 1;
	}
	
	public int getSoldierNum(){
			int temp = 0;
			for(Squad s : squads){
				temp+=s.getSoldierNum();
			}
			return temp;
	}
	
	public String getFoodNumString(){
		return NumberFormatter.format(food);
	}
	public String getSoldierNumString(){
		return NumberFormatter.format(this.getSoldierNum());
	}
	
	public void tick(InputHandler input, World world){
		
	}
	
	public int getFoodConsumption(){
		int temp = 0;
		for (Squad s: squads){
			temp+= s.getFoodConsumption();
		}
		return temp;
	}
}
