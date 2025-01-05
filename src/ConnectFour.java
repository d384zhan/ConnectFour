/**
* The ConnectFour class.
*
* This class represents a Connect Four (TM)
* game, which allows two players to drop
* checkers into a grid until one achieves
* four checkers in a straight line.
*/

import java.io.*;

public class ConnectFour {

   ConnectFourGUI gui;
   
   // Declares all constants for the game
   
   public final int EMPTY = 0;
   public final int NUMPLAYER = 2;
   public final int NUMROW = 6;
   public final int NUMCOL = 7;
   public final int WINCOUNT = 4;
   public final String GAMEFILEFOLDER = "gamefiles";
   
   // Declares global variables
   
   public int[][] board;
   public int curPlayer;
   
   public ConnectFour(ConnectFourGUI gui) {
      this.gui = gui;
      start();
   }
  
  /*
   * ====================================================================
   * | method play() |
   * |--------------------------------------------------------------------|
   * | void - returns nothing |
   * |--------------------------------------------------------------------|
   * | int column |
   * |--------------------------------------------------------------------|
   * | This method plays a piece based on the column that is clicked |
   * ====================================================================
   */  
    
   public void play (int column) {
      
      // Declares a variable for the empty slot
      int emptySlot = locateEmptySlot(column);
      // Proceeds to play if there is an empty slot (emptySlot is not -1)
      if (emptySlot != -1) {
         // Sets the value in the array to the current player and updates the GUI
         board[emptySlot][column] = curPlayer;
         gui.setPiece(emptySlot, column, curPlayer);
         // Sets win to false by default
         boolean win = false;
         // Checks for win conditions
         if (verticalConnect(emptySlot, column) >= WINCOUNT ||
         horizontalConnect(emptySlot, column) >= WINCOUNT ||
         diagonalConnect1(emptySlot, column) >= WINCOUNT ||
         diagonalConnect2(emptySlot, column) >= WINCOUNT) 
         {
            // If any win condition is satisfied, shows a winner message, 
            // resets board, and sets win to true
            gui.showWinnerMessage(curPlayer);
            resetBoard();
            win = true;
         }
      
         // If win is not satisfied, switches players
         if (!win) {
            if (curPlayer == 1) {
               curPlayer = 2;
               gui.setNextPlayer(curPlayer);
            } else {
               curPlayer = 1;
               gui.setNextPlayer(curPlayer);
            }
         }
      
         // If the board is full, shows a tie message and resets the board
         if (boardFull()) {
            gui.showTieGameMessage();
            resetBoard();
         }
      }
      
   }
   
  /*
   * ====================================================================
   * | method resetBoard() |
   * |--------------------------------------------------------------------|
   * | void - returns nothing |
   * |--------------------------------------------------------------------|
   * | no parameters |
   * |--------------------------------------------------------------------|
   * | This method resets all the values in the board array to 0, 
   * | updates the GUI, and sets the player to 1 |                         
   * ====================================================================
   */
   
   public void resetBoard() {
   
      // Traverses the array and sets all values to empty
      for (int r = 0; r < NUMROW; r++) {
         for (int c = 0; c < NUMCOL; c++) {
            board[r][c] = EMPTY;
         }
      }
      // Resets the GUI and sets player to 1
      gui.resetGameBoard();
      curPlayer = 1;
      gui.setNextPlayer(curPlayer);
   }
   
  /*
   * ====================================================================
   * | method locateEmptySlot() |
   * |--------------------------------------------------------------------|
   * | returns int |
   * |--------------------------------------------------------------------|
   * | parameter(s): int column |
   * |--------------------------------------------------------------------|
   * | This method takes a column and displays the first available slot|
   * ====================================================================
   */
   
   public int locateEmptySlot (int column) {
   
      // Traverses a column starting at the bottom row
      for (int r = NUMROW - 1; r >= 0; r--) {
         if (board[r][column] == EMPTY) {
            // Returns the first empty value
            return r;
         }
      }
      // If the full column is full, returns -1
      return -1;
   
   }
   
   /*
   * ====================================================================
   * | method boardFull() |
   * |--------------------------------------------------------------------|
   * | boolean - returns true or false |
   * |--------------------------------------------------------------------|
   * | no parameters |
   * |--------------------------------------------------------------------|
   * | This method checks if all the values in the array are empty |
   * ====================================================================
   */
   
