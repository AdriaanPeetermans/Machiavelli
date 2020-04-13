package testers;

import java.util.Set;

import game.cards.CardsParser;
import game.helpers.Card;

public class TB_CardsParser {

	public static void main(String[] args) {
		CardsParser parser = new CardsParser("src/game/cards/db_cards.txt");
		Set<Card> cards = parser.getCards();
		for (Card card : cards) {
			System.out.println(card);
		}
	}

}
