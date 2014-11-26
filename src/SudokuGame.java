import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * A SudokuGame represents an initial Sudoku game with one valid solution.
 * 
 * @author Gina
 *
 */
public class SudokuGame {
	private final SudokuSolution sudokuSolution;
	private int[][] initialBoard;
	
	/**
	 * Constructs a SudokuGame
	 * 
	 */
	public SudokuGame(){
		sudokuSolution = new SudokuSolution(); // Initialize sudokuSolution
		initialBoard = new int[9][9]; // Initialize initialBoard as an empty int[9][9]
		
		// Clone sudokuSolution.sudokuBoard to a temporary board 
		int[][] tempBoard = new int[9][9];
		for( int i = 0; i < 9; i++ )
			System.arraycopy(sudokuSolution.sudokuBoard[i], 0, tempBoard[i], 0, tempBoard[i].length);
		
		// Create two shuffled lists - row positions and column positions - of the nine integers
		// from 1 to 9, inclusive
		ArrayList<Integer> rowPos = new ArrayList<Integer>();
		ArrayList<Integer> colPos = new ArrayList<Integer>();
		
		for( int row = 0; row <= 8; row++ )
			rowPos.add(row);
		for( int column = 0; column <= 8; column++ )
			colPos.add(column);
		
		Collections.shuffle(rowPos);
		Collections.shuffle(colPos);
		
		// Loop through each row in rowPos
		for( Integer row: rowPos ) {
			
			// Loop through each column in colPos
			for( Integer column: colPos ){
				int value = tempBoard[row][column];
				
				// Set tempBoard[row][column] to 0 and check hasOneSolution()
				tempBoard[row][column] = 0;
				
				// If hasOneSolution() returns false, re-add value to tempBoard[row][column]
				if( !hasOneSolution(row, column, tempBoard) )
					tempBoard[row][column] = value;
			}
		}
		initialBoard = tempBoard;
	}
	
	/**
	 * Returns true if only one value of x, such that 1 <= x <= 9, is valid at currentBoard[row][column]
	 * 
	 * @param row, requires that 0 <= row <= 8
	 * @param column, requires that 0 <= column <= 8
	 * @param currentBoard: the current board
	 * @return true if only one value of x, such that i <= x <= 9, is valid at currentBoard[row][column]
	 * 
	 */
	private boolean hasOneSolution( int row, int column, int[][] currentBoard ){
		int numSolutions = 0; // Initialize numSolutions as 0
		
		for( int value = 1; value <= 9; value++ ){
			if( SudokuSolution.checkValid(row, column, value, currentBoard))
				numSolutions++; // Increment numSolutions by 1 for each valid value
		}

		if( numSolutions == 1 )return true;
		return false;
	}

	/**
	 * Returns a copy of the sudokuBoard of this sudokuSolution
	 * 
	 * @return a copy of the sudokuBoard of this sudokuSolution
	 * 
	 */
	public SudokuSolution getSolution(){
		SudokuSolution solution = new SudokuSolution(sudokuSolution.sudokuBoard);
		return solution;
	}
	
	/**
	 * Returns a copy of this initialBoard
	 * 
	 * @return a copy of this initialBoard
	 * 
	 */
	public int[][] getInitialBoard(){
		int[][] tempBoard = new int[9][9];
		for( int i = 0; i < 9; i++ )
			System.arraycopy(initialBoard[i], 0, tempBoard[i], 0, tempBoard[i].length);
		return tempBoard;
	}
	
	/**
	 * Returns a string representation of this SudokuGame
	 * 
	 */
	public String toString(){
		StringBuffer sb = new StringBuffer();
			
		for( int i = 0; i < initialBoard.length; i++){
			for( int j = 0; j < initialBoard[i].length; j++)
				sb.append( initialBoard[i][j]);
			sb.append("\n");
		}
			
		String sudokuString = sb.toString();
		return sudokuString;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(initialBoard);
		result = prime * result
				+ ((sudokuSolution == null) ? 0 : sudokuSolution.hashCode());
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
		SudokuGame other = (SudokuGame) obj;
		if (!Arrays.deepEquals(initialBoard, other.initialBoard))
			return false;
		if (sudokuSolution == null) {
			if (other.sudokuSolution != null)
				return false;
		} else if (!sudokuSolution.equals(other.sudokuSolution))
			return false;
		return true;
	}

}
