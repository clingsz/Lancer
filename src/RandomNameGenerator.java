import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Random;
import java.util.Scanner;


public class RandomNameGenerator {
	public static String[] fileNames = {"first-names","names","places"};
	public static String firstNames[];
	public static String lastNames[];
	public static String placeNames[];
	public static void init(){
		firstNames = getNames(fileNames[0]);
		lastNames = getNames(fileNames[1]);
		placeNames = getNames(fileNames[2]);
	}
	
	public static String[] getNames(String fileName){
		File f1 = new File("res/name/" + fileName + ".txt");
		String names[] = null;
		try {
			int L = countLines(f1);
			names = new String[L+1];
			Scanner s = new Scanner(f1);
			int cnt = 0;
			while(s.hasNext()){
//				System.out.println(cnt + "," + L);
				names[cnt++] = s.nextLine();
			}
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return names;
	}
	
	public static int countLines(File f) throws IOException{
		LineNumberReader  lnr = new LineNumberReader(new FileReader(f));
		lnr.skip(Long.MAX_VALUE);
		int lines = lnr.getLineNumber() + 1;
		lnr.close();
		return (lines); 
	}
	
	public static String getRandName(int id){
		switch(id){
		case 0: return(firstNames[new Random().nextInt(firstNames.length)]);
		case 1: return(lastNames[new Random().nextInt(lastNames.length)]);
		case 2: return(placeNames[new Random().nextInt(placeNames.length)]);
		}
		return null;
	}
	
	public static String getFirstName(){
		return getRandName(0);
	}
	
	public static String getLastName(){
		return getRandName(1);
	}
	
	public static String getPlaceName(){
		return getRandName(2);
	}
	
	public static void main(String args[]){
		RandomNameGenerator.init();
		int cnt = 0;
		while(cnt++<10){
			for(int i = 0 ; i < 3;i ++){
				System.out.println(fileNames[i] + " : " +RandomNameGenerator.getRandName(i));
			}
		}
	}
	
}
