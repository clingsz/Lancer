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
	public static final int MAXNUM = 10;
	public Squad[] order = new Squad[MAXNUM];
	public int food = 1000;
	public Soldier general;
	
	public WorldUnit(Player p, Point np){
		x = np.x;
		y = np.y;
		player = p;
		energy = 10;
	}
	

	public void setDefaultOrder(){
		for(int i = 0; i<squads.size(); i++){
			order[i] = squads.get(i);
		}
		general = order[0].getGeneral();
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
	
	public boolean isEnemy(WorldUnit w){
		return (player.isEnemy(w.player));
	}


	public Squad getSquadAtOrder(int j) {
		return order[j];
	}
	
	public void clearSquad(){
		for(int i = 0;i<MAXNUM;i++){
			if (order[i]!=null && order[i].isDead()){
				squads.remove(order[i]);
				order[i] = null;
			}
		}
	}
	
	public int getTotalSquad(){
		int cnt = 0;
		for(int i = 0;i<MAXNUM;i++){
			if (order[i]!=null){
				cnt ++;
			}
		}
		return cnt;
	}
	
}
