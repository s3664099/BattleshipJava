import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.junit.Test;

public class battleShipTest {

	Coordinate coord = new Coordinate(3,4);
	Ship battleShip = new Ship(4,"A","Battleship");
		
	@Test
	public void getXTest() {
		assertEquals(coord.getX(),3);
	}
	
	@Test
	public void getYTest() {
		assertEquals(coord.getY(),4);
	}
	
	@Test
	public void getLengthTest() {
		assertEquals(battleShip.getLength(),4);
	}
	
	@Test
	public void getLetterTest() {
		assertEquals(battleShip.getLetter(),"A");
	}
	
	@Test
	public void getNameTest() {
		assertEquals(battleShip.getName(),"Battleship");
	}
	
	@Test
	public void getSunkTest() {
		assertEquals(battleShip.getSunk(),false);
	}
	
	@Test 
	public void getCoordTest() {
		battleShip.addCoordinates(3,4,0,1,4);
		
		for (int x = 0;x<4;x++) {

			if (x<3) {
				assertEquals(battleShip.checkCoordinates(3, 4+x),false);
			} else {
				assertEquals(battleShip.checkCoordinates(3, 4+x),true);
			}
		}
	}
	
	@Test
	public void buildBoardTest() {
		Board board = new Board("Fred");
		String[][] grid = board.getGrid();
		
		assertEquals(grid.length,10);
		assertEquals(grid[0].length,10);
		assertEquals(board.getName(),"Fred");
		
		for (int x=0;x<10;x++) {
			String gridRow = "";
			for (int y=0;y<10;y++) {
				gridRow+=grid[x][y];
			}
			System.out.println(gridRow);
			assertEquals(gridRow,"..........");
		}
	}
	
	@Test
	public void getRandomNumber() {
		Board board = new Board("Fred");
		for (int x=0;x<10;x++) {
			System.out.println(board.getRandomNumber(0, 1));
		}
		
		assertEquals(true,true);
	}
	
	@Test
	public void addShipTest() {
		
		Board board = new Board("Fred");
		board.addShips();
		String[][] grid = board.getGrid();

		for (int x=0;x<10;x++) {
			String gridRow = "";
			for (int y=0;y<10;y++) {
				gridRow+=grid[x][y];
			}
			System.out.println(x+gridRow);
		}
		System.out.println();
		assertEquals(true,true);
	}
	
	@Test
	public void testSinkShip() {

		System.out.println();
		System.out.println("Test Sink Ship");
		Board board = new Board("Fred");
		board.addShips();
		System.out.println();
		String[][] grid = board.getGrid();
		
		for (int x=0;x<10;x++) {
			String gridRow = "";
			for (int y=0;y<10;y++) {
				gridRow+=grid[x][y];
			}
			System.out.println(x+gridRow);
		}
		
		board.testSinkShip();
		
		String[][] grid2 = board.getGrid();
		
		System.out.println();
		for (int x=0;x<10;x++) {
			String gridRow = "";
			for (int y=0;y<10;y++) {
				gridRow+=grid2[x][y];
			}
			System.out.println(x+gridRow);
		}
	}
	
}
