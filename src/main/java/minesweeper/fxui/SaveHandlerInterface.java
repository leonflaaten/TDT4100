package minesweeper.fxui;

import java.io.FileNotFoundException;

public interface SaveHandlerInterface {
	
//	public void save(String filename, Game game) throws FileNotFoundException;
	public void save(Game game) throws FileNotFoundException;
	
//	public Game load(String filename) throws FileNotFoundException;
	public Game load(Game game) throws FileNotFoundException; 
	

}
