import java.util.*;

public class SudokuSolution {
	protected int[][] sudokuBoard;
	
	public SudokuSolution(){
		sudokuBoard = new int[9][9];
		generateSolution(sudokuBoard);
	}
	
	public SudokuSolution(int[][] sudokuBoard){
		this.sudokuBoard = sudokuBoard;
	}
	
	public void generateSolution(int[][] sudokuBoard){
		ArrayList<Integer> integers = new ArrayList<Integer>();
		
		for(int num = 1; num <= 9; num++)
			integers.add(num);
		Collections.shuffle(integers);
		
		for( int i = 0; i < 9; i++ ){
			ArrayList<Integer> rowNums = new ArrayList<Integer>(integers);
			for( int j = 0; j < 9; j++){
				sudokuBoard[i][j] = getNextNumber(sudokuBoard, i, j, rowNums);
			}
		}
	}
	
	private int getNextNumber(int[][] board, int row, int column, ArrayList<Integer> rowNums){
		while( rowNums.size() > 0 ){
			int value = rowNums.remove(0);
			if( checkValid(row, column, value, sudokuBoard)){
				return value;
			}
			rowNums.add(value);
		}
		return -1; 
	}
	
	public static boolean checkValid(int row, int column, int value, int[][] sudokuBoard){
		boolean valid = checkRow( row, value, sudokuBoard )
						& checkColumn( column, value, sudokuBoard )
						& checkBlock( row, column, value, sudokuBoard );
		return valid;
	}
	
	/**
	 * @param row, the row of the sudokuBoard along which to check.
	 * @param value is the value to check for
	 * @param sudokuBoard
	 */
	public static boolean checkRow(int row, int value, int[][] sudokuBoard){
		for( int column = 0; column <= 8; column++ ){
			if( sudokuBoard[row][column] == value )
				return false;
		}
		return true;
	}
	
	public static boolean checkColumn(int column, int value, int[][] sudokuBoard){
		for( int row = 0; row <= 8; row++ ){
			if( sudokuBoard[row][column] == value)
				return false;
		}
		return true;
	}
	
	public static boolean checkBlock(int row, int column, int value, int[][] sudokuBoard){
		int rowStart = getBlockIndex(row);
		int columnStart = getBlockIndex(column);
		
		for( int i = rowStart; i < rowStart + 3; i++ ){
			for( int j = columnStart; j < columnStart + 3; j++ ){
				if( sudokuBoard[i][j] == value)
					return false;
			}
		}
		return true;
	}
	
	private static int getBlockIndex( int lineNum ){
		int index = -1;
		if( lineNum >= 0 & lineNum <= 2 ) return index = 0;
		if( lineNum >= 3 & lineNum <= 5) return index = 3;
		if( lineNum >= 6 & lineNum <= 8) return index = 6;
		return index;
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
