
public class Soldier {
	public String firstName;
	public String lastName;
	public int HP;
	public Soldier(){
		firstName = RandomNameGenerator.getFirstName();
		lastName = RandomNameGenerator.getLastName();
		HP = 100;
	}
	
}
