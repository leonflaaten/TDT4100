package minesweeper.fxui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Tile {

	private int height;
	private int width;
	private Tile [][] board;
	private boolean isGameOver;
	private boolean isGameWon;
	
	public Game(int width, int height, int numberOfBombs) {  
		if (width<1 || height < 1 || numberOfBombs<1) {
			throw new IllegalArgumentException("Cant have a board that is smaller than a tile or less than one bomb");
		}
		
		boardConstraints(width,height);
		this.board = new Tile [height][width];
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				board[y][x] = new Tile (x,y);
			}
		}
		setBombs(numberOfBombs);
		boardBombs(getBoard());
	}
	
	
	public Game(int width, int height) {
		if (width<1 || height < 1) {
			throw new IllegalArgumentException("Cant have a board smaller than 1*1 tiles");
		}
		boardConstraints(width,height);
		
		this.board = new Tile [height][width];
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				board[y][x] = new Tile (x,y);
			}			
		}
		boardBombs(getBoard());
	}
	public void boardConstraints(int width, int height) {
		if (width != height) {
			if (width < height) {
				this.height = width;
				this.width = width;
			}
			else { 
				this.width = height;
				this.height = height;
			}
			}
		else {
			this.height = height;
			this.width = width;
		}
	}
	
	
	public Tile [][] getBoard(){
		return board;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width; 
	}
	
    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isGameWon() {
    	return isGameWon;
    }
    
    public void setGameOver() {
    	for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
			getTile(x,y).setDisable();	
			}
			}
    	isGameOver = true;
    }
    
    public void setGameWon() {
    	isGameWon = true;
    }
	
	public boolean isTile(int x, int y) {
		return (x >= 0 && y >= 0 && x < getWidth() && y < getHeight());
	}
	
	public List<Tile> gameToList() {
		List<Tile> gameList = new ArrayList<Tile>();
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				gameList.add(getTile(i,j));
			}
		}
		return gameList;
	}
	
	public int getNumberOfBombs() {
		int count = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if(getTile(i,j).getType() == '*') {
					count++;
				}
			}
		}
		return count;
	}
	
	public Tile getTile(int x, int y) {
		if(!isTile(x,y)) {
			throw new IllegalArgumentException ("Out of bounds ");
		}
		return board [y][x];
	}
	
 	@Override
 	public String toString() {
 		String boardString = "";
 		for (int x = 0; x < getWidth(); x++) {
 	 		for (int y = 0; y < getHeight(); y++) {
 				boardString += getTile(x,y);
 				}
 			boardString += "\n";
 			} 
 			
 			return boardString;	
}
   
 	public void setBombs(int n) {
 		
 		if (n<0) {
 			throw new IllegalArgumentException("Cant insert a negative amount of bombs");
 		}
 		if (n > getHeight()*getWidth()) {
 			throw new IllegalArgumentException("Cant have more bombs than tiles on the board");
 		}
 		if(n!= (int)n) {
 			throw new IllegalArgumentException("input must be an int");
 		}
 		
 		Random r = new Random();

		int count = 0;
		int target = n;
		while (count < target) {
			int x = r.nextInt((width - 0) + 1) + 0;  //((max-min)+1)+min;
		    int y = r.nextInt((height - 0) + 1) + 0;
		    if(isTile(x,y)&& !getTile(x,y).isMine()) {
		        getTile(x, y).setMine();
		        count++;
		    }
		}
 	}
	public void BombsAround(int x , int y) {
 		int countBombs = 0;
 		try {
 			if (getTile(x-1,y).isMine()) {
 			countBombs++;
 			}
	}
 			catch (Exception e) {
 				
 			}
 		
 		try {
 			if(getTile(x+1, y).isMine()) {
 				countBombs++;
 		}
 		}
 			catch (Exception e) {
 		}

 		try { 
 				if(getTile(x, y+1).isMine()) {
 				countBombs++;
 		}
 		}
 		catch (Exception e) {	
 		}
 		try {
 			if(getTile(x, y-1).isMine()) {
 				countBombs++;
 			}
 		}
 		catch (Exception e) {
 			
 		}
 		
 		try {
 			if(getTile(x-1, y+1).isMine()) {
 				countBombs++;
 			}
 		}
 		catch (Exception e) {
 			
 		}
 		try {
 			if(getTile(x-1, y-1).isMine()) {	
 				countBombs++;
 			}
 		}
 		catch (Exception e) {
 			
 		}
 		try {
 			if(getTile(x+1,y+1).isMine()) {
 				countBombs++;
 			}
 		}
 		catch (Exception e) {
 			
 		}
 		try {
 			if(getTile(x+1, y-1).isMine()) {	
 				countBombs++;
 			}
 		}
 		catch (Exception e) {
 			
 		}
 		if (countBombs>0 && !getTile(x,y).isMine()) {
 			
 		char c=(char)(countBombs+'0');
 		getTile(x,y).setNumber(c);
 		}
	}
	public Tile [][] boardBombs(Tile [][] board) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				BombsAround(i, j);
			}
		}
		return board;
 	}

	public Game reset(Game game, int width, int height, int numberOfBombs){

		Game game2 = new Game(width,height,numberOfBombs);
		Tile t = new Tile();
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				t = game2.getTile(i,j);
				if(t.getType() == '*') {
					getTile(i,j).setMine();
				}
				else {
					getTile(i,j).setAir();
					}
			}
		}
		boardBombs(getBoard());
		return game2;
	}
}