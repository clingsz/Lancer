package screen;

import element.City;
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
	
	
}
