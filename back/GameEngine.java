import java.util.ArrayList;

public class GameEngine {
  ArrayList<Integer> cards = new ArrayList<Integer>();
  public ArrayList<Integer> makeCards() {
    for (int i=0; i<9; i++) {
      int card = (int) Math.floor(Math.random() * 6);
      cards.add(card);
    }
    System.out.println(cards);
    return cards;
  }
}
