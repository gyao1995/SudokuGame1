import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class SudokuView extends JFrame{
	private SudokuController controller;
	protected JPanel contentPane;
	protected JPanel newContentPane;
	private JButton newGame;
	private JButton solve;
	private JButton hint;
	private JButton reset;
	private JButton check;
	
	public SudokuView(){
		controller = new SudokuController();
		controller.setCurrentView(this);
		newGame = new JButton();
		solve = new JButton();
		reset = new JButton();
		hint = new JButton();
		check = new JButton();
		
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
		c.weightx = 0.8;
		c.weighty = 0.6;
		c.fill = GridBagConstraints.BOTH;
		contentPane.add(createSudokuBoard(), c);
	}
	
	public void addRightPanel(JPanel contentPane){
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 15, 5, 5);
		c.weightx = 0.2;
		c.weighty = 0.6;
		c.fill = GridBagConstraints.BOTH;
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
	
	public JPanel createSquare(int gap){
		GridLayout layout = new GridLayout(3, 3, 1, 1);
		layout.setHgap(gap);
		layout.setVgap(gap);
		JPanel square = new JPanel(layout);
		return square;
	}
	
	public void createButtonField(JPanel square, int mainRow, int mainColumn){
		for( int i = 0; i < 3; i++ ){
			for( int j = 0; j < 3; j++ ){
				JButton button = new JButton();
				button.setBorder(BorderFactory.createEmptyBorder());
				button.setBackground(Color.WHITE);
				
				int row = mainRow * 3 + i;
				int column = mainColumn * 3 + j;
				controller.bindSudokuButton(row, column, button);
				square.add(button);
			}
		}
	}
	
	public void makeSudokuInitialButtons(JButton button){
		button.setFont(new Font("Dialog", 13, 20));
		button.setBackground(Color.getHSBColor(0.50f, 0.33f, 0.93f));
	}
	
	public void makeButtonPretty(JButton button){
		button.setBorder(new LineBorder(Color.WHITE));
		button.setBackground(Color.getHSBColor(0.50f, 0.33f, 0.93f));
	}
	
	public JPanel createRightPanel(){
		GridBagLayout layout = new GridBagLayout();
		JPanel leftPanel = new JPanel(layout);
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(0, 10, 0, 15);
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0.3;
		c.weightx = 40;
		c.fill = GridBagConstraints.BOTH;
		
		leftPanel.add(createTitlePanel(), c);
		
		c.gridy = 7;
		c.weighty = 0.2;
		leftPanel.add(createButtonPanel(5), c);
		
		c.gridy = 15;
		c.weighty = 0.4;
		leftPanel.add(createNumberPanel(), c);
		
		return leftPanel;
	}
	
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
	
	public JPanel createButtonPanel(int gap) {
		GridLayout layout = new GridLayout(6, 1, 5, 5);
		layout.setHgap(gap);
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
	
	public static void main(String[] args){
		SudokuView view = new SudokuView();
	}
}