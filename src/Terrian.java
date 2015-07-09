
public class Terrian implements Renderable{
	// water, grass, forest, hill, sand.
	public static String[] names = {"void","water","grass","forest",
		"hill","sand"};
	
	public static Terrian water = new Terrian(1);
	public static Terrian grass = new Terrian(2);
	public static Terrian forest = new Terrian(3);
	public static Terrian hill = new Terrian(4);
	public static Terrian sand = new Terrian(5);
	
//	public static String[] names = {"void","infantry","pikeman","cavalry",
//		"hill","sand"};
	
	public static byte getTerrainID(String s){
		for(byte i = 0;i<names.length;i++) if (names[i].equals(s)) return i;			
		return -1;
	}
	
	public boolean isValidToPut(){
		if (id==grass.id) return true;
		return false;
	}
		
	public byte id = 0;
	
	public Terrian(int id){
		this.id = (byte)id;
	}
	
	public String getTerrianName(){
		return names[id];
	}
	
	public String getImageString(){
		return names[id];
	}
	
	public void render(Screen screen, int xp, int yp, int size){
		screen.render(xp, yp, size, names[id]);
	}
}
