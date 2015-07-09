import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.ImageIcon;

public class ImageSheet {
	//Terrain
	//Unit
	HashMap<String,Image> ImageBase = new HashMap<String,Image>();
	public void init(){
		ImageMaker IM = new ImageMaker();
		Image img = null;
		try{
			for(int i = 0 ; i < Terrian.names.length; i++){
				img = new ImageIcon("res/pic/" + Terrian.names[i] + ".jpg").getImage();
				ImageBase.put(Terrian.names[i] , img);
			}
			Image imglegion = new ImageIcon("res/pic/" + "legion" + ".jpg").getImage();
			Image imgcity = new ImageIcon("res/pic/" + "city" + ".jpg").getImage();
			Image imgsquad[] = new Image[Squad.types.length];
			for(int j = 0 ; j < Squad.types.length;j++){
				imgsquad[j] = new ImageIcon("res/pic/" + Squad.types[j] + ".jpg").getImage();
			}
			for(int i = 0 ; i < Player.MAXNUM; i++){
				ImageBase.put( "legion"+i, IM.setColor(imglegion, Player.playerColor[i], 0));
				ImageBase.put( "city"+i, IM.setColor(imgcity, Player.playerColor[i], 0));
				for(int j = 0 ; j < Squad.types.length;j++){
					ImageBase.put( Squad.types[j]+i, IM.setColor(imgsquad[j], Player.playerColor[i], 0));
				}
			}
			
			Image imgControlBoard = new ImageIcon("res/pic/controlPanel.jpg").getImage();
			ImageBase.put("controlPanel",imgControlBoard);
			Image imgChoice = new ImageIcon("res/pic/choice.jpg").getImage();
			ImageBase.put("choice",IM.getAlpha(imgChoice));
			Image imgCalendar = new ImageIcon("res/pic/calendar.jpg").getImage();
			ImageBase.put("calendar",imgCalendar);
			ImageBase.put("food",new ImageIcon("res/pic/food.jpg").getImage());
			ImageBase.put("population",new ImageIcon("res/pic/population.jpg").getImage());
		}
		catch (Exception e){
			e.printStackTrace();
		}
//		ImageMaker IM = new ImageMaker();
//		img = IM.getAlpha(imgRoad0);
//		img = IM.getRotate(img[1]);
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
