import java.util.*;

// This code is insecure, many inputs cause errors, but due dates happen and I didn't feel like spending
// a bunch of my free time doing error catching. Maybe I'll come back to this at some point
// and make it safe

public class Casino {
  public static void main(String[] args) {
    
    Scanner input = new Scanner(System.in);

    Player player = new Player();
    boolean programRunning = true;
    int endInput;

    while (programRunning) {
      boolean gameRunning = true;
      boolean playing = true;
      
      System.out.println("Welcome to the casino");
      System.out.println("What game would you like to play? Enter 1 for gems, 2 for dice, 3 to see your balance, 4 to see your record, or 5 to quit");
      
      int game = input.nextInt();
      input.nextLine();
      
      switch (game) {
        case 1:
          while (playing) {
            gameRunning = true;
            System.out.println("You have chosen Gems");
            
            // Handling mines
            System.out.println("How many mines would you like?");
            int mines = input.nextInt();
            input.nextLine();
  
            // Handling the bet
            boolean validBet = false;
            int bet = 0;
            while (!validBet) {
              System.out.println("What would you like to wager?");
              bet = input.nextInt();
              input.nextLine();
              if (bet > player.getMoney()) {
                System.out.println("Please enter a valid bet");
              } else {
                validBet = true;
                player.setMoney(player.getMoney() - bet);
              }
            }
  
            // Creates gems object with inputted mines and bet
            Gems gems = new Gems(mines);
            // Setup done, start game
            System.out.println("For inputting rows and columns: Top left corner is [0, 0]");
            boolean minesGenerated = false; 
            int[][] positions = new int[mines][2];
  
            // Loop allows game to keep running
            while (gameRunning) {
              // Generates mines on first loop
              if (!minesGenerated) {
                positions = gems.generateMines();
                minesGenerated = true;
                gems.printBoard();
              }
              
              // Takes user input for rows and columns
              System.out.println("Input the row you'd like to select");
              int row = input.nextInt();
              input.nextLine();
              
              // Checking if row is too large
              if (row > 4) {
                System.out.println("Input a valid row");
                continue;
              }
              
              System.out.println("Input the column you'd like to select");
              int col = input.nextInt();
              input.nextLine();
              
              // Checking if column is too large
              if (col > 4) {
                System.out.println("Input a valid column");
                continue;
              }
  
              // Checking if move has already been played
              if (gems.getBoard()[row][col].equals("V")) {
                System.out.println("That has already been revealed");
                continue;
              }
  
              // Checks if user clicked mine
              boolean mineClicked = false;
              for (int[] coord : positions) {
                if (coord[0] == row && coord[1] == col) {
                  mineClicked = true;
                }
              }
              
              // If mine was clicked:
              if (mineClicked) {
                player.updateRecord(false);
                
                gems.showBoard(positions);
                System.out.println("Oops! You clicked a mine");
                gems.printBoard();
                // Shows board, prints to console, then asks if user would like to play again
                System.out.println("Enter 1 to play again or 2 to exit");
                endInput = input.nextInt();
                input.nextLine();
                // If user quits game, send to hub. If user continues playing, restart game.
                if (endInput == 2) {
                  playing = false;
                  gameRunning = false;
                } else {
                  gameRunning = false;
                }
                
              // If mine wasn't clicked:
              } else {
                // Shows the gem on the board and prints the board
                gems.modifyBoard(row, col, "V");
                gems.printBoard();
                
                // Lets user choose to cash out or continue playing
                System.out.println("1 to keep playing, or 2 to cash out");
                endInput = input.nextInt();
                input.nextLine();
                
                // If user cashes out, print their multiplier, and their earnings
                if (endInput == 2) {
                  player.updateRecord(true);
                  // Round to three decimal places to make mult look better
                  System.out.println("Multiplier: " + round(gems.calculateMult(), 3));
                  double earn = round(bet * gems.calculateMult(), 2);
                  System.out.println("You made: " + earn);
                  player.setMoney(player.getMoney() + earn);
                  
                  // Give user the choice to play again or exit to menu
                  System.out.println("Enter 1 to play again or 2 to exit");
                  endInput = input.nextInt();
                  input.nextLine();
                  
                  // If user quits game, send to hub. If user continues playing, restart game.
                  if (endInput == 2) {
                    playing = false;
                    gameRunning = false;
                  } else {
                    gameRunning = false;
                  }
                }
              }
            }
          }
          break;
        case 2:
          System.out.println("You have chosen dice");
          System.out.println("What is your target number?");
          // Input for target number AKA the high/low comparison
          int target = input.nextInt();
          input.nextLine();

          System.out.println("What would you like to bet?");
          // Taking bet
          int bet = input.nextInt();
          input.nextLine();

          Dice dice = new Dice();
          while (gameRunning) {
            // Takes bet from player
            player.setMoney(player.getMoney() - bet);
            // Generates random number
            int rand = dice.getRandom(target);
            // Player wins
            if (rand > target) {
              player.updateRecord(true);
              System.out.println("You win!");
              System.out.println("Your target: " + target + "| Dice Roll: " + rand);
              System.out.println("------------");
              // Rounding to 3 digits for display purposes
              System.out.println("Multiplier: " + round(dice.calculateMult(target), 3));
              double earn = round(bet * dice.calculateMult(target), 2);
              System.out.println("Your winnings: " + earn);
              player.setMoney(player.getMoney() + earn);
              System.out.println("------------");
              System.out.println("Would you like to roll again? 1 for yes, 2 for no");
              
              endInput = input.nextInt();
              input.nextLine();

              if (endInput == 2) {
                gameRunning = false;
              }
            // House wins
            } else {
              player.updateRecord(false);
              System.out.println("You lose.");
              System.out.println("Your target: " + target + "| Dice Roll: " + rand);
              System.out.println("------------");
              System.out.println("Would you like to roll again? 1 for yes, 2 for no");
              
              endInput = input.nextInt();
              input.nextLine();

              if (endInput == 2) {
                gameRunning = false;
              }
            }
          }
          break;
        case 3:
          // Prints user's balance
          System.out.println(round(player.getMoney(), 2));
          break;
        case 4: 
          // Prints user's record
          player.printRecord();
          break;
        case 5: {
          // Quits program
          programRunning = false;
        }
      }
    }
  }
  public static double round(double value, int places) {
  // I don't know how this works, I copied it from stackoverflow
    long factor = (long) Math.pow(10, places);
    value = value * factor;
    long tmp = Math.round(value);
    return (double) tmp / factor;
  }
}
