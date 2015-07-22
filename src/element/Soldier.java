package element;
import mainrun.RandGen;
import mainrun.RandomNameGenerator;


public class Soldier {
	public String firstName;
	public String lastName;
	public int HP;
	public static int MAXHP = 100;
	public int kills = 0;
	public int age = 20;
	public int AT,DF,LD;
		
	
	public Soldier(){
		firstName = RandomNameGenerator.getFirstName();
		lastName = RandomNameGenerator.getLastName();
		HP = MAXHP;
		AT = RandGen.getRandomNumber(5, 20);
		DF = RandGen.getRandomNumber(0, 5);
		LD = 0;
	}
	
	public void upgrade(){
		kills++;
		if (RandGen.getRandomNumber(0, 10)>6){
			AT++;
		}
		else{
			DF++;
		}
	}
	
	public int getAT(){
		return RandGen.getRandomNumber(0, AT);
	}
	
	public void getDamage(int d){
		HP-=d;
	}
	
	public boolean isDead(){
		return (HP<=0);
	}
	
	public void showInfo(){
//		System.out.print(HP+"/"+AT+"/"+DF+" ");
		System.out.print(HP+" ");
	}
	
}
