package screen;

import java.io.IOException;

import mainrun.RandomNameGenerator;

import element.City;
import element.Squad;
import element.WorldUnit;

public class Battle {
	boolean attackCity = false;
	WorldUnit A,B;
	public Battle(WorldUnit A, WorldUnit B){
		if (B instanceof City){
			attackCity = true;
		}
		this.A = A;
		this.B = B;
	}
	
	
	public void squadAttack(Squad s1, Squad s2){
		s1.fightSquad(s2);
		
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
