package mainrun;
import java.util.ArrayList;

import element.Player;
import element.World;

import screen.CityMenu;
import screen.ControlPanel;
import screen.LegionMenu;
import screen.Menu;
import screen.Screen;
import screen.StartMenu;
import screen.WorldMenu;


public class GameState {
	
	public World world = new World();
	ControlPanel controlPanel = new ControlPanel(); 
	public InputHandler input;
	
	public Menu legionMenu = new LegionMenu();
	public Menu cityMenu = new CityMenu();
	public Menu startMenu = new StartMenu();
	public Menu worldMenu = new WorldMenu();
	
	int id;
	
	
	public GameState(InputHandler input2){
		this.input = input2;
	}
	
	public State state;
	
	public enum State{
		startMenu,
		worldBrowsing,
		worldMenu,
		legionMenu,
		legionMove,
		cityMenu,
		cityRecruit,
		battleBrowsing
	};
	
	
	public void init(Screen screen){
		state = State.startMenu;
		controlPanel.init(screen);
	}
		
	public void resetGame(){
		world.init(64, 64, input);
	}
	

	public void tick(){
		if (state == State.startMenu){
			startMenu.tick(this,input);
		}
		else if (state == State.worldBrowsing){
			world.tick(this);
		}
		else if (state == State.worldMenu){
			worldMenu.tick(this, input);
		}
		else if (state == State.legionMenu){
			legionMenu.tick(this, input);
		}
		else if (state == State.cityMenu){
			cityMenu.tick(this, input);
		}
	}
	
	public void render(Screen screen){
		if (state == State.startMenu){
			startMenu.render(screen);			
		}
		else if (state == State.worldBrowsing){
			world.render(screen);
			controlPanel.render(screen, world);
		}
		else if (state == State.worldMenu){
			world.render(screen);
			controlPanel.render(screen, world);
			worldMenu.render(screen);
		}
		else if (state == State.legionMenu){
			world.render(screen);
			controlPanel.render(screen, world);
			legionMenu.render(screen);
		}
		else if (state == State.cityMenu){
			world.render(screen);
			controlPanel.render(screen, world);
			cityMenu.render(screen);
		}
	}
	
	public boolean checkOK(){
		
	}
	
}
