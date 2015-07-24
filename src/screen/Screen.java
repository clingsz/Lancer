package screen;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;


public class Screen {
	int w,h;
//	public int WORLDHEIGHT;
//	public int WORLDWIDTH;
	public BufferedImage image; 
	Graphics g;
	int[] pixels;
	ImageSheet imageSheet = new ImageSheet();
	public Screen(int w, int h){
		this.w = w;
		this.h = h;
		this.image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		this.g = image.getGraphics();
		imageSheet.init();
//		this.WORLDHEIGHT = WORLDHEIGHT;
//		this.WORLDWIDTH = w;
	}
	public void clear(Color c) {
		g.setColor(c);
		g.fillRect(0, 0, w, h);
	}
	
	public void render(int xp, int yp, int size, Color c){
		g.setColor(c);
		g.fillRect(xp, yp, size, size);
	}
	public void render(int xp, int yp, int size, Image img){
		g.drawImage(img,xp,yp,size,size, null);
	}
	public void render(int xp, int yp, int size, String name){
		g.drawImage(imageSheet.getImage(name),xp,yp,size,size, null);
	}
	public void render(int xp, int yp, int sizeX, int sizeY, String name){
		g.drawImage(imageSheet.getImage(name),xp,yp,sizeX,sizeY, null);
	}
	public void render(int xp, int yp, String dispString) {
		this.render(xp, yp, dispString, 12);
	}
	public void render(int xp, int yp, String dispString,int Fontsize) {
		g.setColor(Color.BLACK);
		Font myFont = new Font("Arial", Font.PLAIN, Fontsize);
		g.setFont(myFont);
		g.drawString(dispString, xp, yp);
	}
	public void drawLine(int ax, int ay, int tx, int ty) {
		g.drawLine(ax, ay, tx, ty);
	}
	public void drawLine(int ax, int ay, int tx, int ty, int batSize) {
		g.drawLine(ax+batSize/2, ay+batSize/2, tx+batSize/2, ty+batSize/2);
	}
}
