import java.awt.Point;


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
				
		cp.drawImage(screen,cp.unitPosX,cp.unitPosY,getImageString());
		cp.drawString(screen,cp.unitPosX+1,cp.unitPosY,cityName+" City");
		
		cp.drawImage(screen,cp.unitPosX+4,cp.unitPosY+1,"population");
		cp.drawString(screen,cp.unitPosX+5,cp.unitPosY+1,NumberFormatter.format(population));
	}
}
