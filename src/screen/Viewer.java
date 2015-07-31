package screen;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import mainrun.GameState;
import mainrun.GameState.State;
import mainrun.InputHandler;

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
	Worldviewer worldviewer;
	Battleviewer battleviewer;
	InputHandler input;
	GameState gameState;
	
	public Menu menu;
	public StartMenu startMenu;
	
	public Viewer(int w, int h, InputHandler input, GameState gameState){
		screen = new Screen(w,h);
		int WORLDHEIGHT = h*3/4;
		controlPanel = new ControlPanel(0,WORLDHEIGHT,w,h/4);
		worldviewer = new Worldviewer(0,0,w,WORLDHEIGHT,screen);
		battleviewer = new Battleviewer(0,0,w,h);
		this.input = input;
		startMenu = new StartMenu(screen,input,gameState);
		this.gameState = gameState;
	}
	
	public void setStartMenu(){
		menu = startMenu;
	}
		
	public void setWorld(World w){
		worldviewer.init(w);
	}
	
	public void render(GameState g){
		if (g.state == State.startMenu){
			renderMenu();
		}
		else if (g.state == State.world){
			
		}
		else if (g.state == State.battle){
			worldviewer.showLegionPossibleMove(g.legion);
			worldviewer.findAttackTarget(g.legion);
		}
	}
	
	public void click(int mx, int my){
		if (gameState.state == State.startMenu){
			menu.click(mx, my);
		}
	}
	
	public void tick(InputHandler input,GameState g){
		if (input.mouse.clicked){
			int mx = input.clickX;
			int my = input.clickY;
			
			if (g.state == State.startMenu){
				menu.click(mx, my);
			}
			else if (g.state == State.world && worldviewer.isInRegion(mx,my)){
				worldviewer.click(mx, my);
			}
			else if (g.state == State.world && controlPanel.isInRegion(mx,my)){
				controlPanel.click(mx, my);
			}
			else if (g.state == State.battle){
				battleviewer.click(mx, my);
			}
		}
	}
	
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
