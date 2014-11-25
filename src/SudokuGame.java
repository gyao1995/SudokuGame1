import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SudokuGame {
	private final SudokuSolution sudokuSolution;
	public int[][] sudokuBoard;
	
	public SudokuGame(){
		sudokuSolution = new SudokuSolution();
		sudokuBoard = new int[9][9];
		
		int[][] tempBoard = new int[9][9];
		for( int i = 0; i < 9; i++ ){
			System.arraycopy(sudokuSolution.sudokuBoard[i], 0, tempBoard[i], 0, tempBoard[i].length);
		}
		
		ArrayList<Integer> rowPos = new ArrayList<Integer>();
		ArrayList<Integer> colPos = new ArrayList<Integer>();
		
		for( int row = 0; row < tempBoard.length; row++ )
			rowPos.add(row);
		for( int column = 0; column < tempBoard.length; column++ )
			colPos.add(column);
		
		Collections.shuffle(rowPos);
		Collections.shuffle(colPos);
		
		for( Integer row: rowPos ) {
			for( Integer column: colPos ){
				int value = tempBoard[row][column];
				tempBoard[row][column] = 0;
				if( !hasOneSolution(row, column, tempBoard) )
					tempBoard[row][column] = value;
			}
		}
		sudokuBoard = tempBoard;
	}
	
	private boolean hasOneSolution( int row, int column, int[][] gameBoard ){
		int numSolutions = 0;
		
		for( int value = 1; value <= 9; value++ ){
			if( SudokuSolution.checkRow(row, value, gameBoard) 
					& SudokuSolution.checkColumn(column, value, gameBoard)
					& SudokuSolution.checkBlock(row, column, value, gameBoard)){
			numSolutions++;
			}
		}

		if( numSolutions == 1 )return true;
		return false;
	}

	public SudokuSolution getSolution(){
		SudokuSolution solution = new SudokuSolution(sudokuSolution.sudokuBoard);
		return solution;
	}
	
	public int[][] getSudokuBoard(){
		return sudokuBoard;
	}
	
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
		if (!Arrays.deepEquals(sudokuBoard, other.sudokuBoard))
			return false;
		if (sudokuSolution == null) {
			if (other.sudokuSolution != null)
				return false;
		} else if (!sudokuSolution.equals(other.sudokuSolution))
			return false;
		return true;
	}

}
