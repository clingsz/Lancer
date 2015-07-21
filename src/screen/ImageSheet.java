package screen;
import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.ImageIcon;

import element.Player;
import element.Squad;
import element.Terrian;

public class ImageSheet {
	//Terrain
	//Unit
	HashMap<String,Image> ImageBase = new HashMap<String,Image>();
	ImageMaker IM = new ImageMaker();
	public void init(){
		Image img = null;
		try{
			for(int i = 0 ; i < Terrian.names.length; i++){
				img = new ImageIcon("res/pic/" + Terrian.names[i] + ".jpg").getImage();
				ImageBase.put(Terrian.names[i] , img);
			}
			Image imglegion = new ImageIcon("res/pic/" + "legion" + ".jpg").getImage();
			Image imgcity = new ImageIcon("res/pic/" + "city" + ".jpg").getImage();
			Image imgsoldier = new ImageIcon("res/pic/" + "soldier" + ".jpg").getImage();
			Image imgsquad[] = new Image[Squad.types.length];
			for(int j = 0 ; j < Squad.types.length;j++){
				imgsquad[j] = new ImageIcon("res/pic/" + Squad.types[j] + ".jpg").getImage();
			}
			for(int i = 0 ; i < Player.MAXNUM; i++){
				ImageBase.put( "legion"+i, IM.setColor(imglegion, Player.playerColor[i], 0));
				ImageBase.put( "city"+i, IM.setColor(imgcity, Player.playerColor[i], 0));
				ImageBase.put( "soldier"+i, IM.setColor(imgsoldier, Player.playerColor[i], 0));
				for(int j = 0 ; j < Squad.types.length;j++){
					ImageBase.put( Squad.types[j]+i, IM.setColor(imgsquad[j], Player.playerColor[i], 0));
				}
			}
			
			addImageAlpha("calendar");
			addImageAlpha("choice");
			addImageAlpha("selected");
			addImageAlpha("target");
			
			addImage("controlPanel");
			addImage("food");
			addImageAlpha("consumption");
			addImage("population");
			addImage("energy");
			addImage("message");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void addImage(String name){
		ImageBase.put(name,new ImageIcon("res/pic/"+name+".jpg").getImage());
	}
	
	public void addImageAlpha(String name){
		ImageBase.put(name,IM.getAlpha(new ImageIcon("res/pic/"+name+".jpg").getImage()));
	}
	
	public Image getImage(String name){
		Image img = ImageBase.get(name); 
		if (img!=null){
			return img; 
		}
		else{
			System.err.println("None such image "+name);
			return ImageBase.get("void");
		}
	}
}
