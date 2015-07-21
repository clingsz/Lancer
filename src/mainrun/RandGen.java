package mainrun;

public class RandGen {
	
	public static int getBinomial(int n, double p) {
		  int x = 0;
		  for(int i = 0; i < n; i++) {
		    if(Math.random() < p)
		      x++;
		  }
		  return x;
	}
	public static int getRandomNumber(int a, int b){
	    return (int)(a+Math.round(Math.random()*(b-a)));
	}
	public static double getRandomDouble(double b){
		return Math.random()*b;
	}
	public static void main(String args[]){
		for (int i = 1;i<50;i++)
		System.out.print(getRandomNumber(4,10));
	}
}
