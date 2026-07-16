import java.util.ArrayList;
import java.util.Date;

public class GameEngine {
  ArrayList<Integer> cards = new ArrayList<Integer>();
  String Player0;
  String Player1;


  public void makeCards() {
    for (int i=0; i<10; i++) {
      int card = (int) Math.floor(Math.random() * 6);
      cards.add(card);
    }
    System.out.println(cards);
  }

  public ArrayList<Integer> Draw(int amount) {
    if (amount < 0) {
      return new ArrayList<Integer>(-1);
    }
    if (cards.size() < amount) {
      amount = cards.size();
    }

    ArrayList<Integer> draw = new ArrayList<Integer>();
    for (int i = 0; i<amount; i++) {
      draw.add(cards.remove(cards.size()-1));
    }
    System.out.println("Drew:" + draw);
    System.out.println("Cards in deck:" + cards);
    return draw;
  }

  public String player(String name) {
    String id = "Default";
    Date now = new Date();

    id = name + now;
    
    if (Player0 == null) {
      Player0 = id;
    } else if (Player1 == null) {
      Player1 = id;
    } else {
      return null;
    }

    return id;
  }
}
