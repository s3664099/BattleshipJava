import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {
	
	private int rows = 10;
	private String[][] grid = new String[rows][rows];
	private String[][] spotsHit = new String[rows][rows];	
	private String objectName;
	private int gridSize = 9;
	private Set<Coordinate> potentialShots = new HashSet<Coordinate>();
	private Set<Coordinate> usedSpots = new HashSet<Coordinate>();
	private Set<Ship> ships = new HashSet<Ship>();
	private int hitShip;
	private Coordinate hit = new Coordinate(0,0);
	private Coordinate originalHit = new Coordinate(0,0);
	private int movement = 0;
	
	public Board(String objectName) {
		this.objectName = objectName;
				
		//Builds the boards, and creates a set of potential shots
		for (int i=0;i<rows;i++) {
			for (int j=0;j<rows;j++) {
				this.grid[i][j] = ".";
				this.spotsHit[i][j] = ".";
				Coordinate potShot = new Coordinate(i,j);
				this.potentialShots.add(potShot);
			}
		}
		
		//Generates the ship objects for the board
		this.ships.add(new Ship(4,"e","Aircraft Carrier"));
		this.ships.add(new Ship(4,"d","Battleship"));
		this.ships.add(new Ship(3,"b","Submarine"));
		this.ships.add(new Ship(3,"c","cruiser"));
		this.ships.add(new Ship(2,"a","destroyer"));
	}
	
	public String getName() {
		return this.objectName;
	}
	
	public void setName(String objectName) {
		this.objectName = objectName;
	}
	
	//Returns the grid containing the ships
	public String[][] getGrid() {
		return this.grid;
	}
	
	//Returns the grid without the ships displayed
	public String[][] getSpotsHit() {
		return this.spotsHit;
	}
	
	//Returns a list of shots that haven't been made
	public Set<Coordinate> getPotentialShots() {
		return this.potentialShots;
	}
	
	//Getters and Setters for the hits
	public void setHitShip(int hitShip) {
		this.hitShip = hitShip;
	}
	
	public int getHitShip() {
		return this.hitShip;
	}
	
	public void setHit(Coordinate hit) {
		this.hit = hit;
	}
	
	public Coordinate getHit() {
		return this.hit;
	}
	
	//Sets the movement the computer will go on if a ship has been hit more than once
	public void setMovement(int movement) {
		this.movement = movement;
	}
	
	public int getMovement() {
		return this.movement;
	}
}
