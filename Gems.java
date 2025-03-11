import java.util.*;
import java.math.BigInteger;

public class Gems extends Casino {
  private int mines;
  private String[][] board = {
    {"-", "-", "-", "-", "-"},
    {"-", "-", "-", "-", "-"},
    {"-", "-", "-", "-", "-"},
    {"-", "-", "-", "-", "-"},
    {"-", "-", "-", "-", "-"}
  };

  
  public Gems(int mines) {
    this.mines = mines;
  }


  public String[][] getBoard() {
    return board;
  }

  // Prints board
  public void showBoard(int[][] positions) {
    // Loops through mine coordinates and puts bombs there
    for (int[] coord : positions) {
      board[coord[0]][coord[1]] = "!";
    }
    // Then loop through the whole board and put gems in empty spots
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        if (board[i][j].equals("-")) {
          board[i][j] = "V";
        }
      }
    }
  }

  // Changes board to given string at the given coordinates
  public void modifyBoard(int row, int col, String str) {
    board[row][col] = str;
  }

// Generates coordinates for each mine at random based on number of mines
  public int[][] generateMines() {
    // Creates a 2d array with size 2 arrays for each mines coordinates
    int[][] positions = new int[mines][2];
    // Set each coordinate to (-1, -1) so that (0, 0) doesn't appear as a repeat if it's generated
    for (int[] coord : positions) {
      coord[0] = -1;
      coord[1] = -1;
    }
    for (int i = 0; i < mines; i++) {
      boolean valid = true;
      // Generate a random number for row and column, 0 to 4
      int row = (int)(Math.random() * 5);
      int col = (int)(Math.random() * 5);
      // Check if it's a repeat coordinate
      for (int[] coord : positions) {
        if (coord[0] == row && coord[1] == col && i != 0) {
          valid = false;
        }
      }
      // If the coordinate isn't a repeat, set the position to the generated one
      if (valid) {
        positions[i][0] = row;
        positions[i][1] = col;
      } else {
        // Keeps loop going until all coordinates are made
        i--;
      }
    }
    return positions;
  }

  public void printBoard() {
    // Loop through, print each position on the board
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        System.out.print(board[i][j] + " ");
      }
      System.out.print("\n");
    } // Space after each column, empty line between each row
  }


  private int findSquaresClicked() {
    int total = 0;
    // Finds each gem clicked and counts them
    for (String[] row : board) {
      for (String elem : row) {
        if (elem.equals("V")) {
          total++;
        }
      }
    }
    return total;
  }


  // !!! All scoring related stuff !!!
  
  // Final calculation, hypergeometric probability formula
  public double calculateMult() {
    int gems = 25 - mines;
    double term1 = combination(gems, findSquaresClicked());
    double term2 = combination(25, findSquaresClicked());
    double probability = term1/term2;
    // Mult = 1/win chance
    return 1/probability;
  }

  // Plugs 2 numbers into combination formula
  // Returns how many groups of size num2 can be taken from num1
  // Essentially, this is # of possible groups ignoring groups with the same elements in a different order
  private int combination(int num1, int num2) {
    if (num1 <= num2) {
      return 0;
    }
    BigInteger bigResult = factorial(num1).divide(factorial(num2).multiply(factorial(num1-num2)));
    return bigResult.intValue();
  }

  // Finds the factorial (x!) of a number
  private BigInteger factorial(int number) {
        BigInteger result = new BigInteger("1");

        for (int factor = 2; factor <= number; factor++) {
          BigInteger bigFactor = new BigInteger(String.valueOf(factor));
          result = result.multiply(bigFactor);
        }
        return result;
    }
}
