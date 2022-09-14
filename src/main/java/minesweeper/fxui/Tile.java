package minesweeper.fxui;

import java.util.Arrays;
import java.util.List;

public class Tile {
	private char type = ' ';
    private int x;
    private int y;
    private boolean disable;
	List<Character> tall = Arrays.asList('1','2','3','4','5','6','7','8');

	public Tile() {
    	
    }
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setAir() {
    	type = ' ';
        disable = false; 
    }

    public void setMine() {
        type = '*';
        disable = false;
    }
    public void setNumber(char x) {
        type = x;
        disable = false;
    }
    
    public void setType(char x) {
    	if(x == ' ') {
    		setAir();
    	}
    	if (x == '*') { 
    		setMine();
    	}
    	else {
    		setNumber(x);
    	}
    }
    
    public void setDisable() {
    	disable = true;
    }
    
    public boolean isAir() {
        return type == ' ';
    }

    public boolean isMine() {
        return type == '*';
    }

    public boolean isNumber() {
		if (tall.contains(type)) {
			return true;
		}
		return false;
    }

    public char getType() {
	 return type;
 }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public boolean isDisable() {
    	return disable;
    }
    
    @Override
    public String toString() {
        return Character.toString(type);
    }
}



