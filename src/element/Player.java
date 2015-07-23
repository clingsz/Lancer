package element;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import mainrun.RandomNameGenerator;

import screen.Screen;


public class Player{
	
	public String name;
	public boolean neutral;
	
	public static int playerCount = 0;
	public static Color playerColor[] = {Color.gray,Color.red,Color.blue,Color.GREEN,Color.yellow};
	public static int MAXNUM = playerColor.length;
	public int id = 0;
	public Color color;
	
	public ArrayList<City> cities = new ArrayList<City>();
	public ArrayList<Legion> legions = new ArrayList<Legion>();
	
	public HashMap<Point,WorldUnit> hashTable = new HashMap<Point,WorldUnit>();
	
	public HashMap<Point,Legion> hashLegion = new HashMap<Point,Legion>();
	public HashMap<Point,City> hashCity = new HashMap<Point,City>();
	
	public Player(int id){
		this.id = id;
		color = playerColor[id];
	}
	
	public Player(World world,int id){
		name = RandomNameGenerator.getLastName();
		this.id = id;
		color = playerColor[id];
		RandGenCity(world);
		if (id==0){
			for(int i = 0;i<20;i++)
			RandGenCity(world);
		}
		else{
			addNewLegion(new Legion(this, new Point(cities.get(0).x,cities.get(0).y+1)));
		}
		
//		RandGenLegion(world);
	}
	
	public void nextDay(){
		for(City c : cities){
			c.nextDay();
		}
		for(Legion l : legions){
			l.nextDay();
		}
	}
	
	public Point getHome(){
		return (new Point(cities.get(0).x,cities.get(0).y));
	}
	
	public void addNewCity(City newCity){
		cities.add(newCity);
		hashTable.put(new Point(newCity.x,newCity.y), newCity);
		hashCity.put(new Point(newCity.x,newCity.y), newCity);
	}
	
	public void addNewLegion(Legion newLegion){
		legions.add(newLegion);
		hashTable.put(new Point(newLegion.x,newLegion.y), newLegion);
		hashLegion.put(new Point(newLegion.x,newLegion.y), newLegion);
	}
	
	public void updateHash(){
		hashTable.clear();
		hashCity.clear();
		hashLegion.clear();
		for (City c: cities){
			hashTable.put(new Point(c.x,c.y), c);
			hashCity.put(new Point(c.x,c.y), c);
		}
		for (Legion c: legions){
			hashTable.put(new Point(c.x,c.y), c);
			hashLegion.put(new Point(c.x,c.y), c);
		}
	}
	
	public void RandGenCity(World world){
		Point np = world.genRandValidLocation();
		City newCity = new City(this, np);
		addNewCity(newCity);
	}
	
	public void RandGenLegion(World world){
		Point np = world.genRandValidLocation();
		Legion newLegion = new Legion(this, np);
		addNewLegion(newLegion);
	}
	
	public boolean hasUnitAt(int x , int y){
		return (hashTable.get(new Point(x,y))==null);
	}
	
	public WorldUnit getUnitAt(int x, int y){
		return (hashTable.get(new Point(x,y)));
	}
	
	public City getCityAt(int x, int y){
		return (hashCity.get(new Point(x,y)));
	}
	
	public Legion getLegionAt(int x, int y){
		return (hashLegion.get(new Point(x,y)));
	}
	
	public boolean isNeutral(){
		return neutral;
	}
	
	public boolean isEnemy(Player p){
		if (p!=this) return true;
		return false;
	}
	
	
	public boolean isFriend(Player p){
		if (p==this) return true;
		return false;
	}
	
}
