package game.engine;

import java.util.Set;

import game.helpers.Character;
import game.helpers.Card;
import game.helpers.CharacterDeck;
import game.helpers.CardDeck;

public class Engine {
	
	/**
	 * This constructor generates a new engine to play the Machiavelli game.
	 * 
	 * @param numberPlayers	Integer containing the number of players.
	 * @param cards	A set of cards to play this game. Provide null if the standard deck is to be used.
	 * @param characters	A set of characters to play this game. Provide null if the standard set of characters is to be used.
	 */
	public Engine(int numberPlayers, Set<Card> cards, Set<Character> characters) {
		this.numberPlayers = numberPlayers;
		this.characterDeck = new CharacterDeck(characters);
		this.cardDeck = new CardDeck(cards);
	}
	
	private final int numberPlayers;
	
	private final CharacterDeck characterDeck;
	
	private final CardDeck cardDeck;
	
	public void start() {
		
	}

}
