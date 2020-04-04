package game.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import game.helpers.Character;
import game.helpers.Card;
import game.helpers.CharacterDeck;
import game.player.Player;
import game.helpers.CardDeck;

public class Engine {
	
	/**
	 * This constructor generates a new engine to play the Machiavelli game.
	 * 
	 * @param numberPlayers	Integer containing the number of players.
	 * @param cards			A set of cards to play this game. Provide null if the standard deck is to be used.
	 * @param characters	A set of characters to play this game. Provide null if the standard set of characters is to be used.
	 */
	public Engine(Set<Player> players, Set<Card> cards, Set<Character> characters) {
		this.numberPlayers = players.size();
		this.players = this.initializePlayers(players);
		this.characterDeck = new CharacterDeck(characters);
		this.cardDeck = new CardDeck(cards);
	}
	
	/**
	 * Randomize given set of players. First player in the list will become king in the first
	 * round.
	 * 
	 * @param players	A set of players that will participate in this game.
	 * 
	 * @return			A list of players, representing the order in which characters will be
	 * 					distributed.
	 */
	private List<Player> initializePlayers(Set<Player> players) {
		List<Player> result = new ArrayList<Player>(this.numberPlayers);
		for (int i = 0; i < this.numberPlayers; i ++) {
			int item = new Random().nextInt(this.numberPlayers-i);
			int j = 0;
			for (Player player : players) {
				if (item == j) {
					result.add(player);
					players.remove(player);
					break;
				}
				j ++;
			}
		}
		return result;
	}
	
	private final int numberPlayers;
	
	public final List<Player> players;
	
	private final CharacterDeck characterDeck;
	
	private final CardDeck cardDeck;
	
	public void start() {
		
	}

}
