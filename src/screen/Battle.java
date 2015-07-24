package screen;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

import mainrun.GameState;
import mainrun.RandomNameGenerator;

import element.City;
import element.Legion;
import element.Player;
import element.Squad;
import element.WorldUnit;

public class Battle {
	boolean attackCity = false;
	GameState gameState;
	public boolean testPlay = false;
	
	WorldUnit A[] = new WorldUnit[2];
	
	public Battle(GameState g){
		Player p1 = new Player(1);
		Player p2 = new Player(2);
		A[0] = new Legion(p1,new Point(1,2));
		A[1] = new Legion(p2,new Point(1,2));
		gameState = g;
		init();
	}
	
	public Battle(GameState g,WorldUnit A, WorldUnit B){
		if (B instanceof City){
			attackCity = true;
		}
		this.A[0] = A;
		this.A[1] = B;
		gameState = g;
		init();
	}
	public boolean graph[][] = new boolean[20][20];
	
	public void init(){
		for(int i = 0; i<20;i++) for(int j=0;j<20;j++) graph[i][j] = false;
		for(int i = 1; i<=4;i++){
			graph[0][i] = true;
			graph[10][10+i] = true;
			
			graph[i][i+4] = true;
			graph[i+10][i+14] = true;
			
			graph[i][i+5] = true;
			graph[i+10][i+15] = true;
		}
		for(int i = 5;i<=9;i++){
			graph[i][19-i+5] = true;
		}
		
		// mirrow
		for(int i = 0; i<20;i++) for(int j=0;j<20;j++) if (graph[i][j]) graph[j][i] = true;
		
		updateGraph();
		updateTarget();
	}
	
	public void updateGraph(){
		for(int k = 0; k<20;k++) if(this.getSquadAtIndex(k)==null){
			for(int i=0;i<20;i++) for(int j=0;j<20;j++) if(i!=j && graph[i][k] && graph[k][j]){
				graph[i][j] = true;
			}
		}
	}
	
	
	
	public Squad getSquadAtIndex(int x){
		int i = 0;
		if (x>=10){
			i = 1;
			x-=10;
		}
		return A[i].getSquadAtOrder(x);
	}
	
	int w = 7;
	int h = 7;
	
	public static int sx[][] = {{3,1,2,4,5,1,2,3,4,5},{3,5,4,2,1,5,4,3,2,1}};
	public static int sy[][] = {{6,5,5,5,5,4,4,4,4,4},{0,1,1,1,1,2,2,2,2,2}};
	
	public void squadAttack(Squad s1, Squad s2){
		s1.fightSquad(s2);
	}
	
	// 0 1 2 
	public int selection = 0;
	public int selectSquad = 0;
	public boolean selectOrder = true;
		
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
			System.in.read();
			a.fightSquad(b);
			a.showInfo();
			b.showInfo();
			if (a.isDanger()){
				dd1++;
//				break;
//				a = new Squad(null,t1);
				a.requestBackUp();
			}
			if (b.isDanger()){
				dd2++;
//				System.out.println("B dead "+dd2);
//				break;
//				b = new Squad(null,t2);
				b.requestBackUp();
			}
			System.out.println();
		}
	}
	
	public String battleOptions[] = {"attack","defend","retreat","exit"}; 

	public void prevSelect() {
		if (selectOrder){
			selection--;
			if (selection<0) selection+= this.battleOptions.length;
		}
		else{
			prevSquad();
		}
	}

	public void nextSelect() {
		if (selectOrder){
			selection++;
			if (selection>=battleOptions.length) selection=0;
		}
		else{
			nextSquad();
		}
	}
	
	public void prevSquad(){
		selectSquad++;
		if (selectSquad>=20) selectSquad = 0;
		if (this.getSelectedSquad()==null) prevSquad();
	}
	
	public void nextSquad(){
		selectSquad--;
		if (selectSquad<0) selectSquad+=20;
		if (this.getSelectedSquad()==null) nextSquad();
	}

	public void changeToOrder() {
		selectOrder = true;
	}

	public void changeToLook() {
		selectOrder = false;
	}

	public void issueOrder() {
		if (selectOrder){
			switch(selection){
			case 0: makeAttack(0); break; //attack
			case 1: break; //defend
			case 2: gameState.setWorldBrowsing(); break; //retreat
			case 3: gameState.endGame(); break; //system
			}
		}
	}
	
	public int targets[] = new int[20];
	public void updateTarget(){
		for(int i = 0;i<20;i++){
			targets[i] = findTarget(i);
		}
	}
	

	public int getTarget(int i){
		if (targets[i]>=10) return targets[i]-10;
		else{
			return targets[i];
		}
	}
	
	public void makeAttack(int a){
		int target = 0;
		for(int i = 9;i>=0;i--){
			updateGraph();
			updateTarget();
			target = getTarget(i+a*10);
			if (A[a].getSquadAtOrder(i)!=null && target>=0){
				A[a].getSquadAtOrder(i).fightSquad(A[1-a].getSquadAtOrder(target));
				System.out.println(i+" fighting " + target);
				if (A[a].getSquadAtOrder(i).isDead()){
					A[a].clearSquad();
				}
				if (A[1-a].getSquadAtOrder(target).isDead()){
					A[1-a].clearSquad();
				}
				updateGraph();
				updateTarget();
			}
		}
	}
	
	
	public int findTarget(int x){
		if (x<10){
			for(int i = 10;i<20;i++){
				if (graph[x][i] && this.getSquadAtIndex(i)!=null){
					return (i);
				}
			}
		}
		else{
			for(int i = 0;i<10;i++){
				if (graph[x][i] && this.getSquadAtIndex(i)!=null){
					return (i);
				}
			}
		}
		return -1;
	}

	public int getSelectedSquadI() {
		if (this.selectSquad<10) return 0;
		return 1;
	}
	
	public int getSelectedSquadJ() {
		if (selectSquad>=10)
		return 19-selectSquad;
		else return selectSquad;
	}

	public void switchSelect() {
		if (selectOrder){
			selectOrder =false;
		}
		else{
			selectOrder = true;
		}
	}

	public Squad getSelectedSquad() {
		int i = getSelectedSquadI();
		int j = getSelectedSquadJ();
		return A[i].order[j];
	}

	public int getSelectSquadIndex(){
		int i = getSelectedSquadI();
		int j = getSelectedSquadJ();
		return i*10+j;
	}
	
	public int getSelectedSquadTargetI() {
		if (getSelectSquadIndex()>=0)
		{
			return 1-getSelectedSquadI();
		}
		else
			return -1;
	}
	
	public int getSelectedSquadTargetJ() {
		if (getSelectSquadIndex()>=0)
		{
			int temp =  targets[getSelectSquadIndex()];
			if (temp>=10) temp-=10;
			return temp;
		}
		else
			return -1;
	}

	
}
