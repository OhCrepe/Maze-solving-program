package Main;

public class MazeValidator {

	public boolean validMaze(boolean[][] walls){
		int exits = 0;
		for(int x = 0; x < walls.length; x++){
			if(!walls[x][0])exits++;
			if(!walls[x][walls[0].length-1])exits++;
		}
		for(int y = 1; y < walls[0].length-1; y++){
			if(!walls[0][y])exits++;
			if(!walls[walls.length-1][y])exits++;
		}
		if(exits == 2)return true;
		return false;
	}
	
}
