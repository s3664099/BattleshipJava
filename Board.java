import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {
	
	private int rows;
	private String[][] grid;
	private String[][] spotsHit;
	private String objectName;
	private int gridSize = 9;
	private Set<Coordinate> potentialShots = new HashSet<Coordinate>();
	private Set<Coordinate> usedSpots = new HashSet<Coordinate>();
	private Set<Integer> shipShots = new HashSet<Integer>();
	private Set<Ship> ships = new HashSet<Ship>();
	private int hitShip;
	private Coordinate hit = new Coordinate(0,0);
	private Coordinate originalHit = new Coordinate(0,0);
	private int movement = 0;
	private boolean manualPlayer;
	
	public Board(String objectName, int size) {

		this.objectName = objectName;
		this.rows = size;
		this.grid = new String[rows][rows];
		this.spotsHit = new String[rows][rows];	
				
		//Builds the boards, and creates a set of potential shots
		for (int i=0;i<this.rows;i++) {
			for (int j=0;j<this.rows;j++) {
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
	
	public int getSize() {
		return rows;
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
	
	public void setPotentialShots(Set<Coordinate> potentialShots) {
		this.potentialShots = potentialShots;
	}
	
	//Returns a list of shots that haven't been made
	public Set<Integer> getShipShots() {
		return this.shipShots;
	}
	
	public void setShipShots(Set<Integer> shipShots) {
		this.shipShots = shipShots;
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
	
	public void setOriginalHit(Coordinate originalHit) {
		this.originalHit = originalHit;
	}
	
	public Coordinate getOriginalHit() {
		return this.originalHit;
	}
	
	public Set<Ship> getShips() {
		return this.ships;
	}
	
	public void setManualPlayer() {
		this.manualPlayer = true;
	}
	
	public boolean getManualPlayer() {
		return this.manualPlayer;
	}
	
	public void addShips() {
		
		int code = 0;
		
		//goes through each of the ships and adds them to the board.
		
		for (Ship ship:ships) {
			System.out.printf("%s %s %s%n", ship.getName(),ship.getLength(),ship.getLetter());
			this.grid = addShip(ship);
			code ++;
		}
		
	}
	
	private String[][] addShip(Ship ship) {
		
		int length = ship.getLength();
		String code = ship.getLetter();
		
		//Creates a set to hold the potential places the ship can be placed
		Set<Coordinate> potentialPlace = new HashSet<Coordinate>();
		
		//Selects a random angle for the ship (0 = Across, 1 = Down)
		int angle = getRandomNumber(0,1);
		
		//If the Angle is verticle
		int shipLenX = 0;
		int shipLenY = length;
		int posX = 0;
		int posY = 1;
		
		if (angle == 1) {
			shipLenX = length;
			shipLenY = 0;
			posX = 1;
			posY = 0;			
		}
				
		//Gets a list of potential places to place the ship
		potentialPlace = selectPlaces(shipLenX,shipLenY,potentialPlace,length,angle);
				
		//Checks if the size is 0, and if it is, builds the set from the other angle
		if (potentialPlace.size()<1) {
			potentialPlace = selectPlaces(shipLenY,shipLenX,potentialPlace,length,angle);
		}
		
		//Gets a random number based on the length of the set
		int number = getRandomNumber(0,potentialPlace.size()-1);
		
		//Converts the set into an array
		List<Coordinate> ppArray = new ArrayList<> (potentialPlace);
		
		int shipX = ppArray.get(number).getX();
		int shipY = ppArray.get(number).getY();

		return placeShip(ship,shipX,shipY,posX,posY,length,code,angle);
	}
	
	//Generates a random number
	public int getRandomNumber(int min,int max) {
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	//Builds a list of potential places the ship can be placed
	private Set<Coordinate> selectPlaces(int left,int down,Set<Coordinate> potentialPlace, int length, int angle) {		
		
		left = this.gridSize - left;
		down = this.gridSize - down; 
				
		//Goes across the board and checks the positions the ship will occupy 
		//to see if it is valid
		for (int x=0;x<left;x++) {
			for (int y=0;y<down;y++) {
				
				boolean used = false;
				
				for (int z=0;z<length;z++) {

					//If the there is a space next to a ship, the position is not valid
					for (Coordinate spot:usedSpots) {
						
						//Different calculations depending on angle
						if (angle == 0) {
						   
							//Checks if this position has already been taken
							if ((spot.getX() == x) && (spot.getY() == y+z)) {
							   used = true;
						   }
						} else if (angle == 1) {
							   if ((spot.getX() == x+z) && (spot.getY() == y)) {
								   used = true;
							   }							
						}
					}
				}
				
				//If the position hasn't been taken, adds it to the list
				if (used != true) {
					potentialPlace.add(new Coordinate(x,y));
				}
			}
		}
				
		return potentialPlace;
	}
	
	//Places the ship on the board
	private String[][] placeShip(Ship ship, int shipX, int shipY, int posX, int posY, int length, String code, int angle) {
		
		int placeShipX = shipX;
		int placeShipY = shipY;
		
		//Add the ship to the board
		for (int x=0;x<length;x++) {
			this.grid[placeShipX][placeShipY] = code;
			placeShipX += posX;
			placeShipY += posY;
		}
		
		ship.addCoordinates(shipX, shipY, posX, posY, length);
		
		//Mark the spots on the board that have been used
		if (angle == 1) {		
			for (int x=-1;x<2;x++) {
				for (int y = shipX-1;y<shipX+length;y++) {
					usedSpots.add(new Coordinate(y,shipY+x));
					ship.addHitSections(y, shipY+x);
				}
			}
		} else {
			for (int x=-1;x<2;x++) {
				for (int y = shipY-1;y<shipY+length;y++) {
					usedSpots.add(new Coordinate(shipX+x,y));
					ship.addHitSections(shipX+x,y);
				}
			}
		}
		
		return this.grid;
	}
	
	//Checks which ship has been destroyed
	public int checkWhichShip(Coordinate coOrds, String playerName) {
		
		//Sets the ship and the win flag
		Ship shipToRemove = checkShipHit(coOrds, playerName);
		int won = 1;
		
		if (shipToRemove != null) {
			won = 2;
			
			//Marks off coordinates on board that was occupied by the ship
			Set <Coordinate> sectionsToMark = shipToRemove.getHitSections();
			markGrid(sectionsToMark);
			removeShip(shipToRemove);
		}
		
		//Checks if there are any ships left on the board
		if (checkRemainingShips()) {
			won = 3;
		}
						
		return won;
	}
	
	private Ship checkShipHit(Coordinate coOrds, String playerName) {
		
		Ship shipToRemove = null;
		
		for (Ship ship:ships) {
			if (ship.checkCoordinates(coOrds.getX(), coOrds.getY(),playerName)) {
				shipToRemove = ship;
				ship.sunkShip(playerName);
			}
		}
		
		return shipToRemove;
	}
	
	private void removeShip(Ship shipToRemove) {
		
		ships.remove(shipToRemove);
	}
	
	//Checks if there are any remaining ships on board
	private boolean checkRemainingShips() {
		
		boolean noShips = false;
		
		if (ships.size() == 0) {
			noShips = true;
		}
		
		return noShips;
	}
	
	//Marks the sections of the grid the ship occupies as hit
	private void markGrid(Set<Coordinate> sectionsToMark) {
		
		for (Coordinate mark:sectionsToMark) {
			
			//Checks if the values are within the confines of the board
			//and marks it if it is
			if ((mark.getX()>-1) && (mark.getX()<10)) {
				if ((mark.getY()>-1) && (mark.getY()<10)) {
					this.grid[mark.getX()][mark.getY()] = "0";
					this.spotsHit[mark.getX()][mark.getY()] = "0";
				}
			}
		}
	}
	
	//Method for testing purposes only
	public void testSinkShip(String playerName) {
		
		//Creates a copy of the ship set
		Set<Ship> copyShips = new HashSet<Ship>(ships);
		
		//Goes through each of the chips
		for(Ship ship:copyShips) {
			
			//Gets the co-ordinates of the ships
			Set<Coordinate> coords = new HashSet<Coordinate>(ship.getCoordinate());
			
			System.out.println();
			System.out.println(ship.getName()+" "+ship.getLetter());
			
			//Goes through each of the co-ordinates and checks to see if it was hit
			for (Coordinate coord:coords) {
				
				System.out.println(coord.getX()+","+coord.getY());
				checkWhichShip(coord, playerName);
				
			}
		}
		
	}
}

