import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class Controller {
	
	Scanner sc = new Scanner(System.in);
	
	//Gets a player to input a number between min & max
	public int getNumber(String query, int min, int max) {
		
		boolean correct = false;
		int response = 0;
		
		//Loops if the answer is not correct
		while(!correct) {
			
			System.out.print(query);
			
			//Catches an input mismatch
			try {
				response = sc.nextInt();
				
				if (response <= max && response >= min) {
					correct = true;
				} else {
					System.out.printf("%nPlease enter a number between %s and %s%n",min,max);
				}
				
			} catch(InputMismatchException e) {
				System.out.printf("%nPlease enter a number%n");
				sc.next();
			}			
		}
		
		return response;
	}
	
	public void addShips(Board user,int num, Display display, int boardSize) {
		
		Set<Ship> ships = user.getShips();
		user.setManualPlayer();
		
		//Sets up the player's name
		sc.nextLine();
		System.out.printf("Please enter player %s's name: ",num);
		String userName = sc.nextLine();
		System.out.println();
		System.out.println();
		
		if (userName.length()<1) {
			userName = userName.format("Nameless Player %s",num);
		}
		
		user.setName(userName);
		
		for (Ship ship:ships) {
			
			//Creates a set to hold the potential places the ship can be placed
			Set<Coordinate> potentialPlace = new HashSet<Coordinate>();
			
			display.displayGrid(10,user.getGrid());
			
			System.out.printf("Placing the %s%n",ship.getName());
			int length = ship.getLength();
			String code = ship.getLetter();
			
			//If the angle if vertical
			int shipLenX = 0;
			int shipLenY = length;
			int posX = 0;
			int posY = 1;
			
			int angle = getAngle();
			
			//If the angle is horizontal
			if (angle == 1) {
				shipLenX = length;
				shipLenY = 0;
				posX = 1;
				posY = 0;
			}
			
			//Generates a list of potential places
			potentialPlace = user.selectPlaces(shipLenX,shipLenY,potentialPlace,length,angle);

			boolean allowable = false;
			
			while (!allowable) {
				
				//Gets the player's preferred position
				int yPos = getNumber("Enter the x position: ",0,boardSize);
				int xPos = getNumber("Enter the y position: ",0,boardSize);
				
				//Checks if the position is one of the potential places
				for (Coordinate place:potentialPlace) {
					if (place.getX() == xPos) {
						if (place.getY() == yPos) {
							allowable = true;
						}
					}
				}
				
				//If it isn't displays potential places
				if (!allowable) {
					System.out.printf("You cannot place the %s there.%n",ship.getName());
					System.out.println("Allowable places: ");
					
					for (Coordinate place:potentialPlace) {
						System.out.printf("(%s,%s)%n",place.getX(),place.getY());
					}
				
				//Otherwise places the ship
				} else {
					user.placeShip(ship,xPos,yPos,posX,posY,length,code,angle);
				}
			}
			
			System.out.printf("Press enter to continue: ",num);
			String enter = sc.nextLine();
			System.out.println();
		}
		
	}
	
	public int fireShot(Board defender, Board attacker, int boardSize) {
		
		boolean alreadyHit = false;
		
		//Sets the win flag
		int win = 0;
		
		while (!alreadyHit) {
			
			//Gets player's shot
			int xCoord = getNumber("Select Row: ",0,boardSize);
			int yCoord = getNumber("Select Column: ",0,boardSize);
			String[][] spotsHit = defender.getSpotsHit();
			String[][] grid = defender.getGrid();
			
			//Checks if the player has already fired there
			if ((grid[xCoord][yCoord] == "0") || (grid[xCoord][yCoord] == "X")) {
				System.out.println("You have already fired a shot there");

			//Hits empty section
			} else if (grid[xCoord][yCoord] == ".") {
				grid[xCoord][yCoord] = "0";
				spotsHit[xCoord][yCoord] = "0";
				alreadyHit = true;

			//Hits occupied section
			} else {
				win = 1;
				grid[xCoord][yCoord] = "X";
				spotsHit[xCoord][yCoord] = "X";
				alreadyHit = true;
			}
			
			//Determines which ship has been hit, and whether it has been sunk
			if (win == 1) {
				win = defender.checkWhichShip(new Coordinate(xCoord,yCoord),attacker.getName());
			}
		}
		return win;
	}
			
	//Function for the player to get an angle
	private int getAngle() {
		System.out.println("Please enter the angle");
		System.out.println("0) Across");
		System.out.println("1) Down");
		return getNumber("",0,1);
	}
}
