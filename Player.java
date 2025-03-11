import java.util.*;

public class Player {
  private double money;
  ArrayList<Boolean> record = new ArrayList<Boolean>();

  public Player() {
    money = 100;
  }

  public double getMoney() {
    return money;
  }

  public void setMoney(double newMoney) {
    money = newMoney;
  }

  public void updateRecord(Boolean update) {
    record.add(update);
  }

  public void printRecord() {
    // Loop through boolean array, true = win, false = loss. Prints comma if not the only item or not the f
    System.out.println("-----------");
    for (int i = 0; i < record.size(); i++) {
      if (record.get(i)) {
        System.out.print("Win");
      } else {
        System.out.print("Lose");
      }
      if (record.size() <= 1 || !(i == record.size() - 1)) {
        System.out.print(", ");
      }
    }
    System.out.println("");
    System.out.println("-----------");
  }
}
