package mainrun;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import element.City;
import element.Legion;
import element.Player;
import element.World;
import screen.CityMenu;
import screen.LegionMenu;
import screen.Menu;
import screen.StartMenu;
import screen.Viewer;
import screen.WorldMenu;



public class GameState {
	
	public World world = new World();
	public InputHandler input;
	public Viewer viewer;
		
	int id;
	
	public boolean worldBrowsing = false;
	public boolean showMenu = false;
	
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
	
	public Menu menu = null;
	
	public void init(int w, int h){
		viewer = new Viewer(w,h);
		setMenu(new StartMenu());
	}
		
	public void resetGame(){
		world.init(128, 128, input);
		viewer.init(world);
		clearMenu();
	}
	
	public void clearMenu(){
		menu = null;
	}
	
	public BufferedImage getImage(){
		return viewer.getImage();
	}
	
	public void render(){
		if (!(menu instanceof StartMenu))
		{
			viewer.render(world);
		}
		if (menu!=null){
			viewer.renderMenu(menu);
		}
	}

	Legion legion = null;
	public void moveLegion(Legion legion){
		clearMenu();
		this.legion = legion;
	}

	public void tick(){
		if (menu!=null){
			menu.tick();
		}
		else if (legion!=null){
			int mx = 0;
			int my = 0;
			if (input.up.clicked) my = -1;
			if (input.down.clicked) my = 1;
			if (input.left.clicked) mx = -1;
			if (input.right.clicked) mx = 1;
			if (mx!=0 || my!=0){
				legion.moveLegion(mx, my, world);
				viewer.setFocus(world, legion.x, legion.y);
			}
			if (input.enter.clicked){
				legion = null;
				viewer.clearSelection();
				setMenu(new WorldMenu());
			}
			if (input.cancel.clicked){
				legion = null;
				viewer.clearSelection();
			}
			if (input.next.clicked){
				legion = null;
				viewer.clearSelection();
				world.nextDay();
			}
			if (input.select.clicked){
				setMenu(new LegionMenu(legion));
				legion = null;
			}
		}
		else{	
			int mx = 0;
			int my = 0;
			if (input.up.down) my = -1;
			if (input.down.down) my = 1;
			if (input.left.down) mx = -1;
			if (input.right.down) mx = 1;
			if (input.enter.clicked) this.setMenu(new WorldMenu());
			viewer.moveFocus(world,mx,my);
			if (input.select.clicked && viewer.canSelectFocus(world.currentPlayerID)){
				viewer.selectUnit();
				viewer.render(world);
				if (viewer.selectedUnit instanceof City){
					setMenu(new CityMenu((City)viewer.selectedUnit));
				}
				else{
					setMenu(new LegionMenu((Legion)viewer.selectedUnit));
				}
			}
			if (input.next.clicked){
				world.nextDay();
			}
		}
	}
	
	public void setMenu(Menu m){
		this.menu = m;
		m.init(input, this);
	}
	
	public void endGame(){
		System.exit(0);
	}
	
}