import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * This class contains methods to generate a view of a Sudoku game.
 * 
 * @author Gina
 *
 */
@SuppressWarnings("serial")
public class SudokuView extends JFrame{
	private SudokuController controller;
	protected JPanel contentPane;
	protected JPanel newContentPane;
	
	/**
	 * Constructs a SudokuView
	 * 
	 */
	public SudokuView(){
		
		// Initialize a new SudokuController
		controller = new SudokuController(); 
		controller.setCurrentView(this);

		// Initialize contentPane
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

	/**
	 * Adds a JPanel representing a 9-by-9 Sudoku board to the contentPane
	 * 
	 * @param contentPane: content pane of this SudokuView
	 * 
	 */
	public void addSudokuBoard(JPanel contentPane){
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 15, 5, 5);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.8;
		c.weighty = 0.6;
		c.fill = GridBagConstraints.BOTH;
		contentPane.add(createSudokuBoard(), c);
	}
	
	/**
	 * Adds a JPanel to the right side of the contentPane
	 * 
	 * @param contentPane: content pane of this SudokuView
	 * 
	 */
	public void addRightPanel(JPanel contentPane){
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 15, 5, 5);
		c.weightx = 0.2;
		c.weighty = 0.6;
		c.fill = GridBagConstraints.BOTH;
		contentPane.add(createRightPanel(), c);
	}
	
	/**
	 * Returns a JPanel representing a 9-by-9 Sudoku game
	 * 
	 * @return mainSquare: representation of a 9-by-9 Sudoku game
	 * 
	 */
	public JPanel createSudokuBoard(){
		JPanel mainSquare = createSquare(6);
		for( int i = 0; i < 3; i ++ ){
			for( int j = 0; j < 3; j++ ){
				JPanel subSquare = createSquare(2);
				createButtonField(subSquare, i, j); // Make each square in the 3-by-3 subSquare a button
				mainSquare.add(subSquare);
			}
		}
		return mainSquare;
	}
	
	/**
	 * Returns a 3-by-3 grid
	 * 
	 * @param gap: the horizontal and vertical gap between each grid-block
	 * @return square: a 3-by-3 grid
	 * 
	 */
	public JPanel createSquare(int gap){
		GridLayout layout = new GridLayout(3, 3);
		layout.setHgap(gap);
		layout.setVgap(gap);
		JPanel square = new JPanel(layout);
		return square;
	}
	
	/**
	 * Adds a JButton to a given square
	 * 
	 * @param square, requires that the square is contained inside the 9-by-9 Sudoku game representation
	 * @param mainRow, requires that 0 <= mainRow <= 2
	 * @param mainColumn, requires that 0 <= mainColumn <= 2
	 * 
	 */
	public void createButtonField(JPanel square, int mainRow, int mainColumn){
		for( int i = 0; i < 3; i++ ){ // Loop through each row in the 3-by-3 block containing square
			for( int j = 0; j < 3; j++ ){ // Loop through each column in the 3-by-3 block containing square
				JButton button = new JButton();
				button.setBorder(BorderFactory.createEmptyBorder());
				button.setBackground(Color.WHITE);
				
				// Calculate the row and column numbers of the square in the 9-by-9 square
				int row = mainRow * 3 + i;
				int column = mainColumn * 3 + j;
				controller.bindSudokuButton(row, column, button); // Bind button to controller
			    square.add(button);
			}
		}
	}
	
	/**
	 * Sets the appearance of an initial game button in the Sudoku game representation
	 * 
	 * @param button, requires that:
	 *                the button is contained inside the 9-by-9 Sudoku game representation, and that
	 *                Integer.valueOf( button.getText() ) is equal to the value at its associated position 
	 *                in controller.initialGameBoard
	 *                
	 */
	public void makeSudokuInitialButtons(JButton button){
		button.setFont(new Font("Dialog", 13, 20));
		button.setBackground(Color.getHSBColor(0.50f, 0.33f, 0.93f));
	}
	
	/**
	 * Returns a JPanel consisting of selection buttons and a number panel
	 * 
	 * @return rightPanel, consisting of selection buttons and a number panel
	 * 
	 */
	public JPanel createRightPanel(){
		GridBagLayout layout = new GridBagLayout();
		JPanel rightPanel = new JPanel(layout);
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(0, 10, 0, 15);
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0.3;
		c.weightx = 40;
		c.fill = GridBagConstraints.BOTH;
		
		rightPanel.add(createTitlePanel(), c);
		
		c.gridy = 7;
		c.weighty = 0.2;
		rightPanel.add(createButtonPanel(), c);
		
		c.gridy = 15;
		c.weighty = 0.4;
		rightPanel.add(createNumberPanel(), c);
		
		return rightPanel;
	}
	
	/**
	 * Returns the title panel of this SudokuView
	 * 
	 * @return titlePanel: the title panel of this SudokuView
	 * 
	 */
	public JPanel createTitlePanel(){
		GridLayout titleLayout = new GridLayout(3, 1);
		JPanel titlePanel = new JPanel(titleLayout);
		
		JPanel emptyPanel = new JPanel();
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
	
	/**
	 * Returns a JPanel consisting of selection buttons
	 * 
	 * @return buttonPanel, consisting of selection buttons
	 * 
	 */
	public JPanel createButtonPanel() {
		GridLayout layout = new GridLayout(6, 1, 5, 5);
		JPanel buttonPanel = new JPanel(layout);
		
		JButton reset = new JButton("Reset");
		reset.setMnemonic(KeyEvent.VK_R);
		makeButtonPretty(reset);
		controller.bindResetButton(reset);
		buttonPanel.add(reset);
		
		JButton hint = new JButton("Hint");
		hint.setMnemonic(KeyEvent.VK_H);
		makeButtonPretty(hint);
		controller.bindHintButton(hint);
		buttonPanel.add(hint);
		
		JButton check = new JButton("Check");
		check.setMnemonic(KeyEvent.VK_C);
		makeButtonPretty(check);
		controller.bindCheckButton(check);
		buttonPanel.add(check);
		
		JButton solve = new JButton("Solve");
		solve.setMnemonic(KeyEvent.VK_S);
		makeButtonPretty(solve);
		controller.bindSolveButton(solve);
		buttonPanel.add(solve);
		
		JButton newGame = new JButton("New Game");
		newGame.setMnemonic(KeyEvent.VK_N);
		makeButtonPretty(newGame);
		controller.bindNewGameButton(newGame);
		buttonPanel.add(newGame);
		
		return buttonPanel;
	}
	
	/**
	 * Returns a panel, consisting of each number from 1 to 9, inclusive and in ascending order
	 * 
	 * @return numberPanel, consisting of each number from 1 to 9, inclusive and in ascending order
	 * 
	 */
	public JPanel createNumberPanel(){
		GridLayout layout = new GridLayout(3, 3, 5, 5);
		JPanel numberPanel = new JPanel(layout);
		
		for( int i = 1; i <= 9; i++ ){
			String value = String.valueOf(i);
			JButton number = new JButton(value);
			makeButtonPretty(number);
			controller.bindNumber(i, number);
			numberPanel.add(number);
		}
		return numberPanel;
	}
	
	/**
	 * Sets the appearance of a selection button or a number panel button
	 * 
	 * @param button, requires that:
	 *                the number is a selection button or
	 *                a number panel button
	 */
	public void makeButtonPretty(JButton button){
		button.setBorder(new LineBorder(Color.WHITE));
		button.setBackground(Color.getHSBColor(0.50f, 0.33f, 0.93f));
	}
	
	public static void main(String[] args){
		new SudokuView();
	}
}