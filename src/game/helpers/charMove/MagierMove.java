package game.helpers.charMove;

import java.util.Set;

import game.engine.Engine;
import game.helpers.Card;
import game.helpers.Character;

public class MagierMove extends CharMove {
	
	/**
	 * This constructor generates a new MAGIER move.
	 * 
	 * @param otherPlayer			A boolean, if true, this player wants to exchange all is cards
	 * 								with the cards of an other player.
	 * @param otherPlayerNumber		The player number of the player this player wants to change his
	 * 								cards with.
	 * @param cards					A set of cards that this player wants to draw again.
	 */
	public MagierMove(boolean otherPlayer, int otherPlayerNumber, Set<Card> cards) {
		super(Character.MAGIER);
		this.otherPlayer = otherPlayer;
		this.otherPlayerNumber = otherPlayerNumber;
		this.cards = cards;
	}
	
	public Set<Card> getCards() {
		return cards;
	}
	
	private final Set<Card> cards;
	
	public int getOtherPlayerNumber() {
		return otherPlayerNumber;
	}
	
	private final int otherPlayerNumber;
	
	public boolean isOtherPlayer() {
		return otherPlayer;
	}
	
	private final boolean otherPlayer;

	@Override
	public boolean isValid(Engine engine, int currentPlayerNumber) {
		if (this.isOtherPlayer()) {
			return this.otherPlayerNumber != currentPlayerNumber;
		}
		else {
			Set<Card> currentCards = engine.getCards(currentPlayerNumber);
			return currentCards.containsAll(this.cards);
		}
	}

	@Override
	public void execute(Engine engine, int currentPlayerNumber) {
		if (this.isOtherPlayer()) {
			engine.switchCards(currentPlayerNumber, this.otherPlayerNumber);
		}
		else {
			engine.switchCardsDeck(currentPlayerNumber, this.cards);
		}
	}

}
