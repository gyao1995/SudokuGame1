import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

public class SudokuView extends JFrame{

	public SudokuView(){
		setTitle("SUDOKU");
		setMinimumSize(new Dimension(820, 650));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagLayout layout = new GridBagLayout();
		JPanel contentPane = new JPanel(layout);
		setContentPane(contentPane);
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 15, 5, 5);
		c.weightx = 2.0;
		c.weighty = 0.6;
		c.fill = GridBagConstraints.BOTH;
		contentPane.add(createSudokuBoard(), c);

		c.weightx = 0.0;
		c.weighty = 0.6;
		c.fill = GridBagConstraints.VERTICAL;
		contentPane.add(createRightPanel(), c);
		pack();
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
		c.fill = GridBagConstraints.BOTH;
		
		leftPanel.add(createTitlePanel(), c);
		
		c.gridy = 1;
		leftPanel.add(createButtonPanel(5), c);
		
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		leftPanel.add(createNumberPanel(), c);
		
		return leftPanel;
	}
	
	public JPanel createTitlePanel(){
		GridLayout titleLayout = new GridLayout(4, 1);
		
		JPanel titlePanel = new JPanel(titleLayout);
		JPanel title2 = new JPanel();
		
		JLabel title = new JLabel("SUDOKU");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		
		title2.add(title);
		title2.setBackground(Color.WHITE);
		Border titleBorder = new LineBorder(Color.getHSBColor(0.50f, 0.33f, 0.93f), 4);
		title2.setBorder(titleBorder);
		title2.setOpaque(true);
		
		titlePanel.add(title2);
		titlePanel.setOpaque(true);
		
		return titlePanel;
	}
	
	public JPanel createButtonPanel(int gap) {
		GridLayout layout = new GridLayout(7, 1, 5, 5);
		layout.setHgap(gap);
		JPanel buttonPanel = new JPanel(layout);
		
		JButton reset = new JButton("Reset");
		reset.setMnemonic(KeyEvent.VK_R);
		makeButtonPretty(reset);
		buttonPanel.add(reset);
		
		JButton hint = new JButton("Hint");
		hint.setMnemonic(KeyEvent.VK_H);
		makeButtonPretty(hint);
		buttonPanel.add(hint);
		
		JButton check = new JButton("Check");
		check.setMnemonic(KeyEvent.VK_C);
		makeButtonPretty(check);
		buttonPanel.add(check);
		
		JButton solve = new JButton("Solve");
		solve.setMnemonic(KeyEvent.VK_S);
		makeButtonPretty(solve);
		buttonPanel.add(solve);
		
		JButton newGame = new JButton("New Game");
		newGame.setMnemonic(KeyEvent.VK_N);
		makeButtonPretty(newGame);
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
				square.add(button);
			}
		}
	}
	
	public static void main(String[] args){
		JFrame window = new SudokuView();
	}
}