import java.util.*;

public class Dice extends Casino {

  public int getRandom(int input) {
    // Generates a random number 0 -> 100
    int random = -1;
    boolean randomLoop = true;
    while (randomLoop) {
      random = (int)(Math.random() * 101);
      // If random number is equal to target, random again
      if (!(random == input)) {
        randomLoop = false;
      }
    }

    return random;
  }
  
  public double calculateMult(int input) {
    // Calculates win chance, then divides 1 by the win chance
    double doubInput = input;
    double probability = (100.0 - input)/100.0;
    double payout = 1/probability;
    double houseMult = payout - (0.01 * payout);
    return houseMult;
  }
}
