package element;

import java.util.ArrayList;

import mainrun.RandGen;

import screen.Screen;


public class Squad{
	public static String[] types = {"infantry","pikeman","cavalry"};
	public static int[] consume = {1,1,2};
	public int type;
	public ArrayList<Soldier> soldiers = new ArrayList<Soldier>();
	public static final int MAXNUM = 16;
	public Player player;
	public Squad(Player p){
		type = RandGen.getRandomNumber(0, 2);
		for (int i = 0; i<MAXNUM; i++){
			soldiers.add(new Soldier());
		}
		player = p;
	}
	
	public Squad(Player p, int type){
		this.type = type;
		for (int i = 0; i<MAXNUM; i++){
			soldiers.add(new Soldier());
		}
		player = p;
	}
	
	
	public String getImageString(){
		return(types[type] + player.id);
	}
	public void render(Screen screen, int xp, int yp, int size) {
		screen.render(xp, yp, size, getImageString());
	}
	public int getSoldierNum(){
		return soldiers.size();
	}
	public int getFoodConsumption(){
		return getSoldierNum()*consume[type];
	}
	
	public static int squadPrice(int type){
		return MAXNUM*consume[type]*2;
	}
}
