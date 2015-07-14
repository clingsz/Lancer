package screen;

public class ColorMaker {

	public static int get(int a, int b, int c, int d) {
		return (get(d) << 24) + (get(c) << 16) + (get(b) << 8) + (get(a));
	}

	public static int get(int d) {
		if (d < 0) return 255;
		int r = d / 100 % 10;
		int g = d / 10 % 10;
		int b = d % 10;
		return r * 36 + g * 6 + b;
	}
	
	
	public static int getPlayerColor(int id,int level){
		int color = 0;
		switch(id){
		case 0: color = level*111; break;
		case 1: color = level*100; break;
		case 2: color = level*10; break;
		case 3: color = level*1; break;
		}
		return get(color);
	}

}