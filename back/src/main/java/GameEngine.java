import java.util.ArrayList;
import java.util.Date;

public class GameEngine {
  ArrayList<Integer> player0Cards = new ArrayList<Integer>();
  String Player0;
  String Player1;

  public String getStatus() {
    return "201";
  }


  public void makeCards() {
    for (int i=0; i<10; i++) {
      int card = (int) Math.floor(Math.random() * 6);
      player0Cards.add(card);
    }
    System.out.println(player0Cards);
  }

  public ArrayList<Integer> Draw(int amount, String id) {
    if (amount < 0) {
      return new ArrayList<Integer>(-1);
    }
    if (player0Cards.size() < amount) {
      amount = player0Cards.size();
    }

    ArrayList<Integer> draw = new ArrayList<Integer>();
    for (int i = 0; i<amount; i++) {
      draw.add(player0Cards.remove(player0Cards.size()-1));
    }
    System.out.println("Drew:" + draw);
    System.out.println("Cards in deck:" + player0Cards);
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
