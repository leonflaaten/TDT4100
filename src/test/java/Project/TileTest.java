package Project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import minesweeper.fxui.Tile;

public class TileTest {
	   
	private Tile tile; 
	
	@BeforeEach
	public void setup() { 
		tile = new Tile(4,4);
	} 
	@Test
	public void TestConstructor() {
		assertEquals(tile.getX(),4);
		assertEquals(tile.getY(),4);
	}
	
	@Test
	public void testSetValidNumber() {
		tile.setNumber('5');
		assertTrue(tile.isNumber());
		
		tile.setNumber('?');
		assertFalse(tile.isNumber());
		
		
	}
	@Test
	public void TestSetMine() {	
		tile.setMine();
		assertTrue(tile.isMine());
		
		tile.setNumber('2');
		assertFalse(tile.isMine());
	}
	@Test
	public void TestSetAir() {
		tile.setAir();
		assertTrue(tile.isAir());
		
		tile.setMine();
		assertFalse(tile.isAir());
	}
	@Test
	public void TestSetDisabled() {
		assertFalse(tile.isDisable());
		
		tile.setDisable();
		
		assertTrue(tile.isDisable());
	}
	
	@Test
	public void TestSetType() {
		tile.setType('*');
		assertTrue(tile.isMine());
		
		tile.setType(' ');
		assertTrue(tile.isAir());
		
		tile.setType('4');
		assertTrue(tile.isNumber());
	}
	
	
	@Test
	public void TestGetType() {
		tile.setType('4');
		assertEquals(tile.getType(),'4');
	}
	
	@Test
	public void TestGetX() {
		tile = new Tile(4,4);
		assertEquals(tile.getX(),4);
	}
	@Test
	public void TestGetY() {
		tile = new Tile(4,4);
		assertEquals(tile.getY(),4);
//	}
//	@Test
//	@DisplayName("Test at det kastes unntak om man gir ugyldige Tile-verdier")
//	public void testSetInvalidType() {
//		assertThrows(
//				IllegalArgumentException.class,
//				() -> tile.setNumber('?'),
//				"IllegalArgumentException skal kastes når man setter Tile til en ugyldig verdi"
//				);
	}
}


