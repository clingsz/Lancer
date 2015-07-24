package element;

import java.util.ArrayList;

import mainrun.RandGen;

import screen.Screen;


public class Squad{
	public static String[] types = {"infantry","pikeman","cavalry"};
	public static int[] tAT = {200,180,240};
	public static int[] tDF = {140,180,150};
	
	public static int[][] nAT = new int[3][3];
	public static int[][] nDF = new int[3][3];
	public static int TotalType;
	
	public static int[] consume = {1,1,2};
	public int type;
	public ArrayList<Soldier> soldiers = new ArrayList<Soldier>();
	public static final int MAXNUM = 10;
	public Player player;
	
	public static void init(){
		TotalType = types.length;
		for(int i = 0 ; i < TotalType; i++) for(int j = 0 ; j< TotalType; j++){
			nAT[i][j] = 0;
			nDF[i][j] = 0;
		}
		// inf vs pike
		nAT[0][1] = 40;
		nDF[0][1] = 10;
		
		nAT[1][2] = 40;
		nDF[1][2] = 40;
		
		nAT[2][0] = 30;
		nDF[2][0] = 40;
	}
	
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
	
	public void fightSquad(Squad q){
		int l1 = soldiers.size();
		int l2 = q.soldiers.size();
		
		int at1 = tAT[type] + nAT[type][q.type];
		int at2 = tAT[q.type] + nAT[q.type][type];
		
		int df1 = tDF[type] + nDF[type][q.type];
		int df2 = tDF[q.type] + nDF[q.type][type];
		
		at1 = at1/2;
		at2 = at2/2;
		df1 = df1/2;
		df2 = df2/2;
		
		int t2 = 0;
		int d1[] = new int[l1];
		int m1[] = new int[l1];
		int d2[] = new int[l2];
		int m2[] = new int[l2];
		
		int tat = 0;
		int tdf = 0;
		int d = 0;
		for (int i=0;i<l1;i++){
			t2 = RandGen.getRandomNumber(0, l2-1);
			tat = (int) Math.ceil((at1+soldiers.get(i).getAT())*(0.5+0.5*soldiers.get(i).HP/100));
			tdf = (int) Math.ceil((df2 + q.soldiers.get(t2).DF)*(0.5+0.5*q.soldiers.get(t2).HP/100));
			d = Math.max(tat-tdf,1);
			d2[t2] += d;
			m1[i] = d;
		}
		
		int t1 = 0;
		for (int i=0;i<l2;i++){
			t1 = RandGen.getRandomNumber(0, l1-1);
			tat = (int) Math.ceil((at2+q.soldiers.get(i).getAT())*(0.5+0.5*q.soldiers.get(i).HP/100));
			tdf = (int) Math.ceil((df1 + soldiers.get(t1).DF)*(0.5+0.5*soldiers.get(t1).HP/100));
			d = Math.max(tat-tdf,1);
			m2[i] = d;
			d1[t1] += d;
		}
		
		for (int i=0;i<l1;i++){
			soldiers.get(i).getDamage(d1[i]);
			soldiers.get(i).makeDamage(m1[i]);
		}
		
		for (int i=0;i<l2;i++){
			q.soldiers.get(i).getDamage(d2[i]);
			q.soldiers.get(i).makeDamage(m2[i]);
		}
		
		this.clearSoldier();
		q.clearSoldier();
		
	}
	
	
	public void fightSquad2(Squad q){
		int l1 = soldiers.size();
		int l2 = q.soldiers.size();
		
		int at1 = tAT[type] + nAT[type][q.type];
		int at2 = tAT[q.type] + nAT[q.type][type];
		
		int df1 = tDF[type] + nDF[type][q.type];
		int df2 = tDF[q.type] + nDF[q.type][type];
		
		
		int t2 = 0;
		int d1[] = new int[l1];
		int d2[] = new int[l2];
		
		for (int i=0;i<l1;i++){
			d2[t2] += Math.max((at1+soldiers.get(i).AT - df2 - q.soldiers.get(t2).DF),1);
			t2++;
			if (t2>=l2) t2 = 0;
		}

		for (int i=0;i<l2;i++){
			q.soldiers.get(i).getDamage(d2[i]);
		}
		
		q.clearSoldier();
		if (!q.isDead()){
			int t1 = 0;
			for (int i=0;i<l2;i++){
				d1[t1] += Math.max((at2+q.soldiers.get(i).AT - df1 - soldiers.get(t1).DF),1);
				t1++;
				if (t1>=l1) t1 = 0;
			}

			for (int i=0;i<l1;i++){
				soldiers.get(i).getDamage(d1[i]);
			}
		}
		this.clearSoldier();
		
		
	}
	
	public Soldier getGeneral(){
		long max = 0;
		int maxi = -1;
		for (int i=0;i<soldiers.size();i++){
			if (soldiers.get(i).makeDamage>max){
				max = soldiers.get(i).makeDamage;
				maxi = i;
			}
		}
		return soldiers.get(maxi);
	}
	
	public void clearSoldier(){
		ArrayList<Soldier> rest = new ArrayList<Soldier>();
		
		for (Soldier s : soldiers){
			if (!s.isDead()){
				rest.add(s);
			}
			else{
//				rest.add(new Soldier());
			}
		}
		soldiers = rest;
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
	
	public void showInfo(){
		System.out.println(this.types[type] + " " + getSoldierNum()+" " +getTotalHP() +"  " + getTotalAT());
		if (this.isDead()){
			System.out.println("dead ");
		}
		else{
			for(Soldier s: soldiers){
				s.showInfo();
			}
			System.out.println();
		}
	}
	
	public int getTotalAT(){
		int temp = 0;
		for (Soldier s: soldiers){
			temp += s.AT;
		}
		return temp;
	}
	
	public int getTotalHP(){
		int temp = 0;
		for (Soldier s: soldiers){
			temp += s.HP;
		}
		return temp;
	}
	
	public boolean isDanger(){
		if (this.getSoldierNum()<=(MAXNUM/4)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public int requestBackUp(){
		int temp = MAXNUM-soldiers.size();
		for(int i = 0;i<temp;i++){
			soldiers.add(new Soldier());
		}
		return temp;
	}
	
	public boolean isDead(){
		clearSoldier();
		if (soldiers.size()==0) return true;
		else return false;
	}
}
