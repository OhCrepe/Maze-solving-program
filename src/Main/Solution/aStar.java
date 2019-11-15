package Main.Solution;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

import Main.MazeSolver;
import Main.Node;

public class aStar implements ImageObserver {

	BufferedImage maze;
	boolean[][] solids;
	String outputPath;
	Node start = null, end = null;
	ArrayList<Node> nodes = new ArrayList<Node>();
	ArrayList<Node> open = new ArrayList<Node>();
	ArrayList<Node> closed = new ArrayList<Node>();
	int pathNodes = 0;
	
	public aStar(MazeSolver mazeSolver){
		this.maze = mazeSolver.getMaze();
		this.solids = mazeSolver.getSolids();
		this.outputPath = mazeSolver.getOutputPath();
	}
	
	public void solveMaze() {
		mapNodes();
		connectNodes();
		estimateDistance();
		findRoute();
	}
	
	//Selects which spots in the maze are a node spot
	void mapNodes(){
		
		int width = maze.getWidth(null), height = maze.getHeight(null);
		for (int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				
				//Makes sure the tile isn't a wall
				if(!solids[x][y]){
					//Decides if it should be considered an entrance/exit to the maze
					if(x == 0 || y == 0 || x == width-1 || y == height-1){
						if(start == null){
							start = new Node(x, y);
							nodes.add(start);
						}
						else if(end == null){
							end = new Node(x, y);
							nodes.add(end);
						}
					}else{
						//If the current tile is a continuation of a straight path, do nothing
						if(!((solids[x-1][y] && solids[x+1][y] && !solids[x][y-1] && !solids[x][y+1])
								|| (!solids[x-1][y] && !solids[x+1][y] && solids[x][y-1] && solids[x][y+1]))){
							nodes.add(new Node(x, y));
						}
					}
				}	
				
			}
		}
		
		System.out.println("Nodes: " + nodes.size());
		System.out.println("Start: " + start.toString());
		System.out.println("End: " + end.toString());
		
	}
	
	//Self explanatory
	void connectNodes(){
			
		for(Node node: nodes){
			int nX = node.getX(), nY = node.getY();
			if(nX > 0)if(!solids[nX - 1][nY])if(node.left == null)node.searchLeft(nodes, solids);
			if(nY > 0)if(!solids[nX][nY - 1])if(node.up == null)node.searchAbove(nodes, solids);
			if(nY < solids[0].length - 1)if(!solids[nX][nY + 1])if(node.down == null)node.searchBelow(nodes, solids);
			if(nX < solids.length - 1)if(!solids[nX + 1][nY])if(node.right == null)node.searchRight(nodes, solids);
		}
			
	}
	
	//Calculates the hValue for each node
	void estimateDistance(){
		
		for(Node node: nodes){
			node.calcH(end);
		}
		
	}
	
	public void findRoute(){
		
		Node current = start;
		open.add(current);
		
		while(!(current.getX() == end.getX() && current.getY() == end.getY())){
			
			//Above node
			if(current.up != null){
				Node tempNode = current.up;
				if(!contains(closed, tempNode)){
					if(contains(open, tempNode)){
						if(tempNode.getFValue() > current.getFValue() + Math.abs(current.getY() - tempNode.getY())){
							tempNode.setParent(current);
						}
					}else{
						tempNode.setParent(current);
						open.add(tempNode);
					}
				}
			}
			
			//Down node
			if(current.down != null){
				Node tempNode = current.down;
				if(!contains(closed, tempNode)){
					if(contains(open, tempNode)){
						if(tempNode.getFValue() > current.getFValue() + Math.abs(current.getY() - tempNode.getY())){
							tempNode.setParent(current);
						}
					}else{
						tempNode.setParent(current);
						open.add(tempNode);
					}
				}
			}
			
			//Left node
			if(current.left != null){
				Node tempNode = current.left;
				if(!contains(closed, tempNode)){
					if(contains(open, tempNode)){
						if(tempNode.getFValue() > current.getFValue() + Math.abs(current.getX() - tempNode.getX())){
							tempNode.setParent(current);
						}
					}else{
						tempNode.setParent(current);
						open.add(tempNode);
					}
				}
			}
			
			//Right node
			if(current.right != null){
				Node tempNode = current.right;
				if(!contains(closed, tempNode)){
					if(contains(open, tempNode)){
						if(tempNode.getFValue() > current.getFValue() + Math.abs(current.getX() - tempNode.getX())){
							tempNode.setParent(current);
						}
					}else{
						tempNode.setParent(current);
						open.add(tempNode);
					}
				}
			}
			
			open.remove(current);
			closed.add(current);
			Collections.sort(open);
			if(open.size() == 0)break;
			current = open.get(0);
			
		}
		
		if(current.equals(end)){
			System.out.println("Solution Found");
			drawSolution(current);
		}
		else System.out.println("Solution not found");

		
	}
	
	private void drawSolution(Node current) {
		BufferedImage output = new BufferedImage(maze.getWidth(), maze.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) output.getGraphics();
		g.drawImage(maze, 0, 0, maze.getWidth(), maze.getHeight(), null);
		g.setColor(Color.RED);
		pathNodes = 1;
		while(current.parent != null){
			g.drawLine(current.getX(), current.getY(), current.parent.getX(), current.parent.getY());
			current = current.parent;
			pathNodes++;
		}
		g.dispose();
		try {
			ImageIO.write(output, "png", new File(outputPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		printStats();
	}

	boolean contains(ArrayList<Node> nodeList, Node node){
		for(Node n: nodeList){
			if(node.equals(n))return true;
		}
		return false;
	}
	
	Node getNode(ArrayList<Node> nodeList, Node node){
		for(Node n: nodeList){
			if(node.equals(n))return n;
		}
		return null;
	}
	
	void printStats(){
		System.out.println("Nodes Considered: " + (closed.size() + open.size()));
		System.out.println("Nodes in Solution: " + pathNodes);
	}

	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3,
			int arg4, int arg5) {
		// TODO Auto-generated method stub
		return false;
	}

}
