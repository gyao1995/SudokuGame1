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
		//Create and set up the window
		setTitle("SUDOKU");
		setMinimumSize(new Dimension(800, 650));

		
		JPanel contentPane = new JPanel(new GridBagLayout());
		setContentPane(contentPane);
		contentPane.add(createSudokuBoard(), getConstraints());
		contentPane.add(createLeftPanel(100), new GridBagConstraints());
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public JPanel createSudokuBoard(){
		JPanel mainSquare = createSquare(6);
		for( int i = 0; i < 3; i ++ ){
			for( int j = 0; j < 3; j++ ){
				JPanel subSquare = createSquare(2);
				createTextField(subSquare, i, j);
				mainSquare.add(subSquare);
			}
		}
		Border emptyBorder = new EmptyBorder(5, 5, 5, 5);
		mainSquare.setBorder(emptyBorder);
		return mainSquare;
	}
	public JPanel createLeftPanel(int gap){
		GridLayout layout = new GridLayout(2, 1, 1, 1);
		layout.setHgap(gap);
		layout.setVgap(gap);
		
		JPanel leftPanel = new JPanel(layout);
		
		leftPanel.add(createButtonPanel(2), new GridBagConstraints());
		leftPanel.add(createNumberPanel(5), new GridBagConstraints());
		
		return leftPanel;
	}
	
	public JPanel createButtonPanel(int gap) {
		GridLayout layout = new GridLayout(7, 1, 2, 2);
		layout.setHgap(gap);
		layout.setVgap(gap);
		JPanel buttonPanel = new JPanel(layout);
		
		JButton reset = new JButton("Reset");
		reset.setMnemonic(KeyEvent.VK_R);
		
		JButton hint = new JButton("Hint");
		hint.setMnemonic(KeyEvent.VK_H);
		
		JButton solve = new JButton("Solve");
		solve.setMnemonic(KeyEvent.VK_S);
		
		JButton newGame = new JButton("New Game");
		newGame.setMnemonic(KeyEvent.VK_N);
		
		buttonPanel.add(reset);
		buttonPanel.add(hint);
		buttonPanel.add(solve);
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
	
	public JPanel createNumberPanel(int gap){
		GridLayout layout = new GridLayout(3, 3, 1, 1);
		layout.setHgap(gap);
		layout.setVgap(gap);
		JPanel numberPanel = new JPanel(layout);
		
		for( int i = 1; i <= 9; i++ ){
			String value = String.valueOf(i);
			JButton number = new JButton(value);
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
	
	public void createTextField(JPanel square, int mainRow, int mainColumn){
		for( int i = 0; i < 3; i++ ){
			for( int j = 0; j < 3; j++ ){
				JFormattedTextField textField = new JFormattedTextField();
				textField.setPreferredSize(new Dimension(16, 30));
				textField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
				textField.setText(" ");
				textField.setBorder(new EmptyBorder(1, 1, 1, 1));
				square.add(textField);
			}
		}
	}
	
	private GridBagConstraints getConstraints() {
        GridBagConstraints wholePanelCnstr = new GridBagConstraints();
        wholePanelCnstr.fill = java.awt.GridBagConstraints.BOTH;
        wholePanelCnstr.weightx = 1.0;
        wholePanelCnstr.weighty = 1.0;
        return wholePanelCnstr;
    }
	
	public static void main(String[] args){
		JFrame window = new SudokuView();
	}
}