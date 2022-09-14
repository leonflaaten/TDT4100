package Project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import minesweeper.fxui.Game;
import minesweeper.fxui.SaveHandler;


public class SaveHandlerTest {      
	 
	private Game game; 
	private SaveHandler saveHandler = new SaveHandler(); 

	@BeforeEach
	private void setUp() {
		 game = new Game(10,10,30);
	}
	@Test
	public void testLoad() {
		Game savedNewGame;
		try {
			savedNewGame = saveHandler.load(game);
			
		} catch (FileNotFoundException e) {
			fail("Could not load saved file");
			return;	
		}
		assertEquals(game.toString(), savedNewGame.toString());
		assertEquals(game.getHeight(), savedNewGame.getWidth());
		assertEquals(game.getWidth(), savedNewGame.getWidth());
		assertEquals(game.getNumberOfBombs(), savedNewGame.getNumberOfBombs());
		for (int i = 0; i < game.getHeight(); i++) {
			for (int j = 0; j < game.getWidth(); j++) {
				assertEquals(game.getTile(i, j).isDisable(), savedNewGame.getTile(i, j).isDisable());
				assertEquals(game.getTile(i,j).getType(), savedNewGame.getTile(i, j).getType());
			}	
		}
	}

	public void createTestSave() throws FileNotFoundException {

		saveHandler.save(game);
		try (PrintWriter writer = new PrintWriter("test.txt")) {

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
	
	
	@Test
	public void testSave() throws IOException {
		
		createTestSave();
		try (BufferedReader reader1 = new BufferedReader(new FileReader("minesweeper.txt"))) {
			try (BufferedReader reader2 = new BufferedReader(new FileReader("test.txt"))) {
				String line1 = reader1.readLine();
				String line2 = reader2.readLine();
				boolean areEqual = true;
				while (line1 != null || line2 != null) {
					if (line1 == null || line2 == null) {
						areEqual = false;
						break;
					}
					else if(!line1.equalsIgnoreCase(line2)) {
						areEqual = false;
						break;
					}
					line1 = reader1.readLine();
					line2 = reader2.readLine();
				}
				assertTrue(areEqual);
			}
		}	
	}
	@Test
	public void testSaveFileExists() {
		File f = new File(SaveHandler.getFilePath(" "));
		
		assertTrue(f.exists());
		}
	}



	
	
	