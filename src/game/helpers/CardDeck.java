package game.helpers;

import java.util.Set;

public class CardDeck {
	
	/**
	 * This constructor generates a new deck of cards.
	 * 
	 * @param cards	The different cards to be put in the deck.
	 */
	public CardDeck(Set<Card> cards) {
		if (cards == null) {
			this.cards = this.initializeCards();
		}
		else {
			this.cards = cards;
		}
	}
	
	/**
	 * This method generates a set of cards to play the game.
	 * 
	 * @return	A fresh initialized set of cards.
	 */
	private Set<Card> initializeCards() {
		return null;
	}
	
	private final Set<Card> cards;
	
	/**
	 * This method draws a card from the set of cards, that has not been drawn before.
	 * 
	 * @param numberCards	The number of cards to be drawn
	 * 
	 * @return	A set of cards containing numberCards cards that have not been drawn before.
	 */
	public Set<Card> drawCard(int numberCards) {
		
	}
	
	/**
	 * This method returns the given cards to the back of the deck and makes them available to be drawn after the old cards have been exhausted.
	 * 
	 * @param cards	The cards to be returned.
	 */
	public void returnCards(Set<Card> cards) {
		
	}
}
