import java.util.*;

/**
 * A SudokuSolution represents a Sudoku solution.
 * 
 * A typical SudokuSolution is a 9-by-9 array where each row, column and 3-by-3 block 
 * contains exactly one integer x, such that 1 <= x <= 9.    
 * 
 * @author Gina
 *
 */
public class SudokuSolution {
	protected int[][] sudokuBoard;
	
	/**
	 * Constructs a SudokuSolution
	 * 
	 */
	public SudokuSolution(){
		sudokuBoard = generateSolution();
	}
	
	/**
	 * Constructs a SudokuSolution given a sudokuBoard
	 * 
	 * @param sudokuBoard: requires that
	 *            sudokuBoard be a 9-by-9 array
	 *            each row, column and 3-by-3 block contains exactly one integer x, such that
	 *            1 <= x <= 9
	 * @modifies this sudokuBoard by setting this sudokuBoard to the parameter
	 * 
	 */
	public SudokuSolution(int[][] sudokuBoard){
		this.sudokuBoard = sudokuBoard;
	}
	
	/**
	 * Generates a sudokuSolution
	 * 
	 * @modifies an empty sudokuBoard by setting each element of the 9-by-9 array
	 *           to an integer x, such that 1 <= x <= 9
	 *           
	 */
	public int[][] generateSolution(){		
		int[][] tempBoard = new int[9][9];
		
		// Create a shuffled list of nine integers, each integer between 1 and 9, inclusive
		ArrayList<Integer> integers = new ArrayList<Integer>();
		for(int num = 1; num <= 9; num++)
			integers.add(num);
		Collections.shuffle(integers);
		
		// Loop through each row 
		for( int row = 0; row <= 8; row++ ){
			
			// Add each element in integers to intUsed, the list of unused integers
			ArrayList<Integer> intUnused = new ArrayList<Integer>(integers);
			
		    // Loop through each column in the row and set sudokuBoard[row][col] to next valid integer
			for( int col = 0; col <= 8; col++)
				tempBoard[row][col] = getNextNumber(tempBoard, row, col, intUnused);
		}
		return tempBoard;
	}
	
	/**
	 * Returns the next valid integer, inside intUnused, at currentBoard[row][col]
	 *  
	 * @param currentBoard: the current board
	 * @param row: row number, requires that: 0 <= row <= 8
	 * @param col: column number, requires that: 0 <= column <= 8
	 * @param intUnused: unused integers in the row
	 * @return value: integer in intUnused, for which checkValid(row, col, value, intUsed) returns true
	 *         -1 if, for all integers x in intUnused, checkValid(row, col, x, intUnused) returns false
	 *         
	 */
	private int getNextNumber(int[][] currentBoard, int row, int col, ArrayList<Integer> intUnused){
		
		// Loop through each integer in intUnused while intUnused is not empty
		while( intUnused.size() > 0 ){
			int value = intUnused.remove(0);
			if( checkValid(row, col, value, currentBoard)) // Return value if value is valid
				return value; 
			intUnused.add(value); // Re-add value to intUnused if value is invalid
		}
		
		return -1; 
	}
	
	/**
	 * Returns true if value is valid at currentBoard[row][col]: 
	 *	      the value is not contained in its row, col, or 3-by-3 block
	 * 
	 * @param row: row number, requires that: 0 <= row <= 8
	 * @param col: column number, requires that: 0 <= column <= 8
	 * @param value: the value for which to check validity
	 * @param currentBoard: the current board
	 * @return true if value is valid at currentBoard[row][col]:
	 *         the value is not contained in its row, col, or 3-by-3 block
	 *         
	 */
	public static boolean checkValid(int row, int col, int value, int[][] currentBoard){
		boolean valid = checkRow( row, value, currentBoard )
						& checkColumn( col, value, currentBoard )
						& checkBlock( row, col, value, currentBoard );
		return valid;
	}
	
	/**
	 * Returns true if the value is not contained in its row
	 * 
	 * @param row, requires that 0 <= row <= 8
	 * @param value: the value for which to check validity
	 * @param currentBoard: the current board
	 * @return true if, for all x such that 0 <= x <= 8, currentBoard[row][x] != value
	 * 
	 */
	public static boolean checkRow(int row, int value, int[][] currentBoard){
		for( int column = 0; column <= 8; column++ ){
			if( currentBoard[row][column] == value )
				return false;
		}
		return true;
	}
	
	/**
	 * Returns true if the value is not contained in its column
	 * 
	 * @param column, requires that 0 <= col <= 8
	 * @param value: the value for which to check validity
	 * @param currentBoard: the current board
	 * @return true if, for all x such that 0 <= x <= 8; currentBoard[x][col] != value
	 * 
	 */
	public static boolean checkColumn(int column, int value, int[][] currentBoard){
		for( int row = 0; row <= 8; row++ ){
			if( currentBoard[row][column] == value)
				return false;
		}
		return true;
	}
	
	/**
	 * Returns true if value is not contained in its 3-by-3 block
	 * 
	 * @param row, requires that 0 <= row <= 8
	 * @param column, requires that 0 <= column <= 8
	 * @param value: the value for which to check validity
	 * @param currentBoard: the current board
	 * @return true if, for all values x inside the 3-by-3 block, x != value
	 * 
	 */
	public static boolean checkBlock(int row, int column, int value, int[][] currentBoard){
		int rowStart = getBlockIndex(row); // Get the start row of the 3-by-3 block
		int columnStart = getBlockIndex(column); // Get the start column of the 3-by-3 block 
		
		// Loop through each row in the 3-by-3 block
		for( int i = rowStart; i < rowStart + 3; i++ ){
			
			// Loop through each column, inside the 3-by-3 block, in the row
			for( int j = columnStart; j < columnStart + 3; j++ ){
				if( currentBoard[i][j] == value)
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Return the start index of the 3-by-3 block that contains lineNum
	 * 
	 * @param lineNum: the line number
	 * @return the start index of the 3-by-3 block that contains lineNum
	 * 
	 */
	private static int getBlockIndex( int lineNum ){
		int index = -1;
		if( lineNum >= 0 & lineNum <= 2 ) index = 0;
		if( lineNum >= 3 & lineNum <= 5) index = 3;
		if( lineNum >= 6 & lineNum <= 8) index = 6;
		return index;
	}
	
	/**
	 * Returns the string representation of this SudokuSolution
	 * 
	 */
	public String toString(){
		StringBuffer sb = new StringBuffer();
		
		for( int i = 0; i < sudokuBoard.length; i++){
			for( int j = 0; j < sudokuBoard[i].length; j++)
				sb.append( sudokuBoard[i][j]);
			sb.append("\n");
		}
		
		String sudokuString = sb.toString();
		return sudokuString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(sudokuBoard);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SudokuSolution other = (SudokuSolution) obj;
		if (!Arrays.deepEquals(sudokuBoard, other.sudokuBoard))
			return false;
		return true;
	}
}
