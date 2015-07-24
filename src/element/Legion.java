package element;

import java.awt.Point;
import java.util.ArrayList;

import mainrun.InputHandler;

import screen.ControlPanel;
import screen.Screen;


public class Legion extends WorldUnit{

		
	public Legion(Player p, Point np){
		super(p, np);
		createStandardSquads();
		setDefaultOrder();
	}
	
	public void createRandomSquads(){
		for(int i = 0; i<MAXNUM; i++){
			squads.add(new Squad(this.player));
		}
	}
	
	public static int standard[] = {2,1,1,1,0,0,0,0,0,0};
	public void createStandardSquads(){
		for(int i = 0; i<MAXNUM; i++){
			squads.add(new Squad(this.player,standard[i]));
		}
		}
	
	public String getLegionName(){
		return general.lastName;
	}
	
	public void render(Screen screen, int xp, int yp, int size){
		screen.render(xp, yp, size, "legion" + player.id);
	}
	public String getImageString(){
		return ("legion" + player.id);
	}

	public boolean canMove(){
		return (energy>0);
	}
	
	public void moveTo(int nx,int ny){
		x=nx;
		y=ny;
		player.updateHash();
		energy = 0;
	}
	
	public void moveLegion(int mx,int my, World world){
		if (world.isValidToMove(mx+x, my+y,this) && energy>0){
			x+=mx;
			y+=my;
			player.updateHash();
//			System.out.println(energy);
			energy--;
		}
	}
	
	public boolean canAttack(World world){
		
		return false;
	}
	
	public void nextDay(){
		energy = 10;
	}
	
	public void stay(){
		
	}
	
		
}