   public boolean boardFull () {
      
      // Traverses through the top row of the array   
      for (int c = 0; c < NUMCOL; c++) {
         if (board[0][c] == EMPTY) {
            // Returns false if any value is empty
            return false;
         }
      }
      // Otherwise, returns true if values are never empty
      return true;
   }
   
  /*
   * ====================================================================
   * | method verticalConnect() |
   * |--------------------------------------------------------------------|
   * | returns int |
   * |--------------------------------------------------------------------|
   * | parameter(s): int row, int column |
   * |--------------------------------------------------------------------|
   * | This method checks how many times the piece in row and column connects
   * | vertically |
   * ====================================================================
   */
   
   public int verticalConnect (int row, int column) {
      
      // Sets the piece played as the piece in the parameters 
      int playerPiece = board[row][column];
      // Sets the count of consecutive pieces as one
      int count = 1;
      // Begins counting pieces in the row below the player piece
      int r = row + 1;
      // Increases count as long as r is less than board length and the piece is equal to playerPiece
      while (r < board.length && board[r][column] == playerPiece) {
         count++;
         r++;
      }
      // Returns number of consecutive pieces
      return count;
   }
   
   /*
   * ====================================================================
   * | method horizontalConnect() |
   * |--------------------------------------------------------------------|
   * | returns int |
   * |--------------------------------------------------------------------|
   * | parameter(s): int row, int column |
   * |--------------------------------------------------------------------|
   * | This method checks how many times the piece in row and column connects
   * | horizontally |
   * ====================================================================
   */
   
   public int horizontalConnect (int row, int column) {
   
      // Sets the piece played as the piece in the parameters
      int playerPiece = board[row][column];
      // Sets the count of consecutive pieces as one
      int count = 1;
      // Counts the consecutive pieces to the left of the playerPiece 
      int c = column - 1;
      while (c >= 0 && board[row][c] == playerPiece) {
         count++; 
         c--;
      }
      
      // Counts the consecutive pieces to the right of the playerPiece
      c = column + 1;
      while (c < board[row].length && board[row][c] == playerPiece) {
         count++; 
         c++;
      }
      
      // Returns number of consecutive pieces
      return count;
   }
   
   /*
   * ====================================================================
   * | method diagonalConnect1() |
   * |--------------------------------------------------------------------|
   * | returns int |
   * |--------------------------------------------------------------------|
   * | parameter(s): int row, int column |
   * |--------------------------------------------------------------------|
   * | This method checks how many times the piece in row and column connects
   * | diagonally from top left to bottom right |
   * ====================================================================
   */
   
   public int diagonalConnect1 (int row, int column) {
   
      
      // Sets the piece played as the piece in the parameters
      int playerPiece = board[row][column];
      // Sets the count of consecutive pieces as one
      int count = 1;
      // Begins counting from the piece to the top left of the playerPiece
      int r = row - 1;
      int c = column - 1;
      // Loops from the piece to the top edges of the board diagonally and counts the consecutive
      while (r >= 0 && c >= 0 && board[r][c] == playerPiece) {
         count++;
         r--;
         c--;
      }
      // Begins counting from the piece to the bottom right of the playerPiece
      r = row + 1;
      c = column + 1;
      // Loops from the piece to the bottom edges of the board diagonally and counts the consecutive
      while (r < board.length && c < board[row].length && board[r][c] == playerPiece) {
         count++;
         r++;
         c++;
      }
      // Returns the number of consecutive pieces
      return count;
      
   }
      
  /*
   * ====================================================================
   * | method diagonalConnect2() |
   * |--------------------------------------------------------------------|
   * | returns int |
   * |--------------------------------------------------------------------|
   * | parameter(s): int row, int column |
   * |--------------------------------------------------------------------|
   * | This method checks how many times the piece in row and column connects
   * | diagonally from top right to bottom left |
   * ====================================================================
   */
   
