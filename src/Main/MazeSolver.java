package Main;

import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import Main.Input.FileRetriever;
import Main.Solution.aStar;

public class MazeSolver {

	BufferedImage maze;
	boolean[][] solidMap;
	String outputPath;
	long startTime;
	
	public static void main(String[] args){
		new MazeSolver();
	}
	
	public MazeSolver(){
		loadMaze();
		mapMazeWalls();
		validateMaze();
		new aStar(this).solveMaze();
		finish();
	}
	
	void loadMaze(){		
		maze = new FileRetriever(this).getMaze();
		startTime = System.nanoTime();
		if(maze == null){
			shutdown("There was an error retrieving the requested image");
		}
	}
	
	void mapMazeWalls(){
		MazeMapper mapper = new MazeMapper();
		solidMap = mapper.mapWalls(maze);
		//mapper.printWalls(solidMap);
	}
	
	void validateMaze(){
		boolean valid = new MazeValidator().validMaze(solidMap);
		if(!valid)shutdown("The maze given was not valid");
		else System.out.println("Maze is valid");
	}

	void shutdown(String msg){
		JOptionPane.showMessageDialog(null, msg + "\nThe program will now terminate");
		System.exit(0);
	}
	
	public void defineOutput(String filePath){
		int pos = filePath.length()-1;
		while(filePath.charAt(pos) != '.'){
			pos--;
		}
		outputPath = filePath.substring(0, pos) + "_solved.png";
	}
	
	void finish(){
		printTime();
		System.out.println("Solution saved: " + outputPath);
		JOptionPane.showMessageDialog(null, "Solution found and saved as\n" + outputPath);
		System.exit(0);
	}
	
	public BufferedImage getMaze(){
		return maze;
	}
	
	public boolean[][] getSolids(){
		return solidMap;
	}
	
	public String getOutputPath(){
		return outputPath;
	}
	
	public void printTime(){
		System.out.println("Time to completion: " + (float)((System.nanoTime() - startTime)/1000000000f) + "s");
	}
	
}
