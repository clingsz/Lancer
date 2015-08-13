package screen;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import mainrun.Game;
import mainrun.InputHandler;
import mainrun.RandomNameGenerator;
import element.City;
import element.Legion;
import element.Player;
import element.Squad;
import element.Terrian;
import element.World;
import element.WorldUnit;

public class Viewer {
	Screen screen;
	ControlPanel controlPanel;
	
	public World world = new World();	
	public Battle battle = null;
	public Legion legion = null;	
	
	Worldviewer worldviewer;
	Battleviewer battleviewer;
	InputHandler input;
	public State state;
	
	public enum State{
		startMenu,
		world,
		battle
	};
	
	public Menu menu;
	public StartMenu startMenu;
	
	public Viewer(int w, int h, Game game){
		input = new InputHandler(game);
		input.linkViewer(this);
		screen = new Screen(w,h);
		
		init();
		
		int WORLDHEIGHT = h*3/4;
		worldviewer = new Worldviewer(0,0,w,WORLDHEIGHT,screen);
		controlPanel = new ControlPanel(0,WORLDHEIGHT,w,h/4,worldviewer);
		battleviewer = new Battleviewer(0,0,w,h);
		startMenu = new StartMenu(this);
		setStartMenu();
	}
	
	public void init(){
		RandomNameGenerator.init();
		Squad.init();
	}
	
	public void setStartMenu(){
		menu = startMenu;
		state = State.startMenu;
	}
		
	public void setWorld(){
		worldviewer.init(this.world);
		state = State.world;
	}
	
	public void setBattle(){
		state = State.battle;
	}
	
	public void resetGame(){
		world.init(64, 64, input);
		setWorld();
	}
	
	public void endGame(){
		System.exit(0);
	}
	
	public void testBattle(){
		battle = new Battle(this);
		battle.testPlay = true;
	}
	
	private void setBattle(WorldUnit attackTarget) {
		state = State.battle;
		if (attackTarget instanceof City){
			City ac = (City)(attackTarget);
			System.out.println(legion.getLegionName()+ " is battling with city " + ac.cityName);
		}
		else{
			Legion al = (Legion)(attackTarget);
			System.out.println(legion.getLegionName()+ " is battling with legion " + al.getLegionName());
		}
		battle = new Battle(this,legion,attackTarget);
	}
	
	public void render(){
		if (state == State.startMenu){
			renderMenu();
		}
		else if (state == State.world){
			worldviewer.render();
			controlPanel.render(screen,worldviewer);
		}
		else if (state == State.battle){
//			worldviewer.showLegionPossibleMove(legion);
//			worldviewer.findAttackTarget(legion);
		}
	}
	
	public void click(int mx, int my){
		if (state == State.startMenu){
			menu.click(mx, my);
		}
		else if(state == State.world){
			if (worldviewer.isInRegion(mx, my)){
				worldviewer.click(mx, my);
			}
			else if (controlPanel.isInRegion(mx, my)){
				controlPanel.click(mx, my);
			}
		}
	}
	
	public void keyPress(){
		if (state == State.world){
			worldviewer.keyPress(input);
		}
	}
	
//	public void tick(InputHandler input,GameState g){
//		if (input.mouse.clicked){
//			int mx = input.clickX;
//			int my = input.clickY;
//			
//			if (g.state == State.startMenu){
//				menu.click(mx, my);
//			}
//			else if (g.state == State.world && worldviewer.isInRegion(mx,my)){
//				worldviewer.click(mx, my);
//			}
//			else if (g.state == State.world && controlPanel.isInRegion(mx,my)){
//				controlPanel.click(mx, my);
//			}
//			else if (g.state == State.battle){
//				battleviewer.click(mx, my);
//			}
//		}
//	}
	
	public void renderMenu(){
		menu.render(screen);
	}
	
	public void renderControlBoard(){
		this.controlPanel.render(screen,worldviewer);
		if (this.worldviewer.focusUnit!=null){
			controlPanel.renderUnitInfo(screen,this.worldviewer.focusUnit);
		}
	}
		
	public BufferedImage getImage(){
		return screen.image;
	}

}
