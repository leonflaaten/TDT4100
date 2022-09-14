package minesweeper.fxui;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner; 

public class SaveHandler implements SaveHandlerInterface {

	public final static String SAVE_FOLDER = "src/saves";
	

	public void save(Game game) throws FileNotFoundException {
		try (PrintWriter writer = new PrintWriter("minesweeper.txt")) {

			writer.println(game.getWidth());
			writer.println(game.getHeight());
			writer.println(game.isGameOver());
			writer.println(game.isGameWon());
		
			for (int y = 0; y < game.getHeight(); y++) {
				for (int x = 0; x < game.getWidth(); x++) {
					writer.print(game.getTile(x, y).getType());
				}
			}
			writer.println();
			
			for (int y = 0; y < game.getHeight(); y++) {
				for (int x = 0; x < game.getWidth(); x++) {
			writer.println(game.getTile(x, y).isDisable());
				}
			}
		} 
	}
	
	public Game load(Game game) throws FileNotFoundException {
		try(Scanner scanner = new Scanner(new FileReader("minesweeper.txt"))) {
				String line = scanner.nextLine();
				int width = Integer.parseInt(line);
				String line2 = scanner.nextLine();
				int height = Integer.parseInt(line2);
				String line3 = scanner.nextLine();
				boolean gameOver = Boolean.parseBoolean(line3);
				if (gameOver) {
				game.setGameOver();
				}
				
				String line4 = scanner.nextLine();
				boolean gameWon = Boolean.parseBoolean(line4);
				if(gameWon) {
					game.setGameWon();
				}
				
				String board = scanner.nextLine();
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						char symbol = board.charAt((y * width) + x);
						game.getTile(x, y).setType(symbol);
					}
				}  
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						String val = scanner.nextLine();
						boolean bol = Boolean.parseBoolean(val);

						if(bol) {
							game.getTile(x, y).setDisable(); 
						}		
					}
				}	
				}
				return game;
			}
 
	public static String getFilePath(String filename) {
		return "minesweeper.txt";
	}
	
	public static void main(String[] args) {
		
	  
		

	}

}


