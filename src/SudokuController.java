import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JPanel;

public class SudokuController {
	
	private SudokuView currentView;
	protected SudokuGame currentGame;
	protected SudokuSolution currentSolution;
	private JButton[][] sudokuBoard;
	private int[][] initialGameBoard;
	private JButton selectedSquare;
	private JButton[][] wrongSquares;
	private int selectedNumber;
	private boolean win;

	public SudokuController(){
		currentGame = new SudokuGame();
		currentSolution = currentGame.getSolution();
		sudokuBoard = new JButton[9][9];
		setInitialGameBoard(new int[9][9]);
		
		selectedSquare = null;
		wrongSquares = new JButton[9][9];
		initializeWrongSquares();
		
		win = false;
	}
	
	public void bindSudokuButton(int i, int j, JButton button) {
		for( int n = 0; n < 9; n++ ){
			System.arraycopy(currentGame.getInitialBoard()[n], 0, initialGameBoard[n], 0, initialGameBoard[n].length);
		}
		
		int[][] initial = initialGameBoard;
		
		if( initial[i][j] != 0 ){
			Integer value = initial[i][j];
			button.setText(value.toString());
			currentView.makeSudokuInitialButtons(button);
			makeSelectableSquares(button, value);
		}
		else{
			int value = 0;
			makeSelectableSquares(button, value);
		}
		sudokuBoard[i][j] = button;
	}
	

