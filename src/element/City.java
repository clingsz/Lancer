package element;

import java.awt.Point;

import mainrun.NumberFormatter;
import mainrun.RandomNameGenerator;

import screen.ControlPanel;
import screen.Screen;


public class City extends WorldUnit implements Renderable{
	public int population;
	public String cityName; 
	public City(Player p,Point np){
		super(p, np);
		population = 500;
		food = 500;
		cityName = RandomNameGenerator.getPlaceName();
		squads.add(new Squad(p));
	}
	public void render(Screen screen, int xp, int yp, int size) {
		screen.render(xp, yp, size, "city"+player.id);
	}
	
	public String getImageString(){
		return ("city"+player.id);
	}
	public int getPopulation(){
		return population;
	}
	public void nextDay(){
		food+=population/2;
		super.nextDay();
	}
	
	public void renderInfo(Screen screen, ControlPanel cp){
		super.renderInfo(screen, cp);
				
		cp.drawImage(screen,1,1,getImageString());
		cp.drawString(screen,0,2,cityName+" City");
		
		cp.drawImage(screen,6,2,"population");
		cp.drawString(screen,7,2,NumberFormatter.format(population));
	}
}
