package Main.Input;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import Main.MazeSolver;

public class FileRetriever {

	MazeSolver solver;
	
	public FileRetriever(MazeSolver s){
		this.solver = s;
	}
	
	public BufferedImage getMaze(){
		
		String filePath = JOptionPane.showInputDialog(null, "Please give the filepath of the maze you wish to have solved.");
		BufferedImage maze;
		try{
			maze = ImageIO.read(new File(filePath));
			solver.defineOutput(filePath);
			System.out.println("Loaded maze: " + filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "There was an error retrieving file with path\n" + filePath + "\nPlease try again with a different file path.");
			maze = getMaze();
		}
		return maze;
	}
	
}
