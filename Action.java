import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Action {
	
	int shotX;
	int shotY;
	int selectShot;
	
	public int fire(Board defender, Board attacker) {
		
		int result = 0;
		
		//Sets the variables used in the algorithm
		//including information from previous turn
		
		Coordinate originalHit = new Coordinate(0,0);
		Coordinate shot = new Coordinate(0,0);
		String[][] grid = defender.getGrid();
		String[][] spotsHit = defender.getSpotsHit();
		int hitShip = defender.getHitShip();
		int hitResult = 0;
		Set<Coordinate> potentialShots = attacker.getPotentialShots();
		
		//Has the ship already been hit
		if (hitShip == 1) {
			
			//Gets the previous hit and sets is as the original
			Coordinate hit = defender.getHit();
			originalHit = hit;
			
			Set<Integer> shipShots = defender.getShipShots();
			
			System.out.println(shipShots.size());
			
			int shotSelected = defender.getRandomNumber(0,shipShots.size()-1);
			List<Integer> shstList = new ArrayList<>(shipShots);
			selectShot = shstList.get(shotSelected);
			shstList.remove(shotSelected);

			shipShots.clear();
			
			for(int remainShots:shstList) {
				shipShots.add(remainShots);
			}
			
			defender.setShipShots(shipShots);
			
			defender.setMovement(selectShot);
			
			shot = getNextShot(selectShot,hit);
			
			//Checks to see if there is anything left in potential shots
			if (potentialShots.size()>0) {
				potentialShots = removeShot(potentialShots,shot);
			}
			
		//The ship has been hit more than once
		} else if (hitShip == 2) {
			
			//Retrieves the next shot
			Set<Integer> shipShots = defender.getShipShots();
			Coordinate hit = defender.getHit();
			selectShot = defender.getMovement();
			shot = getNextShot(selectShot,hit);
			
			//Checks to see if there is anything left in potential shots
			if (potentialShots.size()>0) {
				potentialShots = removeShot(potentialShots,shot);
			}			
			
		} else {
			
			//Otherwise selects a random shot from the list of potential shots
			int shotSelected = attacker.getRandomNumber(0,potentialShots.size()-1);
			
			List<Coordinate> ptshList = new ArrayList<Coordinate>(potentialShots);
			shot = ptshList.get(shotSelected);
			
			if (potentialShots.size()>0) {
				potentialShots = removeShot(potentialShots,shot);
			}
		}
		
		attacker.setPotentialShots(potentialShots);
		
		String hitPosition = "0";
		
		System.out.printf("Shot fired at: %s,%s%n",shot.getX(),shot.getY());
		
		//Has this empty spot already been hit?
		if (grid[shot.getX()][shot.getY()] == "0") {
			grid[shot.getX()][shot.getY()] = ".";
		}
		
		//The spot isn't empty
		if (grid[shot.getX()][shot.getY()] != ".") {
			
			//Marks it as a ship
			hitPosition = "X";
			
			//Sets the flag that a ship has been hit and checks if it has been sunk
			defender.setHit(shot);
			hitResult = defender.checkWhichShip(shot,attacker.getName());
			
			System.out.printf("Hit result %s%n",hitResult);
			
			//If a ship wasn't hit previously, sets the flag
			if (hitShip == 0) {
				defender.setHitShip(1);
				Set<Integer> shipShots = checkShipShots(potentialShots,shot,defender);
				defender.setShipShots(shipShots);
			}
			
			//If it has, sets it that it has been hit more than once
			if (hitShip == 1) {
				defender.setHitShip(2);
				defender.setOriginalHit(originalHit);
				
				Set<Integer> shipShots = new HashSet<Integer>();
				
				//Sets the next potential shot in case the shot misses
				if (selectShot == 0) {
					shipShots.add(1);
				} else if (selectShot == 1) {
					shipShots.add(0);
				} else if (selectShot == 2) {
					shipShots.add(3);
				} else {
					shipShots.add(2);
				}
				
				//Checks if the next shot will be off the board
				if ((selectShot == 0) && (shot.getX()+1>defender.getSize()-1)) {
					defender.setMovement(1);
				} else if ((selectShot == 1) && (shot.getX()+1<0)) {
					defender.setMovement(0);
				} else if ((selectShot == 2) && (shot.getY()+1>defender.getSize()-1)) {
					defender.setMovement(3);
				} else if ((selectShot == 3) && (shot.getY()+1<0)) {
					defender.setMovement(2);
				}
				
				defender.setShipShots(shipShots);
			}
		
		//If it was a miss
		} else {
			
			//If the previous shots hit the ship, then goes back to the original spot
			//and sets it in the opposite direction
			if (hitShip == 2) {
				defender.setHitShip(1);
				defender.setHit(defender.getOriginalHit());
			} 
		}
		
		grid[shot.getX()][shot.getY()] = hitPosition;
		spotsHit[shot.getX()][shot.getY()] = hitPosition;
		
		if (hitResult == 2) {
			defender.setHitShip(0);
		}
		
		return hitResult;
	}
		
	//Method that takes a set and a coordinate and removes it from the set
	private Set<Coordinate> removeShot(Set<Coordinate> potentialShots,Coordinate shot) {
		
		//Converts the set into a list
		List<Coordinate> ptshList = new ArrayList<>(potentialShots);
		
		//Locates the coordinate in the list
		for (int x=0;x<ptshList.size();x++) {
			if ((ptshList.get(x).getX() == shot.getX()) && (ptshList.get(x).getY() == shot.getY())) {

				//Removes it from the list
				ptshList.remove(x);
			}
		}
		
		//Clears the set and converts the list back to a new set
		potentialShots.clear();
		potentialShots = new HashSet<Coordinate>(ptshList);
		
		return potentialShots;
	}
	
	//Gets the next shots from when the ship was hit
	private Coordinate getNextShot(int selected,Coordinate hit) {
		
		Coordinate shot;
		
		if (selected == 0) {
			shot = new Coordinate(hit.getX()+1,hit.getY());
		} else if (selected == 1) {
			shot = new Coordinate(hit.getX()-1,hit.getY());
		} else if (selected == 2) {
			shot = new Coordinate(hit.getX(),hit.getY()+1);
		} else {
			shot = new Coordinate(hit.getX(),hit.getY()-1);
		}
		
		return shot;
	}
	
	//Checks to see if the potential shots once the ship is hit, haven't already been
	//taken. If they haven't it adds the potential shot to the list
	private Set<Integer> checkShipShots(Set<Coordinate> potentialShots, Coordinate shot,
			Board defender) {
		
		Set<Integer> shipShots = new HashSet<Integer>();
		
		for (Coordinate ptshCoords:potentialShots) {
		
			if ((ptshCoords.getX() == shot.getX()+1) && (ptshCoords.getY() == shot.getY())) {
				if (shot.getX()+1<defender.getSize()) {
					shipShots.add(0);
				}
			} else if ((ptshCoords.getX() == shot.getX()-1) && (ptshCoords.getY() == shot.getY())) {
				if (shot.getX()-1>-1) {
					shipShots.add(1);
				}
			} else if ((ptshCoords.getX() == shot.getX()) && (ptshCoords.getY() == shot.getY()+1)) {
				if (shot.getY()+1<defender.getSize()) {
					shipShots.add(2);
				}
			}else if ((ptshCoords.getX() == shot.getX()) && (ptshCoords.getY() == shot.getY()-1)) {
				if (shot.getY()-1>-1) {
					shipShots.add(3);
				}
			}
		}
				
		return shipShots;
		
	}
}
/*
 * 
 * #Determines the move that the attacker is going to make
def fire(defender,attacker):




 */
