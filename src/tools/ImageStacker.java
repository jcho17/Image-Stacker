package tools;
/*
 * Justine Cho (G Period)
 * Kim Li (F Period)
 * USE THE PPM READER ON THE NIFTY ASSIGNMENT PAGE TO VIEW THE NEW PPM.
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JApplet;

public class ImageStacker extends JApplet {
	private  int cols;
	private  int rows;
	private ArrayList<Noisy> noisies;
	
	public void init(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the name of image to process (cone_nebula or n44f for now, but "
				+ "\nmore images can be imported to the Destination folder of the src)"); 
		String filename = scanner.next();
		System.out.println("Enter the path to save the new ppm.\nRemember to include the / at the end (ex. /Users/username/Desktop/)");
		String path = scanner.next();
		scanner.close();
		try {
			readFile(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			createImage(filename, path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	public class Noisy{
		
		private int[][] red;
		private int[][] green;
		private int[][] blue;

		public Noisy(int r, int c){
			red = new int[c][r];
			green = new int[c][r];
			blue = new int [c][r];
		}
		public void addRed(int redVal, int r, int c){
			red[c][r]=redVal;
		}
		public void addGreen(int greenVal, int r, int c){
			green[c][r]=greenVal;
		}
		public void addBlue(int blueVal, int r, int c){
			blue[c][r]=blueVal;
		}
		public int getRed(int r, int c){
			return red[c][r];
		}
		public int getGreen(int r, int c){
			return green[c][r];
		}
		public int getBlue(int r, int c){
			return blue[c][r];
		}

	}
	public void readFile (String name) throws IOException, FileNotFoundException{
		noisies= new ArrayList<Noisy>();
		for (int i=1; i<=9; i++){
			Scanner sc = new Scanner (new BufferedReader(new FileReader ("Destination/"+name+"/"+
					name+"_00"+i+".ppm")));
			sc.nextLine();
			cols = Integer.parseInt(sc.next());
			rows= Integer.parseInt(sc.next());
			Noisy n = new Noisy(rows, cols);
			sc.nextLine();
			sc.nextLine();
			for (int k = 0; k<rows; k++){
				for (int j=0; j<cols;j++){
					n.addRed(sc.nextInt(), k, j);
					n.addGreen(sc.nextInt(), k, j);
					n.addBlue(sc.nextInt(),k,j);
				}
			}
			noisies.add(n);
			sc.close();	
		}
		Scanner sc = new Scanner (new BufferedReader(new FileReader ("Destination/"+name+"/"+
				name+"_010"+".ppm")));
		sc.nextLine();
		cols = Integer.parseInt(sc.next());
		rows= Integer.parseInt(sc.next());
		Noisy n = new Noisy(rows, cols);
		sc.nextLine();
		sc.nextLine();
		for (int k = 0; k<rows; k++){
			for (int j=0; j<cols;j++){
				n.addRed(sc.nextInt(), k, j);
				n.addGreen(sc.nextInt(), k, j);
				n.addBlue(sc.nextInt(),k,j);
			}
		}
		noisies.add(n);
		sc.close();
		
	}
	public void createImage (String name, String path) throws IOException{
		File f= new File (path+name+"_new"+".ppm");
		if (!f.exists()){
			f.createNewFile();
		}
		BufferedWriter wr = new BufferedWriter(new FileWriter(f.getAbsoluteFile()));
		wr.write("P3");
		wr.newLine();
		wr.write(cols+" "+rows+ " 255");
		wr.newLine();
		for(int i=0; i<rows; i++){
			for (int j=0;j<cols;j++){
				int redsum = 0;
				int greensum=0;
				int bluesum=0;
				for (int k =0; k<noisies.size();k++){
					redsum+=noisies.get(k).getRed(i,j);
					greensum+=noisies.get(k).getGreen(i,j);
					bluesum+=noisies.get(k).getBlue(i,j);
				}
				int redVal= redsum/10;
				int greenVal=greensum/10;
				int blueVal=bluesum/10;
				wr.write(""+redVal+" "+greenVal + " "+blueVal);
				wr.newLine();
			}
		}
		wr.close();
		
		System.out.println("Completed.New ppm saved in:" + path+ " as "+name+"_new");
		
		
	}

}
