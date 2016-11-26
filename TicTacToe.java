import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TicTacToe extends JFrame{
	
	JPanel buttonPanel = new JPanel(); //panel for the TicTacToe buttons
	JPanel configuration = new JPanel(); //panel for button to change configuration
	
	JButton setting = new JButton("Configuration"); //button to go to menu
	JButton buttons[][] = new JButton[3][3]; //array that holds the buttons 
	char board[][] = new char[3][3]; //array to hold the current positions of the symbols
	int nTurn = 0; //to keep track of the number of turns
	char symbol = 'X'; //initialize the symbol to X
	char notSymbol; //to keep track of the opponent symbol at any time
	int selection; //to get the choice from the menu
	
	public TicTacToe(int choice) { 	
		selection = choice; //initialize selection
		if(symbol == 'X') //set notSymbol to not symbol
			notSymbol = 'O'; 
		else notSymbol = 'X';
		
		buttonPanel.setLayout(new GridLayout(3,3)); //set layout to a 3 by 3 grid
		for(int i=0; i<3; i++) 
			for(int j=0; j<3; j++) {
				board[i][j] = ' '; //initialize the board
				buttons[i][j] = new JButton();//fills the array of buttons with buttons
				buttons[i][j].addActionListener(new onClick(i, j)); //add action listener to all of them, giving their position
				buttonPanel.add(buttons[i][j]);	//add buttons to the panel			
			}
		setting.addActionListener(new newClicked()); //add action listener to the configuration button
		configuration.add(setting); //add the configuration button to the setting panel
		
		setLocation(500,200); //set default setting of the window
		setSize(500,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout()); //choose border layout
		setVisible(true);	
		
		add(buttonPanel, BorderLayout.CENTER); //add the buttons to the center of the border layout
		add(configuration, BorderLayout.SOUTH);//add the configuration button to the south of the border layout
	}
	
	private class newClicked implements ActionListener{ //if configuration button is clicked, launch menu

		public void actionPerformed(ActionEvent arg0) {
			new Menu();
			dispose();
			
		}
		
		
	}
	void win(char symbol) { //method for a win
		JOptionPane.showMessageDialog(null, symbol + " wins!!"); //show win message
		new TicTacToe(selection).setVisible(true); //launch new TicTacToe with selected settings
		dispose();		
	}
	
	boolean checkWin(char symbol, char[][] board) { //check the current passed in board is a win
		 
		if(board[0][0] == symbol && board[0][1] == symbol && board[0][2] == symbol) //check the 8 possible wins 
			return true; //return true if it's a win
		if(board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol)
			return true;
		if(board[0][0] == symbol && board[1][0] == symbol && board[2][0] == symbol)
			return true;
				
		if(board[1][1] == symbol && board[1][0] == symbol && board[1][2] == symbol)
			return true;
		if(board[1][1] == symbol && board[0][1] == symbol && board[2][1] == symbol)
			return true;
		if(board[1][1] == symbol && board[2][0] == symbol && board[0][2] == symbol)
			return true;
		
		if(board[2][2] == symbol && board[2][0] == symbol && board[2][1] == symbol)
			return true;
		if(board[2][2] == symbol && board[0][2] == symbol && board[1][2] == symbol)
			return true;
		
		return false; //return false if no win
	}
	
	void switchTurn() { //switches the symbol, method created cause it's used a lot
		char temp = symbol;
		symbol = notSymbol;
		notSymbol = temp;
	}
	
	void setter(int r, int c) { //play from the computer, add the play to the board and set the text to the button
		buttons[r][c].setText("" + symbol);
		board[r][c] = symbol;
	}
	
	void safePlay() { //find a common sense move
		char test[][] = new char[3][3]; //char array for tests
		
		for(int i=0; i<3; i++) //check if win move possible
			for(int j=0; j<3; j++) {
				for(int k=0; k<3; k++)
					for(int l=0; l<3; l++)
						test[k][l] = board[k][l]; //set a test board to the current board
				if(test[i][j] == ' ') { //check each possible move
					test[i][j] = symbol;
					if(checkWin(symbol, test)) { //if the move is a win, play this move
						setter(i, j); //play on winning move
						return; //exit the method
					}					
				}				
			}
		for(int i=0; i<3; i++) //check if next move is opponent win
			for(int j=0; j<3; j++) {
				for(int k=0; k<3; k++)
					for(int l=0; l<3; l++)
						test[k][l] = board[k][l]; 
				if(test[i][j] == ' ') {
					test[i][j] = notSymbol;
					if(checkWin(notSymbol, test)) {
						setter(i, j);
						return;
					}					
				}				
			}
		int r, c;
		boolean taken = true;
		do {
			r = (int) (Math.random() * 3);
			c = (int) (Math.random() * 3);
			if(board[r][c] == ' ')
				taken = false;				
		} while(taken);
		setter(r, c);
						
	}
	void goodPlay() {
		if(nTurn == 1) {
			if(board[1][1] == notSymbol) 
				setter(2, 0);
			
			else
				setter(1, 1);					
		}
		else if(nTurn == 2) {
			if(board[1][1] == notSymbol && board[0][2] == notSymbol) 
				setter(0, 0);
			
			else if((board[0][0] == notSymbol && board[2][2] == notSymbol) ||
					(board[0][2] == notSymbol && board[2][0] == notSymbol))
				setter(2, 1);
			else if(board[0][0] == notSymbol && (board[1][2] == notSymbol || board[2][1] == notSymbol)) //start of corner + opposite side
				setter(2, 2);
			else if(board[0][2] == notSymbol && (board[1][0] == notSymbol || board[2][1] == notSymbol))
				setter(2, 0);
			else if(board[2][0] == notSymbol && (board[0][1] == notSymbol || board[1][2] == notSymbol))
				setter(0, 2);
			else if(board[2][2] == notSymbol && (board[0][1] == notSymbol || board[1][0] == notSymbol)) //end of corner + opposite side
				setter(0, 0);
			else if(board[1][0] == notSymbol && board[0][1] == notSymbol)// start of side side
				setter(0, 0);
			else if(board[0][1] == notSymbol && board[1][2] == notSymbol)
				setter(0, 2);
			else if(board[1][2] == notSymbol && board[2][1] == notSymbol)
				setter(2,2);
			else if(board[2][1] == notSymbol && board[1][0] == notSymbol)
				setter(2, 0);
			else if(board[1][0] == notSymbol && board[1][2] == notSymbol)//opposite sides
				setter(2,2);
			else if(board[0][1] == notSymbol && board[2][1] == notSymbol)
				setter(0,0);
			else
				safePlay();
		}
		else
			safePlay();
		
	}
	void alrightPlay() {
		if(nTurn == 1) {
			if(board[1][1] == notSymbol) 
				setter(2, 0);
			
			else
				setter(1, 1);
		}
		else if(nTurn == 2) {
			if(board[1][1] == notSymbol && board[0][2] == notSymbol) 
				setter(0, 0);
			
			else if((board[0][0] == notSymbol && board[2][2] == notSymbol) ||
					(board[0][2] == notSymbol && board[2][0] == notSymbol))
				setter(2, 1);
			else if(board[0][0] == notSymbol && (board[1][2] == notSymbol || board[2][1] == notSymbol)) //start of corner + opposite side
				setter(2, 2);
			else if(board[1][0] == notSymbol && board[0][1] == notSymbol)// start of side side
				setter(0, 0);
			else if(board[1][2] == notSymbol && board[2][1] == notSymbol)
				setter(2,2);
			else if(board[0][1] == notSymbol && board[2][1] == notSymbol)
				setter(0,0);
			else
				safePlay();
		}
		else
			safePlay();			
	}
	
	boolean checkTie() {
		int count = 0;
		boolean isTie = false;
		for(int i=0; i<3; i++)
			for(int j=0; j<3; j++)
				if(board[i][j] == 'X' || board[i][j] == 'O')
					count++;
		if(count == 9) 
			isTie = true;
		return isTie;
	}
	private class onClick implements ActionListener {
		
		int r, c;
		public onClick(int row, int column) {
			r = row;
			c = column;
		}
		public void actionPerformed(ActionEvent e) {
			if(board[r][c] == 'X' || board[r][c] == 'O')
				return;
			buttons[r][c].setText("" + symbol);
			board[r][c] = symbol;
			nTurn++;
			if (checkWin(symbol, board)) {
				win(symbol);
				return;
			}
			if(checkTie()) {
				JOptionPane.showMessageDialog(null, "It's a tie!!");
				new TicTacToe(selection).setVisible(true);
				dispose();
				return;
			}	
			switchTurn();
			
			if(selection == 0)
				switchTurn();
			
			if(selection == 1) 
				safePlay();	
			
			else if(selection == 2) 
				alrightPlay();
				
			else if (selection == 3)
				goodPlay();								
			
			if (checkWin(symbol, board))
				win(symbol);
			if (checkTie()) {
				JOptionPane.showMessageDialog(null, "It's a tie!!");
				new TicTacToe(selection).setVisible(true);
				dispose();
				return;
			}
			
			switchTurn();	
			
			
		}
		
	}

}
