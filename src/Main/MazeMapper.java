package Main;

import java.awt.image.BufferedImage;

public class MazeMapper {

	public boolean[][] mapWalls(BufferedImage maze){
		boolean[][] walls = new boolean[maze.getWidth(null)][maze.getHeight(null)];
		System.out.println("Size: " + maze.getWidth(null) + "x" + maze.getHeight(null));
		for(int x = 0; x < maze.getWidth(null); x++){
			for(int y = 0; y < maze.getHeight(null); y++){
				int RGB = maze.getRGB(x, y);
				if(RGB == -1){
					walls[x][y] = false;
				}else walls[x][y] = true;
			}
		}
		return walls;
	}
	
	public void printWalls(boolean[][] walls){
		for(int y = 0; y < walls[0].length; y++){
			System.out.println("");
			for(int x = 0; x < walls.length; x++){
				if(walls[x][y])System.out.print("#");
				else System.out.print(" ");
			}			
		}
	}
	
}
