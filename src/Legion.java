import java.awt.Point;
import java.util.ArrayList;


public class Legion extends WorldUnit implements Renderable{
	public static final int MAXNUM = 16;
	Soldier general;
	public Legion(Player p, Point np){
		super(p, np);
		for(int i = 0; i<MAXNUM; i++){
			squads.add(new Squad(p));
		}
		general = new Soldier();
	}
	
	public String getLegionName(){
		return general.lastName;
	}
	
	public void render(Screen screen, int xp, int yp, int size){
		screen.render(xp, yp, size, "legion" + player.id);
	}
	public String getImageString(){
		return ("legion" + player.id);
	}
	
	public void tick(InputHandler input,World world){
		if (energy>0){
			int mx = 0;
			int my = 0;
			if (input.up.down) my = -1;
			if (input.down.down) my = 1;
			if (input.left.down) mx = -1;
			if (input.right.down) mx = 1;
			moveLegion(mx,my,world);
		}
	}
	
	public void moveLegion(int mx,int my, World world){
		if (world.isValidToMove(mx+x, my+y)){
			x+=mx;
			y+=my;
			player.updateHash();
			System.out.println(energy);
			energy--;
		}
	}
	
	public void renderInfo(Screen screen, ControlPanel cp){
		super.renderInfo(screen, cp);
		cp.drawImage(screen,cp.unitPosX,cp.unitPosY,getImageString());
		cp.drawString(screen,cp.unitPosX+1,cp.unitPosY,general.lastName + " Legion");
//		cp.drawImage(screen,cp.unitPosX,cp.unitPosY+1,"food");
//		cp.drawString(screen,cp.unitPosX+1,cp.unitPosY+1, NumberFormatter.format(food));
		
		
	}
	
}
