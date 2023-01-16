import java.util.HashSet;
import java.util.Set;

public class Ship {
	private int length = 0;
	private String letter = "";
	private String name = "";
	private boolean sunk = false;
	private Coordinate coords;
	private Set <Coordinate> hitSections;
	private Set <Coordinate> coordinates = new HashSet <Coordinate>();
	
	//Constructs the ship taking a length, a letter for the map, and a name
	public Ship(int length, String letter, String name) {
		this.length = length;
		this.letter = letter;
		this.name = name;
	}
	
	public int getLength() {
		return this.length;
	}
	
	public String getLetter() {
		return this.letter;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean getSunk() {
		return this.sunk;
	}
	
	//Adds the coordinates that the ship will occupy on the map
	public void addCoordinates(int xCoord, int yCoord,int incX,int incY,int length) {
		
		for (int x=0;x<length;x++) {
			Coordinate coord = new Coordinate(xCoord,yCoord);
			this.coordinates.add(coord);
			xCoord += incX;
			yCoord += incY;
		}
	}
	
	//Adds a coordinate where the ship has been hit
	public void addHitSections(int xCoord,int yCoord) {
		Coordinate coord = new Coordinate(xCoord,yCoord);
		this.hitSections.add(coord);
	}
	
	public Set<Coordinate> getHitSections() {
		return hitSections;
	}
	
	//Checks if the coordinates are where the ship is located
	public boolean checkCoordinates(int xCoord,int yCoord) {
		
		boolean sunk = false;
		boolean found = false;
		Coordinate foundCoord = null;
		
		//Goes through the coordinates
		for (Coordinate coord:coordinates) {
			
			//If they are equal to the coordinates, the  makes as found
			if (coord.getX() == xCoord && coord.getY() == yCoord) {
				foundCoord = coord;
				found = true;
			}
			
			//If found, announces that the ship has been hit.
			//Also removes the coordinates from the set
			if (found) {
				this.coordinates.remove(foundCoord);
				System.out.format("You hit the %s%n",this.name);
			}
			
			//Checks if size of the coordinates are 0.
			//If so marks the ship as sunk
			if (this.coordinates.size() == 0) {
				sunk = true;
			}
		}
		
		return sunk;	
	}
	
	//Sets the ship as sunk and announces
	public void sunkShip() {
		this.sunk = true;
		System.out.format("You sunk the %s", this.name);
	}
} 
