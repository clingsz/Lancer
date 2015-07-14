package element;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JFrame;

import mainrun.GameState;
import mainrun.GameState.State;
import mainrun.InputHandler;
import mainrun.RandGen;
import mainrun.RandomNameGenerator;


import screen.ControlPanel;
import screen.Menu;
import screen.Screen;


public class World{
	static int max_num = 2000000;
	Color color[] = {Color.gray,Color.red,Color.yellow,Color.green,Color.cyan,Color.blue,Color.pink};

	public int Width;
	public int Height;
	public InputHandler input;
	public Terrian terrian[][]; //0 for blank, 1 for bubble, 2 for base, -1 for block
	public ArrayList<Player> players;
	public int size;
	DateFormat dateformat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
	Date date;
	int focusX,focusY;
	
	public int currentPlayerID = 1;
	public WorldUnit selectedUnit = null;
	
	public int screenX,screenY; 
	
	int dx[] = {0,0,1,0,-1};
	int dy[] = {0,1,0,-1,0};

	public Menu currentMenu = null;

	
	public World(){

	}
	
	public void init(int w, int h, InputHandler in){
		RandomNameGenerator.init();
		Width = w;
		Height = h;
		input = in;
		terrian = new Terrian[Width][Height];
		screenX = 0; screenY = 0;
		players = new ArrayList<Player>();
		try {
			date = dateformat.parse("Jan 25, 1350");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int cnt = 0;
		
		byte[] map = TerrianGen.createAndValidateTopMap(w, h)[0];
		
		for (int i =0; i<Width; i++) for(int j = 0 ; j < Height; j++) {
			cnt++;
			terrian[i][j] = new Terrian(map[i+j*w]);
		}

		for (int i = 0; i<2;i++){
			players.add(new Player(this));
		}
		size = 30;
		focusX = players.get(1).cities.get(0).x;
		focusY = players.get(1).cities.get(0).y;
		
		screenXOFFSET = 0;
		screenYOFFSET = 0;
	}
	
	public boolean isValidToPlace(int x, int y){
		if (!isInWorld(x,y)) return false;
		if (this.getUnitAt(x,y)!=null) return false;
		if (getTerrianAt(x,y).isValidToPut()) return true;
		return false;
	}
	
	public boolean isValidToMove(int x, int y){
		if (!isInWorld(x,y)) return false;
		if (this.getUnitAt(x,y)!=null) return false;
		if (getTerrianAt(x,y).isValidToMove()) return true;
		return false;
	}

	
	public boolean isInWorld(int tx, int ty){
		if (tx<0 || tx>=Width || ty<0 || ty>=Height) return false;
		return true;
	}
	
	public boolean shouldDisplay(Screen screen, WorldUnit u){
		return shouldDisplay(screen,u.x,u.y);
	}
	
	public boolean shouldDisplay(Screen screen, int x, int y){
		int sw = screen.WORLDWIDTH;
		int sh = screen.WORLDHEIGHT;
		if (x<screenX || x>screenX+sw/size || y<screenY || y>screenY+sh/size) return false;
		return true;
	}
	
	
	
	public int getShowX(int x){
		return screenXOFFSET + (x-screenX)*size;
	}
	
	public int getShowY(int y){
		return screenYOFFSET + (y-screenY)*size;
	}
	
	public Point genRandValidLocation(){
		int rx,ry;
		do{
			rx = getRandX();
			ry = getRandY();
			if (this.isValidToPlace(rx, ry)) break;
		}
		while(true);
		return new Point(rx,ry);
	}
	
	public int getRandX(){
		return RandGen.getRandomNumber(0, Width-1);
	}
	public int getRandY(){
		return RandGen.getRandomNumber(0, Height-1);
	}
	
	public int screenXOFFSET,screenYOFFSET;
	public int showWidth,showHeight;
	
	public void render(Screen screen){
		int sw = screen.WORLDWIDTH;
		int sh = screen.WORLDHEIGHT;
		// render blocks
		for (int x = 0; x<=sw/size; x++)
			for (int y = 0; y<=sh/size; y++){
				int tx = screenX+x;
				int ty = screenY+y;
				if (isInWorld(tx,ty)){
					terrian[tx][ty].render(screen, screenXOFFSET+x*size, screenYOFFSET+y*size, size);
				}
			}
		// render units
		for (Player p : players){
			p.render(screen, this);
		}
		
//		 render controlInfo
		screen.render(getShowX(focusX), getShowY(focusY), size,"choice");
		
//		 render selectedUnit
		if (selectedUnit!=null){
			this.selectedUnit.renderSelected(screen,this);
		}
		
		if (currentMenu!=null){
			currentMenu.render(screen);
		}
	}
	
	
	public Terrian getFocusTerrian(){
		return(terrian[focusX][focusY]);
	}
	
	public Terrian getTerrianAt(int x, int y){
		return(terrian[x][y]);
	}
	
	public String getFocusString(){
		return ("("+focusX+","+focusY+") " + getFocusTerrian().getTerrianName());
	}
	
	public WorldUnit getUnitAtFocus(){
		return getUnitAt(focusX,focusY);
	}
	
	public WorldUnit getUnitAt(int x, int y){
		WorldUnit wu = null;
		for(Player p: players){
			wu = p.getUnitAt(x, y);
			if (wu!=null) return wu;
		}
		return wu;
	}
	
	public void clearSelection(){
		this.selectedUnit = null;
	}
	
	public String getDate(){
		return dateformat.format(date);
	}
	
	public Image getMiniMap(Screen screen, ControlPanel controlPanel){
		int w = Width;
		int h = Height;
		BufferedImage img = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
		int[] pixels = new int[w * h];
		WorldUnit wu = null;
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int i = x + y * w;
				wu = getUnitAt(x, y);
				if (wu!=null){
					int pid = wu.player.id;
					pixels[i] = wu.player.color.getRGB();
				}
				else{
					if (terrian[x][y].id == (byte) Terrian.water.id) pixels[i] = 0x000080;
					if (terrian[x][y].id == Terrian.grass.id) pixels[i] = 0x208020;
					if (terrian[x][y].id == Terrian.hill.id) pixels[i] = 0xa0a0a0;
					if (terrian[x][y].id == Terrian.sand.id) pixels[i] = 0xa0a040;
					if (terrian[x][y].id == Terrian.forest.id) pixels[i] = 0x003000;
				}
			}
		}
		img.setRGB(0, 0, w, h, pixels, 0, w);
		showWidth = (screen.WORLDWIDTH)/size;
		showHeight = (screen.WORLDHEIGHT)/size;
		img.getGraphics().drawRect(screenX, screenY, showWidth, showHeight);
		return img;
	}
	

	public int changeDir(int dir){
		int choice = (int)(Math.random()*2);
		if (choice==0) choice=-1;
		dir+=choice;
		if(dir>4) dir=1;
		if (dir<1) dir=4;
		return dir;
	}


	public boolean isValid(int nx,int ny){
		if (nx>=0&&nx<Width&&ny>=0&&ny<Height) return true;
		return false;
	}
	
	public Player getCurrentPlayer(){
		return players.get(currentPlayerID);
	}
	
	public void tick(GameState g){
			int mx = 0;
			int my = 0;

			if (input.up.down) my = -1;
			if (input.down.down) my = 1;
			if (input.left.down) mx = -1;
			if (input.right.down) mx = 1;
			moveFocus(mx,my);
			if (input.home.clicked) {
				Point np = getCurrentPlayer().getHome();
				focusX = np.x;
				focusY = np.y;
			}
			if (input.enter.clicked){
				g.state = State.worldMenu;
			}
			
			if (input.select.clicked){
				if (this.getUnitAtFocus().player.id == this.currentPlayerID){
					this.selectedUnit = this.getUnitAtFocus();
					if (this.selectedUnit instanceof Legion){
						g.state = State.legionMenu;
					}
					else if (this.selectedUnit instanceof City){
						g.state = State.cityMenu;
					}
					
				}
			}
			
//			if (input.next.clicked){
//				this.nextDay();
//			}
//		}
//		if (gameState.id==2){
//			
//			if (input.cancel.clicked){
//				selectedUnit = null;
//				gameState.setState(GameState.worldbrowse);
//				currentMenu = null;
//			}
//			else{
//				currentMenu = legionMenu;
//				legionMenu.tick(input, this);
////				this.selectedUnit.tick(input,this);
////				setFocus(this.selectedUnit.x,this.selectedUnit.y);
//			}
	}
	
	public void nextDay(){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);  // number of days to add
		date = c.getTime();
		for(Player p : players){
			p.nextDay();
		}
	}
	
	public void setFocus(int x, int y){
		if (isValid(x,y)) {
			focusX = x; focusY = y;
		}
		resetScreenPosition();
	}
	
	public void moveFocus(int dx, int dy){
		if (isValid(dx+focusX,focusY)) focusX+=dx;
		if (isValid(focusX,focusY+dy)) focusY+=dy;
		resetScreenPosition();
	}
	public void resetScreenPosition(){
		screenX = Math.min(Width-showWidth,Math.max(0,focusX - showWidth/2));
		screenY = Math.min(Height-showHeight,Math.max(0,focusY - showHeight/2));
	}
}	
