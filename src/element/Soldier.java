package element;
import mainrun.RandGen;
import mainrun.RandomNameGenerator;


public class Soldier {
	public String firstName;
	public String lastName;
	public int HP;
	public static int MAXHP = 100;
	public long makeDamage = 1;
	public int age = 20;
	public int AT,DF,LD;
		
	public int level = 0;
	
	public Soldier(){
		firstName = RandomNameGenerator.getFirstName();
		lastName = RandomNameGenerator.getLastName();
		HP = MAXHP;
		AT = RandGen.getRandomNumber(5, 20);
		DF = RandGen.getRandomNumber(0, 5);
		LD = 0;
	}

	public static int LEVELT = 10;
	public static int MAXLEVEL = 100;
	public void makeDamage(int d){
//		long increasedLevel = (makeDamage+d)/LEVELT - (makeDamage)/LEVELT;
		int increasedLevel = calcLevel(makeDamage+d) - level;
		while (increasedLevel>0){
			upgrade();
			increasedLevel--;
		}
		makeDamage+=d;
	}
	
	public int calcLevel(long d){
		return (int)(Math.log(d/LEVELT)/Math.log(2));
	}
	public void upgrade(){
		if (this.getLevel()<MAXLEVEL){
			level++;
			if (RandGen.getRandomNumber(0, 10)>3){
				AT++;
			}
			else{
				DF++;
			}
		}
	}
	
	public int getAT(){
		return RandGen.getRandomNumber(0, AT);
	}
	
	public void getDamage(int d){
		if (d==0){
			HP = Math.min(MAXHP,HP + 1);
		}
		else{
			HP-=d;
		}
	}
	
	public boolean isDead(){
		return (HP<=0);
	}
	
	public String getInfo(){
		return ("HP:"+HP + " AT:" + AT + " DF:"+DF);
	}
	
	public String getName(){
		return (firstName + "." + lastName + " LV" + level);
	}
	
	public void showInfo(){
		System.out.print(HP+"/"+AT+"/"+DF+"-LV"+getLevel()+"  ");
//		System.out.print(HP+" ");
	}
	public int getLevel(){
		return level;
	}
	
}
