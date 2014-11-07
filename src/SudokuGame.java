import java.util.ArrayList;
import java.util.Collections;

public class SudokuGame {
	private final SudokuSolution sudokuSolution;
	private int[][] sudokuBoard;
	
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
		SudokuSolution tempSudoku = new SudokuSolution(gameBoard);
		
		for( int value = 1; value <= 9; value++ ){
			if( tempSudoku.checkRow(row, value, gameBoard) 
					& tempSudoku.checkColumn(column, value, gameBoard)
					& tempSudoku.checkBlock(row, column, value, gameBoard)){
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

}
