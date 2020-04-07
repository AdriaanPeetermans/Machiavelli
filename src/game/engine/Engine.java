package game.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
		this.chosenChars = new HashMap<Character, Integer>(this.numberPlayers);
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
	
	private final List<Player> players;
	
	private final CharacterDeck characterDeck;
	
	private final CardDeck cardDeck;
	
	private HashMap<Character, Integer> chosenChars;
	
	/**
	 * This attribute contains the character that was on top of the character deck and can be chosen
	 * again by the last player in a game with 7 players.
	 */
	private Character topChar;
	
	/**
	 * This attribute contains the current king player number. The value is initialized to 0.
	 */
	private int kingPlayer = 0;
	
	/**
	 * Start the engine!
	 */
	public void start() {
		this.initializeGame();
		this.distributeChars();
	}
	
	/**
	 * Prepare this game to start the first round:
	 *  - Provide the player the number of players in this game
	 *  - Provide each player a unique player number
	 *  - Give the king to a random player
	 */
	private void initializeGame() {
		this.provideNumberPlayers();
		this.providePlayerNumber();
		this.provideKing();
	}
	
	private void provideNumberPlayers() {
		for (Player player : this.players) {
			player.setNumberPlayers(this.numberPlayers);
		}
	}
	
	private void providePlayerNumber() {
		int number = 0;
		for (Player player : this.players) {
			player.setPlayerNumber(number);
			number ++;
		}
	}
	
	private void provideKing() {
		for (Player player : this.players) {
			player.setKing(this.kingPlayer);
		}
	}
	
	/**
	 * This method will distribute the characters over the different players. It will execute the
	 * following steps:
	 *  - Provide the players the information of the open characters (if any)
	 *  - Provide the king player with the information of the top character
	 *  - Provide each player with a set of characters, from which he can choose one or put one away
	 *    in case numberPlayers == 2
	 *  - Obtain the characters chosen by each player
	 *  - Obtain the characters put away by each player, in case numberPlayers == 2
	 */
	private void distributeChars() {
		this.provideOpenChars();
		this.provideKingTopChar();
		this.provideObtainPlayerChars();
	}
	
	private void provideOpenChars() {
		int numberOpenChars = 0;
		switch (this.numberPlayers) {
		case 4:
			numberOpenChars = 2;
			break;
		case 5:
			numberOpenChars = 1;
			break;
		}
		HashSet<Character> openChars = new HashSet<Character>(2);
		for (int i = 0; i < numberOpenChars; i++) {
			openChars.add(this.characterDeck.popCharNoKing());
		}
		if (numberOpenChars > 0) {
			for (Player player : this.players) {
				player.setOpenChars(openChars);
			}
		}
	}
	
	private void provideKingTopChar() {
		this.topChar = this.characterDeck.popChar();
		this.players.get(this.kingPlayer).setKingChar(this.topChar);
	}
	
	private void provideObtainPlayerChars() {
		PlayerIterator playerIterator = new PlayerIterator(this);
		while (playerIterator.hasNext()) {
			Player currentPlayer = playerIterator.next();
			if ((this.numberPlayers == 7) && (playerIterator.isLast())) {
				this.characterDeck.makeAvailable(this.topChar);
			}
			currentPlayer.setCharsToChoose(this.characterDeck.getAvailableChars());
			Character chosenChar = null;
			while (!this.characterDeck.isAvailable(chosenChar)) {
				chosenChar = currentPlayer.getChosenChar();
			}
			this.characterDeck.removeAvailableChar(chosenChar);
			this.chosenChars.put(chosenChar, playerIterator.currentPlayer);
			if ((this.numberPlayers == 2) && !(playerIterator.isKing()) && !(playerIterator.isLast())) {
				Character putAwayChar = null;
				while (!this.characterDeck.isAvailable(putAwayChar)) {
					putAwayChar = currentPlayer.getPutAwayChar();
				}
				this.characterDeck.removeAvailableChar(putAwayChar);
			}
		}
	}
	
	/**
	 * This Iterator implementation enables the engine to iterate over all players in the correct
	 * order (starting with the king player) and the correct multiplicity (twice for a game of 2
	 * players).
	 */
	private class PlayerIterator implements Iterator<Player> {
		
		public PlayerIterator(Engine engine) {
			this.engine = engine;
			if ((this.engine.numberPlayers == 2) || (this.engine.numberPlayers == 3)) {
				this.numberIter = this.engine.numberPlayers*2;
			}
			else {
				this.numberIter = this.engine.numberPlayers;
			}
			this.nextPlayer = this.engine.kingPlayer;
		}
		
		private final Engine engine;
		
		private final int numberIter;
		
		private int iter = 0;
		
		private int currentPlayer;
		
		private int nextPlayer;
		
		public boolean isKing() {
			return this.iter == 1;
		}
		
		public boolean isLast() {
			return this.iter == this.numberIter;
		}

		@Override
		public boolean hasNext() {
			return this.iter < this.numberIter;
		}

		@Override
		public Player next() {
			this.currentPlayer = this.nextPlayer;
			Player result = this.engine.players.get(this.currentPlayer);
			this.iter ++;
			this.nextPlayer ++;
			if (this.nextPlayer >= this.engine.numberPlayers) {
				this.nextPlayer = 0;
			}
			return result;
		}
	}

}
