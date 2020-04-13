package game.helpers;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import game.cards.CardsParser;

public class CardDeck {
	
	/**
	 * This constructor generates a new deck of cards.
	 * 
	 * @param cards	The different cards to be put in the deck.
	 */
	public CardDeck(Set<Card> cards, String dbFileName) {
		if (cards == null) {
			this.cards = this.initializeCards();
		}
		else {
			this.cards = cards;
		}
		this.dbFileName = dbFileName;
		this.drawnCards = new HashSet<Card>();
	}
	
	private final String dbFileName;
	
	/**
	 * This method generates a set of cards to play the game.
	 * 
	 * @return	A fresh initialized set of cards.
	 */
	private Set<Card> initializeCards() {
		CardsParser parser = new CardsParser(this.dbFileName);
		return parser.getCards();
	}
	
	private Set<Card> cards;
	
	private Set<Card> drawnCards;
	
	/**
	 * This method draws a card from the set of cards, that has not been drawn before.
	 * 
	 * @param numberCards	The number of cards to be drawn
	 * 
	 * @return	A set of cards containing numberCards cards that have not been drawn before.
	 */
	public Set<Card> drawCard(int numberCards) {
		Set<Card> result = new HashSet<Card>(numberCards);
		if (this.cards.size() < numberCards) {
			for (Card card : this.cards) {
				result.add(card);
			}
			int remaining = numberCards - this.cards.size();
			this.cards = new HashSet<Card>(this.drawnCards.size());
			for (Card card : this.drawnCards) {
				this.cards.add(card);
			}
			this.drawnCards = new HashSet<Card>();
			result.addAll(this.popRandCards(remaining));
		}
		else {
			result.addAll(this.popRandCards(numberCards));
		}
		return result;
	}
	
	private Set<Card> popRandCards(int numberCards) {
		Set<Card> result = new HashSet<Card>(numberCards);
		for (int i = 0; i < numberCards; i++) {
			int item = new Random().nextInt(this.cards.size());
			int j = 0;
			for (Card card : this.cards) {
				if (j == item) {
					result.add(card);
					this.cards.remove(card);
					break;
				}
				j++;
			}
		}
		return result;
	}
	
	/**
	 * This method returns the given cards to the back of the deck and makes them available to be drawn after the old cards have been exhausted.
	 * 
	 * @param cards	The cards to be returned.
	 */
	public void returnCards(Set<Card> cards) {
		for (Card card : cards) {
			this.drawnCards.add(card);
		}
	}
}
