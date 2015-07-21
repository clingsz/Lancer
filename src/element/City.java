package element;

import java.awt.Point;
import java.util.Random;

import mainrun.NumberFormatter;
import mainrun.RandGen;
import mainrun.RandomNameGenerator;

import screen.ControlPanel;
import screen.Screen;


public class City extends WorldUnit{
	public int population;
	public static int MAXPOPULATION = 10000;
	public String cityName; 
	public City(Player p,Point np){
		super(p, np);
		population = 500;
		food = 500;
		cityName = RandomNameGenerator.getPlaceName();
		squads.add(new Squad(p));
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
		if (food>maxFood()) food = maxFood();
		energy = 10000;
		breed();
	}
	
	public boolean recruit(int t){
		if (food<Squad.squadPrice(t) | population<Squad.MAXNUM) return false;
		food-=Squad.squadPrice(t);
		population-=Squad.MAXNUM;
		squads.add(new Squad(player,t));
		energy--;
		return true;
	}
	
	public String getPopulationNumString(){
		return NumberFormatter.format(population);
	}
	
	public int maxFood(){
		return population*10;
	}
	
	public void breed(){
		population*=1+RandGen.getRandomDouble(0.1)*(food/maxFood());
		if (population>MAXPOPULATION)
			population = MAXPOPULATION;
	}
}
