package screen;

import java.awt.Point;
import java.io.IOException;

import mainrun.RandomNameGenerator;

import element.City;
import element.Legion;
import element.Player;
import element.Squad;
import element.WorldUnit;

public class Battle {
	boolean attackCity = false;
	WorldUnit A[] = new WorldUnit[2];
	
	public Battle(){
		Player p1 = new Player(1);
		Player p2 = new Player(2);
		A[0] = new Legion(p1,new Point(1,2));
		A[1] = new Legion(p2,new Point(1,2));
	}
	
	public Battle(WorldUnit A, WorldUnit B){
		if (B instanceof City){
			attackCity = true;
		}
		this.A[0] = A;
		this.A[1] = B;
	}
	
	int w = 7;
	int h = 7;
	
	public static int sx[][] = {{3,1,2,4,5,1,2,3,4,5},{3,1,2,4,5,1,2,3,4,5}};
	public static int sy[][] = {{6,5,5,5,5,4,4,4,4,4},{0,1,1,1,1,2,2,2,2,2}};
	
	public void squadAttack(Squad s1, Squad s2){
		s1.fightSquad(s2);
	}
	
	public void render(Screen screen){
		
	}
	
	public static void main(String args[]) throws IOException{
		RandomNameGenerator.init();
		Squad.init();
		int t1 = 0;
		int t2 = 1;
		Squad a = new Squad(null,t1);
		Squad b = new Squad(null,t2);
		a.showInfo();
		b.showInfo();
		int dd1 = 0;
		int dd2 = 0;
		
		int t=0;
		while(t<1000){
			t++;
			System.out.println("Round "+t + " dead rate "+ dd1 +" : " + dd2);
//			System.in.read();
			a.fightSquad(b);
			a.showInfo();
			b.showInfo();
			if (a.isDead()){
				dd1++;
//				break;
				a = new Squad(null,t1);
				
			}
			if (b.isDead()){
				dd2++;
//				System.out.println("B dead "+dd2);
//				break;
				b = new Squad(null,t2);
			}
			System.out.println();
		}
	}
	
}
