import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class SudokuWin extends JFrame {
	private SudokuController controller;
	private SudokuView currentView;
	private JPanel winContentPane;
	
	public SudokuWin(){
		
		winContentPane = new JPanel(new GridBagLayout());
		setTitle("SUDOKU");
		setMinimumSize(new Dimension(300, 200));
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setContentPane(winContentPane);
		addMainPanel(winContentPane);
		pack();
	}
	
	public void addMainPanel(JPanel contentPane){
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.7;
		c.fill = GridBagConstraints.BOTH;
		contentPane.add(createMainPanel(), c);
		
		c.insets = new Insets(0, 5, 5, 5);
		c.gridy = 1;
		c.weighty = 0.3;
		contentPane.add(createButtonPanel(), c);

	}
	
	public JPanel createMainPanel(){
		GridLayout layout = new GridLayout(3, 1);
		
		JPanel emptyPanel = new JPanel();
		emptyPanel.setBackground(Color.getHSBColor(0.50f, 0.33f, 0.93f));
		JPanel titlePanel = new JPanel(layout);
		JPanel title2 = new JPanel(new BorderLayout());
		
		JLabel title = new JLabel("Congratulations! You Win!");
		title.setFont(new Font("Dialog", 0, 20));;
		title.setHorizontalAlignment(SwingConstants.CENTER);
		
		title2.add(title, BorderLayout.CENTER);
		title2.setBackground(Color.getHSBColor(0.50f, 0.33f, 0.93f));
		title2.setOpaque(true);
		
		titlePanel.add(emptyPanel);
		titlePanel.add(title2);
		titlePanel.setBackground(Color.getHSBColor(0.50f, 0.33f, 0.93f));
		titlePanel.setOpaque(true);
		
		return titlePanel;
	}
	
	public JPanel createButtonPanel(){
		GridLayout layout = new GridLayout(1, 3, 5, 5);
		JPanel buttonPanel = new JPanel(layout);
		
		JButton newGame = new JButton("New Game");
		newGame.setBorder(new LineBorder(Color.WHITE));
		newGame.setBackground(Color.getHSBColor(0.50f, 0.33f, 0.93f));
		bindNewGameButton(newGame);
		buttonPanel.add(newGame);
		
		JButton quitGame = new JButton("Quit Game");
		quitGame.setBorder(new LineBorder(Color.WHITE));
		quitGame.setBackground(Color.getHSBColor(0.50f, 0.33f, 0.93f));
		bindQuitGameButton(quitGame);
		buttonPanel.add(quitGame);
		
		JButton cancel = new JButton("Cancel");
		cancel.setBorder(new LineBorder(Color.WHITE));
		cancel.setBackground(Color.getHSBColor(0.50f, 0.33f, 0.93f));
		bindCancelButton(cancel);
		buttonPanel.add(cancel);
		
		return buttonPanel;
	}
	
	public void bindNewGameButton(JButton button){
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e){
				createNewGame();
				dispose();
			}
		});
	}
	
	public void bindCancelButton(JButton button){
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
	}
	
	public void bindQuitGameButton(JButton button){
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e){
				dispose();
				closeCurrentView();
			}
		});
	}
	
	public void createNewGame(){
		controller.createNewGame();
	}
	
	public void setCurrentView(SudokuView currentView){
		this.currentView = currentView;
	}
	
	public void closeCurrentView(){
		currentView.dispose();
	}

	public void setCurrentController(SudokuController controller) {
		this.controller = controller;
	}
}
