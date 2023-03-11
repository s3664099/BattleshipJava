import java.util.concurrent.TimeUnit;

public class BattleShip {
	
	private Board opponent01;
	private Board opponent02;
	private Board firstShot;
	private Board secondShot;
	private Boolean skipTurn;
	private int players;
	private Display display;
	private int result;
	private int goFirst;
	private int boardSize = 10;
	private int turns = 0;
	private Action action = new Action();
	private Controller controller = new Controller();

	
	public BattleShip() {
		
		players = controller.getNumber("Please enter the number of players (0,1,2): ",0,2);
		
		opponent01 = new Board("Marvin",boardSize);
		opponent02 = new Board("Deep Thought",boardSize);
		display = new Display();
		
		if (players == 0) {
			opponent01.addShips();
			opponent02.addShips();
		} else if (players == 1) {
			opponent02.addShips();
			controller.addShips(opponent01,1, display,boardSize-1);
		} else {
			controller.addShips(opponent01,1, display,boardSize-1);
			controller.addShips(opponent02,2, display,boardSize-1);
		}
		
		result = 1;
		goFirst = opponent01.getRandomNumber(0,1);
		skipTurn = false;
	}
	
	public void startGame() {
		
		if (players == 0) {
			display.displayGrids(boardSize,opponent01.getGrid(),opponent02.getGrid());
		
			//Sleeps for 5 seconds
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (goFirst == 0) {
			firstShot = opponent01;
			secondShot = opponent02;
		} else {
			firstShot = opponent02;
			secondShot = opponent01;
		}
		
		System.out.printf("%s goes first%n",firstShot.getName());
		
		showGrid(boardSize,firstShot,secondShot,players);
		
		while (result != 3) {
			
			turns += 1;
			
			//Sleeps for 2 seconds
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			result = turn(firstShot,secondShot);
			
			if (result !=3) {
				
				if (players == 2) {
					display.displayGrids(boardSize,firstShot.getGrid(),secondShot.getSpotsHit());
				}
				
				result = turn(secondShot,firstShot);
			}
			
			showGrid(boardSize,firstShot,secondShot,players);
			
			if (result == 3) {
				System.out.printf("Game over in %s turns%n",turns);
			}			
		}
	}
	
	private void showGrid(int boardSize, Board firstShot, Board secondShot, int players) {
	
		//Sees how many players and displays the appropriate boards
		if (players == 0) {
			display.displayGrids(boardSize,opponent01.getGrid(),opponent02.getGrid());	
		} else if (players == 1) {
			
			if (firstShot.getManualPlayer()) {
				display.displayGrids(boardSize,firstShot.getGrid(),secondShot.getSpotsHit());
			} else {
				display.displayGrids(boardSize,firstShot.getSpotsHit(),secondShot.getGrid());
			}
		} else {
			display.displayGrids(boardSize,firstShot.getSpotsHit(),secondShot.getGrid());
		}
		
	}
		
	private int turn(Board defender, Board attacker) {
		
		result = 0;
		
		if (!skipTurn) {
			System.out.printf("%s's Shot%n",attacker.getName());
			
			//Checks whether a manual player or not, and if not calls the algorith
			if (attacker.getManualPlayer()) {
				result = controller.fireShot(defender,attacker,boardSize);
			} else {
				result = action.fire(defender,attacker);
			}
		}
		
		skipTurn = false;
		
		//Checks if the shot was a hit, and sets the skip turn flag
		if (result != 0) {
			skipTurn = true;
		}
		
		//Checks for a win conditions
		if (result == 3) {
			System.out.printf("%s has won%n",attacker.getName());
		}
		
		return result;
	}
}