	public void makeSelectableSquares(final JButton button, final int value){
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e){
				if( value == 0 ){
					if(selectedSquare != button & selectedSquare != null){
						selectedSquare.setBackground(Color.WHITE);
						selectSquare(button);
					}
					else if (selectedSquare == null){
						selectSquare(button);
					}
					else if (selectedSquare == button){
						button.setBackground(Color.WHITE);
						button.setText(null);
						resetWrongSquares();
						selectedSquare = null;
					}
				}
				else{
					if(selectedSquare != null)
						selectedSquare.setBackground(Color.WHITE); 
					selectedSquare = null;
					
				}
			}
		});
	}
	
	public void resetWrongSquares(){
		
		for( int i = 0; i < 9; i++ ){
			for( int j = 0; j < 9; j++ ){
				String valueString = wrongSquares[i][j].getText();
				int value = Integer.valueOf(valueString);
		
				if( value != 0 )
					sudokuBoard[i][j].setBackground(Color.WHITE);
			}
		}
		initializeWrongSquares();
	}
	
	public void initializeWrongSquares(){
		for( int i = 0; i < 9; i++ ){
			for( int j = 0; j < 9; j++ )
				wrongSquares[i][j] = new JButton("0");
		}
	}
	
	public void selectSquare(JButton button){
		button.setBackground(Color.getHSBColor(0.55f, 0.33f, 0.85f));
		if(selectedNumber != 0 ){
			button.setText(String.valueOf(selectedNumber));
			button.setFont(new Font("Dialog", 13, 20));
			selectedNumber = 0;
			if( checkWin() )
				win();
		}
		resetWrongSquares();
		
		selectedSquare = button;
	}
	
	public void bindNumber(final int number, final JButton button){
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e){
				selectedNumber = number;
				if(selectedSquare != null ){
					selectedSquare.setText(String.valueOf(selectedNumber));
					selectedSquare.setFont(new Font("Dialog", 13, 20));
					selectedNumber = 0;
					if( checkWin() )
						win();
				}
			}
		});
	}
	
	public void bindNewGameButton(JButton button){
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e){
				createNewGame();
				if( selectedSquare != null )
					selectedSquare.setBackground(Color.WHITE);
				selectedSquare = null;
			}
		});
		resetWrongSquares();
	}
	
	public void bindSolveButton(JButton button){
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e){
				solveGame();
				if( selectedSquare != null )
					selectedSquare.setBackground(Color.WHITE);
				selectedSquare = null;
			}
		});
		resetWrongSquares();
	}
	
	public void bindHintButton(JButton button){
		button.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				getHint();
				if( selectedSquare != null)
					selectedSquare.setBackground(Color.WHITE);
				selectedSquare = null;
				
			}
		});
		resetWrongSquares();
	}
	
	public void bindResetButton(JButton button){
		button.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				resetGame();
				selectedSquare = null;
				
			}
		});
		resetWrongSquares();
	}
	
	public void bindCheckButton(JButton button){
		button.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				checkGame();
				if( selectedSquare != null && selectedSquare.getText().isEmpty() )
					selectedSquare.setBackground(Color.WHITE);
				selectedSquare = null;
				selectedNumber = 0;
				
			}
		});
	}
	
	public void createNewGame(){
		currentView.remove(currentView.contentPane);
		SudokuGame sudokuGame = new SudokuGame();
		currentGame = sudokuGame;
		currentSolution = currentGame.getSolution();
		
		currentView.newContentPane = new JPanel(new GridBagLayout());
		currentView.setContentPane(currentView.newContentPane);
		currentView.addSudokuBoard(currentView.newContentPane);
		currentView.addRightPanel(currentView.newContentPane);
		
		currentView.pack();
		currentView.revalidate();
		currentView.repaint();
		
	}
	
	public void solveGame(){
		int[][] solvedGameBoard = currentSolution.getSudokuBoard(); 
		
		for( int i = 0; i < 9; i++ ){
			for( int j = 0; j < 9; j++ ){
				Integer value = solvedGameBoard[i][j];
				sudokuBoard[i][j].setText(value.toString());
				sudokuBoard[i][j].setFont(new Font("Dialog", 13, 20));
			}
		}
	}
	
	public void resetGame(){
		int[][] initial = initialGameBoard;
		
		for( int i = 0; i < 9; i++ ){
			for( int j = 0; j < 9; j++ ){
				if( initial[i][j] == 0 ){
					sudokuBoard[i][j].setText(null);
					sudokuBoard[i][j].setBackground(Color.WHITE);
				}
			}
		}
	}
	
	public void getHint(){
		ArrayList<Integer> rowPos = new ArrayList<Integer>();
		ArrayList<Integer> colPos = new ArrayList<Integer>();
		
		for( int i = 0; i < 9; i++ )
			rowPos.add(i);
		for( int j = 0; j < 9; j++ )
			colPos.add(j);
		
		Collections.shuffle(rowPos);
		Collections.shuffle(colPos);
		
		int[][] solvedGameBoard = currentSolution.getSudokuBoard(); 

		outerLoop:
		for( int m = 0; m < rowPos.size(); m++ ){
			for( int n = 0; n < colPos.size(); n++ ){
				int row = rowPos.get(m);
				int col = colPos.get(n);
				
				String valueString = sudokuBoard[row][col].getText();
				try {
					Integer.valueOf(valueString);
				} catch (NumberFormatException noValue){
					int num = solvedGameBoard[row][col];
					sudokuBoard[row][col].setText(String.valueOf(num));
					sudokuBoard[row][col].setFont(new Font("Dialog", 13, 20));
					break outerLoop;
				}
			}
		}
	}
	
	public void checkGame(){
		int[][] initial = initialGameBoard;
		int[][] currentGameBoard = new int[9][9];
		
		for( int i = 0; i < 9; i++ ){
			for( int j = 0; j < 9; j++ ){
				String valueString = sudokuBoard[i][j].getText();
				
				try{ 
					int value = Integer.valueOf(valueString);
					currentGameBoard[i][j] = value;
				} catch(NumberFormatException noValue){
					currentGameBoard[i][j] = 0;
				}
			}
		}
		
		for( int i = 0; i < 9; i++ ){
			for( int j = 0; j < 9; j++ ){
				int value = currentGameBoard[i][j];

				if( initial[i][j] == 0 & value != 0){
					currentGameBoard[i][j] = 0;
					boolean valid = (SudokuSolution.checkRow(i, value, currentGameBoard) 
							& SudokuSolution.checkColumn(j, value, currentGameBoard)
							& SudokuSolution.checkBlock(i, j, value, currentGameBoard));
					if(valid == false ){
						sudokuBoard[i][j].setBackground(Color.getHSBColor(0.95f, 0.33f, 0.85f));
						wrongSquares[i][j].setText(sudokuBoard[i][j].getText());
					}else{
						sudokuBoard[i][j].setBackground(Color.WHITE);
					}
				}
			}
		}
	}
	
	public boolean checkWin(){
		int[][] currentGameBoard = new int[9][9];
		
		for( int i = 0; i < 9; i++ ){
			for( int j = 0; j < 9; j++ ){
				String valueString = sudokuBoard[i][j].getText();
				
				try{ 
					int value = Integer.valueOf(valueString);
					currentGameBoard[i][j] = value;
				} catch(NumberFormatException noValue){
					currentGameBoard[i][j] = 0;
				}
			}
		}
		
		for( int i = 0; i < 9; i++ ){
			for( int j = 0; j < 9; j++ ){
				int value = currentGameBoard[i][j];
				
				if( value != currentSolution.getSudokuBoard()[i][j])
					return win = false;
			}
		}
		
		win = true;
		return win;
	}
	
	public void win(){
		SudokuWin win = new SudokuWin();
		win.setCurrentView(currentView);
		win.setCurrentController(this);
	}
	
	private void setInitialGameBoard(int[][] is) {
	    this.initialGameBoard = is;
	}

	public void setCurrentView(SudokuView currentView) {
		this.currentView = currentView;
	}
	
	public int[][] getInitialGameBoard() {
		return initialGameBoard;
	}
}
