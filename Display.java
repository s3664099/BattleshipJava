
public class Display {

	String lines;
	
	//Creates the lines to provide the top and bottom edges to the board
	private String createLines(String lines) {
		
		lines = String.format("%s ",lines);
		
		for (int x = 0;x<12;x++) {
			lines = String.format("%s-",lines);
		}
		
		return lines;
		
	}
	
	//Displays the Lines
	private String displayLine(String line,String[][] user, String spaces, int x) {
		
		for (int y=0;y<10;y++) {
			line = String.format("%s%s",line,user[x][y]);
		}
				
		line = String.format("%s|",line);
		
		return line;
	}
	
	//Prints a line that displays the column numbers at the top
	private String createNumbers(String spaces) {
		
		String numbers = String.format("%s  ",spaces);
		
		for (int x=0;x<10;x++) {
			numbers = String.format("%s%s",numbers,x);
		}
		
		return numbers;
	}
	
	//Displays the grid for a single user
	public void displayGrid(int rows, String[][] user) {
		
		String lines = "";
		String spaces = "             ";
		lines = this.createLines(spaces);
		String numberLine = String.format("%s",this.createNumbers(spaces));
		
		System.out.println(numberLine);
		System.out.println(lines);
		
		for (int x=0;x<10;x++) {
			String line = String.format("%s%s|",spaces,x);
			line = String.format("%s",this.displayLine(line,user,spaces,x));
			System.out.println(line);
		}
		
		System.out.println(lines);
	}
	
	//Displays the grid, side by side, for the computer and the player
	public void displayGrids(int rows,String[][] player, String[][] computer) {
		
		String lines = "";
		String spaces = "             ";
		lines = this.createLines(spaces);
		lines = createLines(String.format("%s%s",lines,spaces));
		String numberLine = String.format("%s%s",this.createNumbers(spaces),this.createNumbers(spaces));
		
		System.out.println(numberLine);
		System.out.println(lines);
		
		for(int x=0;x<10;x++) {
			String line = String.format("%s%s|",spaces,x);
			line = String.format("%s%s",this.displayLine(line,player,spaces,x),spaces);
			line = String.format("%s%s|",line,x);
			line = String.format("%s",displayLine(line,computer,spaces,x));
			
			System.out.println(line);
		}
		
		System.out.println(lines);
	}	
}