
public class BattleShip {
	
	private Board opponent01;
	private Board opponent02;
	private Board firstShot;
	private Board secondShot;
	private Boolean skipTurn;
	private int players;
	private Display display;
	
	public BattleShip() {
		
		opponent01 = new Board("Marvin");
		opponent02 = new Board("Deep Thought");
		display = new Display();
		
		opponent01.addShips();
		opponent02.addShips();
	
	}
	
	public void startGame() {
				
		display.displayGrids(10,opponent01.getGrid(),opponent02.getGrid());
	}

}
