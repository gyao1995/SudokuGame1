import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class SudokuView extends JFrame{

	private static final long serialVersionUID = 1L;
	protected JPanel contentPane;
	protected JPanel newContentPane;
	private JButton[][] sudokuBoard;
	protected SudokuGame currentGame;
	private int[][] initialGameBoard;
	protected SudokuSolution currentSolution;
	private static JButton newGame;
	private static JButton solve;
	private static JButton hint;
	private static JButton reset;
	private JButton selectedSquare;
	private JButton[][] wrongSquares;
	private JButton check;
	private JButton selectedNumber;
	private boolean win;
	
	public SudokuView(){
		sudokuBoard = new JButton [9][9];
		currentGame = new SudokuGame();
		currentSolution = currentGame.getSolution();
		newGame = new JButton();
		solve = new JButton();
		reset = new JButton();
		hint = new JButton();
		check = new JButton();
		win = false;
		wrongSquares = new JButton[9][9];
		initializeWrongSquares();
		
		contentPane = new JPanel(new GridBagLayout());
		setTitle("SUDOKU");
		setMinimumSize(new Dimension(820, 650));
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		setContentPane(contentPane);
		addSudokuBoard(contentPane);
		addRightPanel(contentPane);
		
		pack();
	}
	
	public void addSudokuBoard(JPanel contentPane){
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 15, 5, 5);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 2.0;
		c.weighty = 0.6;
		c.fill = GridBagConstraints.BOTH;
		contentPane.add(createSudokuBoard(), c);
	}
	
	public void addRightPanel(JPanel contentPane){
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 15, 5, 5);
		c.weightx = 0.0;
		c.weighty = 0.6;
		c.fill = GridBagConstraints.VERTICAL;
		contentPane.add(createRightPanel(), c);
	}
	
	public JPanel createSudokuBoard(){
		JPanel mainSquare = createSquare(6);
		for( int i = 0; i < 3; i ++ ){
			for( int j = 0; j < 3; j++ ){
				JPanel subSquare = createSquare(2);
				createButtonField(subSquare, i, j);
				mainSquare.add(subSquare);
			}
		}
		return mainSquare;
	}
	public JPanel createRightPanel(){
		GridBagLayout layout = new GridBagLayout();
		JPanel leftPanel = new JPanel(layout);
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(0, 10, 0, 15);
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0.3;
		c.fill = GridBagConstraints.BOTH;
		
		leftPanel.add(createTitlePanel(), c);
		
		c.gridy = 7;
		c.weighty = 0.1;
		leftPanel.add(createButtonPanel(5), c);
		
		c.gridy = 15;
		c.weighty = 0.6;
		leftPanel.add(createNumberPanel(), c);
		
		return leftPanel;
	}
	
	public JPanel createTitlePanel(){
		GridLayout titleLayout = new GridLayout(3, 1);
		
		JPanel emptyPanel = new JPanel();
		JPanel titlePanel = new JPanel(titleLayout);
		JPanel title2 = new JPanel(new BorderLayout());
		
		JLabel title = new JLabel("SUDOKU");
		title.setFont(new Font("Dialog", 0, 20));;
		title.setHorizontalAlignment(SwingConstants.CENTER);
		
		title2.add(title, BorderLayout.CENTER);
		title2.setBackground(Color.WHITE);
		Border titleBorder = new LineBorder(Color.getHSBColor(0.50f, 0.33f, 0.93f), 2);
		title2.setBorder(titleBorder);
		title2.setOpaque(true);
		
		titlePanel.add(emptyPanel);
		titlePanel.add(title2);
		titlePanel.setOpaque(true);
		
		return titlePanel;
	}
	
	public JPanel createButtonPanel(int gap) {
		GridLayout layout = new GridLayout(6, 1, 5, 5);
		layout.setHgap(gap);
		JPanel buttonPanel = new JPanel(layout);
		
		JButton reset = new JButton("Reset");
		reset.setMnemonic(KeyEvent.VK_R);
		makeButtonPretty(reset);
		bindResetButton(reset);
		buttonPanel.add(reset);
		
		JButton hint = new JButton("Hint");
		hint.setMnemonic(KeyEvent.VK_H);
		makeButtonPretty(hint);
		bindHintButton(hint);
		buttonPanel.add(hint);
		
		JButton check = new JButton("Check");
		check.setMnemonic(KeyEvent.VK_C);
		makeButtonPretty(check);
		bindCheckButton(check);
		buttonPanel.add(check);
		
		JButton solve = new JButton("Solve");
		solve.setMnemonic(KeyEvent.VK_S);
		makeButtonPretty(solve);
		bindSolveButton(solve);
		buttonPanel.add(solve);
		
		JButton newGame = new JButton("New Game");
		newGame.setMnemonic(KeyEvent.VK_N);
		makeButtonPretty(newGame);
		bindNewGameButton(newGame);
		buttonPanel.add(newGame);
		
		buttonPanel.add(createDifficultyPanel());
		
		return buttonPanel;
	}
	
	public JPanel createDifficultyPanel(){
		JRadioButton easy = new JRadioButton("Easy");
		easy.setMnemonic(KeyEvent.VK_E);
		
		JRadioButton medium = new JRadioButton("Medium");
		medium.setMnemonic(KeyEvent.VK_M);
		medium.setSelected(true);
		
		JRadioButton difficult = new JRadioButton("Difficult");
		medium.setMnemonic(KeyEvent.VK_D);
		
		ButtonGroup group = new ButtonGroup();
		group.add(easy);
		group.add(medium);
		group.add(difficult);
		
		JPanel difficulties = new JPanel();
		difficulties.add(easy);
		difficulties.add(medium);
		difficulties.add(difficult);
		
		return difficulties;
	}
	
	public JPanel createNumberPanel(){
		GridLayout layout = new GridLayout(3, 3, 5, 5);
		JPanel numberPanel = new JPanel(layout);
		
		for( int i = 1; i <= 9; i++ ){
			String value = String.valueOf(i);
			JButton number = new JButton(value);
			makeButtonPretty(number);
			bindNumber(i, number);
			numberPanel.add(number);
		}
		return numberPanel;
	}
	
	public JPanel createSquare(int gap){
		GridLayout layout = new GridLayout(3, 3, 1, 1);
		layout.setHgap(gap);
		layout.setVgap(gap);
		JPanel mainSquare = new JPanel(layout);
		return mainSquare;
	}
	
	public void makeButtonPretty(JButton button){
		button.setBorder(new LineBorder(Color.WHITE));
		button.setBackground(Color.getHSBColor(0.50f, 0.33f, 0.93f));
	}
	
	public void createButtonField(JPanel square, int mainRow, int mainColumn){
		for( int i = 0; i < 3; i++ ){
			for( int j = 0; j < 3; j++ ){
				JButton button = new JButton();
				button.setBorder(BorderFactory.createEmptyBorder());
				button.setBackground(Color.WHITE);
				
				int row = mainRow * 3 + i;
				int column = mainColumn * 3 + j;
				bindSudokuButton(row, column, button);
				square.add(button);
			}
		}
	}
	
	public void bindSudokuButton(int i, int j, JButton button) {
		initialGameBoard = new int[9][9];
		for( int n = 0; n < 9; n++ ){
			System.arraycopy(SudokuGame.sudokuBoard[n], 0, initialGameBoard[n], 0, initialGameBoard[n].length);
		}
		
		int[][] initial = initialGameBoard;
		
		if( initial[i][j] != 0 ){
			Integer value = initial[i][j];
			button.setText(value.toString());
			makeSudokuInitialButtons(button);
			makeSelectableSquares(button, value);
		}
		else{
			int value = 0;
			makeSelectableSquares(button, value);
		}
		sudokuBoard[i][j] = button;
	}
	
	public void makeSudokuInitialButtons(JButton button){
		button.setFont(new Font("Dialog", 13, 20));
		button.setBackground(Color.getHSBColor(0.50f, 0.33f, 0.93f));
	}
	
	public void makeSelectableSquares(final JButton button, final int value){
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e){
				if( value == 0 ){
					if(selectedSquare != button & selectedSquare != null){
						selectedSquare.setBackground(Color.WHITE);
						resetWrongSquares();
						selectSquare(button);
					}
					else if (selectedSquare == null){
						resetWrongSquares();
						selectSquare(button);
					}
					else if (selectedSquare == button){
						button.setBackground(Color.WHITE);
						button.setText(null);
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
		int[][] wrongSquareBoard = new int[9][9];
		
		for( int i = 0; i < 9; i++ ){
			for( int j = 0; j < 9; j++ ){
				String valueString = wrongSquares[i][j].getText();
				int value = Integer.valueOf(valueString);
				wrongSquareBoard[i][j] = value;
			}
		}
		
		for( int i = 0; i < 9; i++ ){
			for( int j = 0; j < 9; j++ ){
				int value = wrongSquareBoard[i][j];
				
				if( value != 0 )
					wrongSquares[i][j].setBackground(Color.WHITE);
			}
		}
		initializeWrongSquares();
	}
	
	public void initializeWrongSquares(){
		for( int i = 0; i < 9; i++ ){
			for( int j = 0; j < 9; j++ ){
				wrongSquares[i][j] = new JButton("0");
			}
		}
	}
	
	public void selectSquare(JButton button){
		button.setBackground(Color.getHSBColor(0.55f, 0.33f, 0.85f));
		if(selectedNumber != null ){
			int number = Integer.parseInt(selectedNumber.getText());
			button.setText(String.valueOf(number));
			button.setFont(new Font("Dialog", 13, 20));
			if( checkWin() ){
				win();
			}
		}
		selectedNumber = null;
		selectedSquare = button;
	}
	
	public void bindNumber(final int number, final JButton button){
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e){
				selectedNumber = button;
				if(selectedSquare != null ){
					selectedSquare.setText(String.valueOf(number));
					selectedSquare.setFont(new Font("Dialog", 13, 20));
					if( checkWin() ){
						win();
					}
				}
			}
		});
	}
	
	public void bindNewGameButton(JButton button){
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e){
				createNewGame();
			}
		});
		newGame = button;
	}
	
	public void bindSolveButton(JButton button){
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e){
				solveGame();
			}
		});
		solve = button;
	}
	
	public void bindHintButton(JButton button){
		button.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				getHint();
			}
		});
		hint = button;
	}
	
	public void bindResetButton(JButton button){
		button.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				resetGame();
			}
		});
		reset = button;
	}
	
	public void bindCheckButton(JButton button){
		button.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				checkGame();
			}
		});
		check = button;
	}
	
	public void createNewGame(){
		remove(contentPane);
		SudokuGame sudokuGame = new SudokuGame();
		newContentPane = new JPanel(new GridBagLayout());
		setContentPane(newContentPane);
		addSudokuBoard(newContentPane);
		addRightPanel(newContentPane);
		
		pack();
		revalidate();
		repaint();
		
		currentGame = sudokuGame;
		currentSolution = currentGame.getSolution();
	}
	
	public void solveGame(){
		int[][] solvedGameBoard = currentSolution.sudokuBoard; 
		
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
		
		int[][] solvedGameBoard = currentSolution.sudokuBoard; 

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
						wrongSquares[i][j] = sudokuBoard[i][j];
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
				
				if( value != currentSolution.sudokuBoard[i][j])
					return win = false;
			}
		}
		
		win = true;
		return win;
	}
	
	public void win(){
		for( int i = 0; i < 9; i++ ){
			for( int j = 0; j < 9; j++ ){
				makeSudokuInitialButtons(sudokuBoard[i][j]);
			}
		}
		SudokuWin win = new SudokuWin();
		win.setCurrentView(this);
	}
	
	public static void main(String[] args){
		SudokuView view = new SudokuView();
	}
}