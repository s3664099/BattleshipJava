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
	
}