   public int diagonalConnect2 (int row, int column) {
   
      // Sets the piece played as the piece in the parameters
      int playerPiece = board[row][column];
      // Sets the count of consecutive pieces as one
      int count = 1;
      // Begins counting from the piece to the top right of the playerPiece
      int r = row - 1;
      int c = column + 1;
      // Loops from the piece to the top edges of the board diagonally and counts the consecutive
      while (r >= 0 && c < board[row].length && board[r][c] == playerPiece) {
         count++;
         r--;
         c++;
      }
      // Begins counting from the piece to the bottom left of the playerPiece
      r = row + 1;
      c = column - 1;
      // Loops from the piece to the bottom edges of the board diagonally and counts the consecutive
      while (r < board.length && c >= 0 && board[r][c] == playerPiece) {
         count++;
         r++;
         c--;
      }
      // Returns the number of consecutive pieces
      return count;
   
   }
   
  /*
   * ====================================================================
   * | method saveToFile() |
   * |--------------------------------------------------------------------|
   * | boolean - returns true or false |
   * |--------------------------------------------------------------------|
   * | parameter(s): String fileName |
   * |--------------------------------------------------------------------|
   * | This method takes in the name of a file and saves the status of the
   * | game to that file, and returns false if unable to save |
   * ====================================================================
   */
          
   public boolean saveToFile (String fileName) {
      
      // Puts the BufferedWriter in a try/catch statement
      try {
         BufferedWriter out = new BufferedWriter (new FileWriter(GAMEFILEFOLDER + "/" + fileName));
         // Loops through the board and prints the values, separated by spaces
         for (int r = 0; r < NUMROW; r++) {
            for (int c = 0; c < NUMCOL; c++) {
               out.write(board[r][c] + " ");
               
            }
            out.newLine();
         }
         // Writes down the current player
         out.write(curPlayer + "");
         // Closes the writer and returns true
         out.close();
         return true;
      } catch (IOException iox) {
         // If writing to file contains an error, returns false
         return false;
      }
   }
   
  /*
   * ====================================================================
   * | method loadFromFile() |
   * |--------------------------------------------------------------------|
   * | boolean - returns true or false |
   * |--------------------------------------------------------------------|
   * | parameter(s): String fileName |
   * |--------------------------------------------------------------------|
   * | This method takes in the name of an existing file and loads the 
   * | status of the game from that file, returns false if cannot load |
   * ====================================================================
   */
   
   public boolean loadFromFile (String fileName) {
      // Puts the BufferedReader in a try/catch statement
      try {
         BufferedReader in = new BufferedReader (new FileReader(GAMEFILEFOLDER + "/" + fileName));
         // Loops through the board and reads the values, using the split method to separate by spaces
         for (int r = 0; r < NUMROW; r++) {
            String[] nums = in.readLine().split(" ");
            for (int c = 0; c < NUMCOL; c++) { 
               board[r][c] = Integer.parseInt(nums[c]);
            }
         }
         // Reads in the current player and updates the GUI
         curPlayer = Integer.parseInt(in.readLine());
         gui.setNextPlayer(curPlayer);
         // Closes the reader and returns true
         in.close();
         return true;
      } catch (IOException iox) {
         // If reading from file contains an error, returns false
         return false;
      }
   }
   
  /*
   * ====================================================================
   * | method start() |
   * |--------------------------------------------------------------------|
   * | void - returns nothing |
   * |--------------------------------------------------------------------|
   * | no parameters |
   * |--------------------------------------------------------------------|
   * | This method initializes the board array to its dimensions and 
   * | resets the board |
   * ====================================================================
   */
   
   public void start () {
   
      // Declares and determines the dimensions of the board based on the constants
      board = new int [NUMROW][NUMCOL];
      // Resets board
      resetBoard();
   
   }
   
  /*
   * ====================================================================
   * | method updateGameBoard() |
   * |--------------------------------------------------------------------|
   * | void - returns nothing |
   * |--------------------------------------------------------------------|
   * | no parameters |
   * |--------------------------------------------------------------------|
   * | This method traverses the board array and updates all the icons 
   * | based on the values in the array |
   * ====================================================================
   */
   
   public void updateGameBoard() {
     
      // Loops through the whole board array and updates the GUI for the taken slots
      for (int r = 0; r < NUMROW; r++) {
         for (int c = 0; c < NUMCOL; c++) {
            if (board[r][c] != EMPTY) {
               gui.setPiece(r, c, board[r][c]);
            }
         }
      }     
   
   }
}
   
