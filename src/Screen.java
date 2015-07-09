import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;


public class Screen {
	int w,h;
	BufferedImage image; 
	Graphics g;
	int[] pixels;
	ImageSheet imageSheet = new ImageSheet();
	public Screen(int w, int h){
		this.w = w;
		this.h = h;
		this.image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		this.g = image.getGraphics(); 
		imageSheet.init();
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
		this.render(xp, yp, dispString, 14);
	}
	public void render(int xp, int yp, String dispString,int Fontsize) {
		Font myFont = new Font("Serif", Font.BOLD, Fontsize);
		g.setFont(myFont);
		g.drawString(dispString, xp, yp);
	}
}
