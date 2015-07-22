package element;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
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
	DateFormat dateformat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
	Date date;
	
	public int currentPlayerID = 1;
	
	int dx[] = {0,0,1,0,-1};
	int dy[] = {0,1,0,-1,0};
	
	public World(){

	}
	
	public void init(int w, int h, InputHandler in){
		RandomNameGenerator.init();
		Width = w;
		Height = h;
		input = in;
		terrian = new Terrian[Width][Height];
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
			players.add(new Player(this,i));
		}
	}
	
	public boolean isValidToPlace(int x, int y){
		if (!isInWorld(x,y)) return false;
		if (this.getUnitAt(x,y)!=null) return false;
		if (getTerrianAt(x,y).isValidToPut()) return true;
		return false;
	}
	
	public boolean isValidToMove(int x, int y, Legion legion){
		if (!isInWorld(x,y)) return false;
		if (this.getLegionAt(x,y)!=null) {
//			System.out.println("Legion Block");
			return false;
		}
		if (this.getCityAt(x,y)!=null && this.getCityAt(x,y).player.id!=legion.player.id) return false;
		if (getTerrianAt(x,y).isValidToMove()) return true;
		return false;
	}

	
	public boolean isInWorld(int tx, int ty){
		if (tx<0 || tx>=Width || ty<0 || ty>=Height) return false;
		return true;
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
	
	
	
	public Terrian getTerrianAt(int x, int y){
		return(terrian[x][y]);
	}
	
	public WorldUnit getUnitAt(int x, int y){
		WorldUnit wu = null;
		for(Player p: players){
			wu = p.getUnitAt(x, y);
			if (wu!=null) return wu;
		}
		return wu;
	}
	
	public Legion getLegionAt(int x, int y){
		Legion wu = null;
		for(Player p: players){
			wu = p.getLegionAt(x, y);
			if (wu!=null) return wu;
		}
		return wu;
	}
	
	public City getCityAt(int x, int y){
		City wu = null;
		for(Player p: players){
			wu = p.getCityAt(x, y);
			if (wu!=null) return wu;
		}
		return wu;
	}
	
	
	public String getDate(){
		return dateformat.format(date);
	}
	
	public Image getMiniMap(){
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
		
	public void nextDay(){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);  // number of days to add
		date = c.getTime();
		for(Player p : players){
			p.nextDay();
		}
	}

	public ArrayList<Point> getPossibleMove(Legion legion) {
		ArrayList<Point> temp = new ArrayList<Point>();
		ArrayList<Integer> e = new ArrayList<Integer>();
		temp.add(new Point(legion.x,legion.y));
		e.add(legion.energy);
		int head = 0;
		int tail = 1;
		int nx = 0;
		int ny = 0;
		Point p,np;
		int enow = 0;
		while(head<tail){
			p = temp.get(head);
			enow = e.get(head);
			if (enow==0) break;
			for(int i = 1;i<=4;i++){
				nx = dx[i]+p.x;
				ny = dy[i]+p.y;
				np = new Point(nx,ny);
				if (!temp.contains(np) && this.isValidToMove(nx, ny, legion)){
//					System.out.println(np);
					temp.add(np);
					e.add(enow-1);
					tail++;
				}
			}
			head++;
		}
		return temp;
	}
	
	public ArrayList<WorldUnit> findAttackTarget(Legion legion){
		ArrayList<WorldUnit> temp = new ArrayList<WorldUnit>();
		int nx,ny;
		for (int i = 1;i<=4; i++){
			nx = dx[i]+legion.x;
			ny = dy[i]+legion.y;
			if (this.getLegionAt(nx,ny)!=null && this.getLegionAt(nx,ny).isEnemy(legion)){
				temp.add(this.getLegionAt(nx,ny));
			}
			else if (this.getCityAt(nx,ny)!=null  && this.getCityAt(nx,ny).isEnemy(legion)){
				temp.add(this.getCityAt(nx,ny));
			}
		}
		return temp;
	}
	
}	
