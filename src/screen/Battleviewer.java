package screen;

import element.Squad;

public class Battleviewer extends Vobject{
	int batX,batY,batW,batH,batSize;
	
	Battle b;
	
	public Battleviewer(int nx, int ny, int nw, int nh) {
		super(nx, ny, nw, nh);
		batW = nh-80;
		batH = nh-80;
		batX = (nw-batW)/6*5;
		batY = (nh-batH)/2;
		batSize = batW/7;
	}

	public void renderBattle(Screen screen){
		screen.render(0, 0,screen.w,screen.h,"message");
		int sx,sy;
		for(int i = 0; i<b.w;i++) for(int j = 0;j<b.h;j++){
			screen.render(batX+i*batSize, batY+j*batSize, batSize,"grass");
		}
		for(int i = 0;i<2;i++) for(int j=0;j<10;j++){
			Squad sq = b.A[i].getSquadAtOrder(j);
			if (sq!=null){
				screen.render(batX+(int)((b.sx[i][j]+0.8)*batSize), batY+(int)((b.sy[i][j]+0.2)*batSize),j+"");
				screen.render(batX+b.sx[i][j]*batSize, batY+b.sy[i][j]*batSize, batSize,sq.getImageString());
				screen.render(batX+(int)((b.sx[i][j]+0.6)*batSize), batY+(int)((b.sy[i][j]+0.9)*batSize),sq.getSoldierNum()+"");
			}
		}
		
		// render option
		int optSize = batSize/5*4;
		for(int i = 0;i<b.battleOptions.length;i++){
			sx = 10+optSize*i;
			sy = screen.h/5*4;
			screen.render(sx,sy,optSize,b.battleOptions[i]);
			if (i==b.selection && b.selectOrder){
				screen.render(sx,sy,optSize,"choice");
			}
		}
		// render selected squad
		if (b.selectSquad>=0){
			int i = b.getSelectedSquadI();
			int j = b.getSelectedSquadJ();
			int ax = batX+b.sx[i][j]*batSize;
			int ay = batY+b.sy[i][j]*batSize;
			
			screen.render(ax,ay, batSize,"selected");
			
			i = b.getSelectedSquadTargetI();
			j = b.getSelectedSquadTargetJ();
			if (i>=0 && j>=0){
				int tx = batX+b.sx[i][j]*batSize;
				int ty = batY+b.sy[i][j]*batSize;
				screen.render(tx,ty, batSize,"target");
				screen.drawLine(ax,ay,tx,ty);
			}
			Squad sq = b.getSelectedSquad();
			int solSize = batSize/5*3;
			if (sq!=null){
				for (i = 0; i<sq.soldiers.size();i++){
					int x = 20;
					int y = 40+solSize*i;
					screen.render(x,y, solSize,sq.getImageString());
					screen.render(x+solSize,y+solSize/2, sq.soldiers.get(i).getName());
					screen.render(x+solSize,y+solSize, sq.soldiers.get(i).getInfo());
				}
			}
		}
		
		for(int i =0;i<20;i++){
			if (b.getSquadAtIndex(i)!=null && b.targets[i]>=0){
				int a = i/10;
				int a2 = 1-a;
				int j = i;
				if (j>=10) j-=10;
				int ax = batX+b.sx[a][j]*batSize;
				int ay = batY+b.sy[a][j]*batSize;

				j = b.targets[i];
				if (j>=10) j-=10;
				int tx = batX+b.sx[a2][j]*batSize;
				int ty = batY+b.sy[a2][j]*batSize;
				screen.render(tx,ty, batSize,"target");
				screen.drawLine(ax,ay,tx,ty,batSize);
			}
		}
		
	}


}
