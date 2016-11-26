import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Menu extends JFrame{
 
	JPanel configuration = new JPanel(); //panel for JRadio buttons
	JPanel newGame = new JPanel(); //panel for new game button
	JButton button = new JButton("New Game"); //button for new game
	
	JRadioButton humans = new JRadioButton("2 players"); //radio buttons to select 2 players or vs AI and difficulty
	JRadioButton vsNoobAI = new JRadioButton("Noob AI"); //easiest bot
	JRadioButton vsMediumAI	= new JRadioButton("Medium AI"); //tricky bot, but can be defeated
	JRadioButton vsImpossibleAI = new JRadioButton("Impossible AI"); //can't be defeated
	ButtonGroup group = new ButtonGroup(); //group for the JRadioButtons 
	
	public Menu() {
		group.add(humans); //add the buttons to the group
		group.add(vsNoobAI);
		group.add(vsMediumAI);
		group.add(vsImpossibleAI);
		humans.setSelected(true); //initialize the 2 players option
		configuration.add(humans); //add the Jradio buttons to the configuration panel
		configuration.add(vsNoobAI);
		configuration.add(vsMediumAI);
		configuration.add(vsImpossibleAI);
		
		newGame.add(button); //add button to the panel 
		button.addActionListener(new onClick()); //add action listener to the button
		
		setLocation(500,200); //set default settings for the window
		setSize(500,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setLayout(new BorderLayout()); //choose border layout
		setVisible(true);	
		
		add(configuration, BorderLayout.NORTH); //add panels to the border layout
		add(newGame, BorderLayout.SOUTH);
	}
	private class onClick implements ActionListener { 

		public void actionPerformed(ActionEvent e) { //starts TicTacToe, with option selected
			if(humans.isSelected())
				new TicTacToe(0).setVisible(true);
			else if(vsNoobAI.isSelected())
				new TicTacToe(1).setVisible(true);
			else if(vsMediumAI.isSelected())
				new TicTacToe(2).setVisible(true);
			else
				new TicTacToe(3).setVisible(true);
			dispose(); //get rid of this window
		}
		
	}
}
