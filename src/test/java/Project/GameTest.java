package Project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import minesweeper.fxui.Game;
import minesweeper.fxui.Tile;

public class GameTest {
	
	private Game game;   
	 
	@BeforeEach 
	public void setup() {
		game = new Game(10,10,30);
	}
	@Test 
	public void TestConstructor() {
		assertEquals(game.getWidth(), 10);
		assertEquals(game.getHeight(),10);
	
		List<Character> tall = Arrays.asList('1','2','3','4','5','6','7','8');
		for (int i = 0; i < game.getHeight(); i++) {
			for (int j = 0; j < game.getWidth(); j++) {
				assertTrue(game.getTile(i, j).getType()==(' ')||
				tall.contains(game.getTile(i, j).getType())||
				game.getTile(i, j).getType()==('*'));				
			}
		}
		assertThrows(IllegalArgumentException.class,
				()-> new Game(0,0,4),
				"Cant have a board that is smaller than a tile or less than one bomb");
		
		// Tester å lage et game med tomt brett
		Game game2 = new Game(10,10);
		assertEquals(game2.getWidth(), 10);
		assertEquals(game2.getHeight(),10);
		for (int i = 0; i < game2.getHeight(); i++) {
			for (int j = 0; j < game2.getWidth(); j++) {
				assertTrue(game2.getTile(i, j).getType()==(' '));
			}
		}
	}
	@Test
	public void testNumberOfBombs() {
		assertEquals(30,game.getNumberOfBombs());
		
	}
	@Test
	public void testSetBombs() {
		game.setBombs(30);
		assertEquals(game.getNumberOfBombs(), 60);
		

		assertThrows(IllegalArgumentException.class, 
				()->game.setBombs(-70),
				"Cant have a negative amount of bombs");
		
		assertThrows(IllegalArgumentException.class,
				()->game.setBombs(150),
				"Cant have more bombs than tiles on the board");
		
		assertThrows(IllegalArgumentException.class,
				()->game.setBombs('g'),
				"Input must be an Int");
	}
	
	
	@DisplayName("Test at BombsAround teller riktig")
	@Test
	public void TestBombsAround() {
		game = new Game(2,2,1);
		int count = 0;
		for (int i = 0; i < game.getHeight(); i++) {
			for (int j = 0; j < game.getWidth(); j++) {
				if (game.getTile(i, j).isNumber()) {
					count++;
				}
			}
		}
		assertEquals(count, 3);
		
		Game game2 = new Game(10,10);
		for (int i = 0; i < game2.getHeight(); i++) {
			for (int j = 1; j < game2.getWidth()-1; j++) {
					game2.getTile(i, 0).setType('*');
				
					game2.getTile(i,9).setType('*');
				
					game2.getTile(0, j).setType('*');
			
					game2.getTile(9,j).setType('*');
				}
		}
		game2.boardBombs(game2.getBoard());			
		int count2 = 0;
		for (int i = 0; i < game2.getHeight(); i++) {
			for (int j = 0; j < game2.getWidth(); j++) {
				if (game2.getTile(i, j).isNumber()) {
					count2++;
		}
			}
		}
		assertEquals(28, count2);     
	}
	@Test
	@DisplayName("Test at spillet avsluttes når setGameOver() kalles")
	public void testGameOver() {
		game.setGameOver();
		assertTrue(game.isGameOver());
	}
	@Test
	public void testGameWon() {
		game.setGameWon();
		assertTrue(game.isGameWon());
	}
	@Test
	@DisplayName("Test at det kastes Exception når Tile settes til ugyldig verdi")
	public void TileOutOfBounds() {
		assertThrows(IllegalArgumentException.class, 
				()->game.getTile(game.getHeight()+1, game.getWidth()+1),
				"Out of bounds");
		
		assertThrows(IllegalArgumentException.class, 
				()->game.getTile(-1, -1),
				"Out of bounds");
	}
	@Test
	public void testIsTile() {
		assertTrue(game.isTile(5, 5));
		assertFalse(game.isTile(-1, -1));
		assertFalse(game.isTile(-100, -100));
		assertFalse(game.isTile(100, 100));
	}
	@Test
	public void TestReset() {
		List<Tile> gameList = game.gameToList();
		
		Game game2 = game.reset(game, 10, 10,30);
		List<Tile> game2List = game2.gameToList();
		
		assertFalse(gameList.equals(game2List));
	}
}

