package Main;

import java.util.ArrayList;

public class Node implements Comparable<Node>{

	int x, y;
	public Node left, right, up, down, parent;
	int fValue, gValue, hValue;
	
	public Node(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public String toString(){
		return x + ", " + y;
	}
	
	public boolean atPos(int x, int y){
		if(this.x == x && this.y == y)return true;
		else return false;
	}
	
	public void searchLeft(ArrayList<Node> nodes, boolean[][] solids){
		
		int offSet = 0;
		while(true){
			offSet++;
			for(Node n: nodes){
				if(n.atPos(x, y))continue;
				if(n.getY() == y && n.getX() < x){
					if(n.atPos(x-offSet, y)){
						n.right = this;
						this.left = n;
						//System.out.println("Node " + n.getX() + ", " + n.getY() + " is to the left of node " + x + ", " + y);
						return;
					}
				}
			}
			
		}		
	}
	
	public void searchAbove(ArrayList<Node> nodes, boolean[][] solids){
		
		int offSet = 0;
		while(true){
			offSet++;
			for(Node n: nodes){
				if(n.atPos(x, y))continue;
				if(n.getX() == x && n.getY() < y){
					if(n.atPos(x, y-offSet)){
						n.down = this;
						this.up = n;
						//System.out.println("Node " + n.getX() + ", " + n.getY() + " is above of node " + x + ", " + y);
						return;
					}
				}
			}
			
		}		
	}
	
	public void searchBelow(ArrayList<Node> nodes, boolean[][] solids){
		
		int offSet = 0;
		while(true){
			offSet++;
			for(Node n: nodes){
				if(n.atPos(x, y))continue;
				if(n.getX() == x && n.getY() > y){
					if(n.atPos(x, y+offSet)){
						n.up = this;
						this.down = n;
						//System.out.println("Node " + n.getX() + ", " + n.getY() + " is below node " + x + ", " + y);
						return;
					}
				}
			}
			
		}		
	}
	
	public void searchRight(ArrayList<Node> nodes, boolean[][] solids){
		
		int offSet = 0;
		while(true){
			offSet++;
			for(Node n: nodes){
				if(n.atPos(x, y))continue;
				if(n.getY() == y && n.getX() > x){
					if(n.atPos(x+offSet, y)){
						n.left = this;
						this.right = n;
						//System.out.println("Node " + n.getX() + ", " + n.getY() + " is to the right of node " + x + ", " + y);
						return;
					}
				}
			}
			
		}		
	}
	
	public int getGValue(){
		return gValue;
	}
	
	//Estimates how far from the end the node is
	public void calcH(Node node){
		hValue = Math.abs(node.getX() - this.x) + Math.abs(node.getY() - this.y);
		calcF();
	}
	
	//Calculates how much it costs to reach this node
	public void calcG(Node node){
		gValue = node.getGValue() + Math.abs(this.x - node.getX()) + Math.abs(this.y - node.getY());
		calcF();
	}
	
	public void calcF(){
		this.fValue = gValue + hValue;
	}
	
	public boolean equals(Node node){
		if(this.x == node.getX() && this.y == node.getY())return true;
		return false;
	}
	
	public int compareTo(Node n) {
		return fValue - n.fValue;
	}

	public int getFValue() {
		return fValue;
	}

	public void setParent(Node n) {
		this.parent = n;
		calcG(parent);
	}	
	
}
