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
import game.helpers.Revenue;
import game.helpers.charMove.CharMove;
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
		this.cardDeck = new CardDeck(cards, "src/game/cards/db_cards.txt");
		this.initializeChosenChars();
		this.initializeCoins(2);
		this.initializeCards(4);
		this.initializeCity();
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
	
	/**
	 * This method returns the playerNumber of the player that has chosen the given character. If
	 * no player has chosen this character, the value of -1 is returned.
	 * 
	 * @param character	The character for which the player that has chose it has to be returned.
	 * 
	 * @return			An integer representing the player that has chosen this character.
	 */
	private int getCurrentPlayerNumber(Character character) {
		if (!this.chosenChars.containsKey(character)) {
			return -1;
		}
		return this.chosenChars.get(character);
	}
	
	private void initializeChosenChars() {
		this.chosenChars = new HashMap<Character, Integer>(this.numberPlayers);
	}
	
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
	 * This attribute contains the player number of the stolen player. It contains the value of -1
	 * if no player is stolen.
	 */
	private int stolenPlayer = -1;
	
	/**
	 * This attribute contains the player number that steals. It contains the value of -1 if no
	 * player is stolen.
	 */
	private int thiefPlayer = -1;
	
	/**
	 * This attribute represents the current character that is killed for this round. The player who
	 * has this character will not be able to do his turn during this round.
	 */
	private Character killedChar = null;
	
	/**
	 * Start the engine!
	 */
	public void start() {
		this.initializeGame();
		while (!this.hasEnded()) {
			this.distributeChars();
			for (Character character : this.characterDeck) {
				this.executePlayerTurn(character);
				
			}
		}
	}
	
	/**
	 * This method will check if this game has ended. A game is considered as ended if one or more
	 * players own 8 or more cards in his city.
	 * 
	 * @return	A boolean containing the value true if this game has ended.
	 */
	private boolean hasEnded() {
		for (int i = 0; i < this.numberPlayers; i++) {
			if (this.getNumberBuildings(i) >= 8) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Execute a turn for the player who is holding the given character.
	 * 
	 * @param character	The current character. If no player has this character, this method will
	 * 					have no effect. If this character is killed, this method will reset the
	 * 					killed character and return.
	 */
	private void executePlayerTurn(Character character) {
		if (character == this.killedChar) {
			this.killedChar = null;
			return;
		}
		int currentPlayerNumber = this.getCurrentPlayerNumber(character);
		if (currentPlayerNumber < 0) {
			return;
		}
		Player currentPlayer = this.players.get(currentPlayerNumber);
		for (Player otherPlayers : this.players) {
			otherPlayers.setActiveChar(character, currentPlayerNumber);
		}
		this.handleStolen(currentPlayerNumber);
		this.getCharMove(currentPlayerNumber);
		Revenue revenue = currentPlayer.getRevenue();
		this.handleRevenue(currentPlayerNumber, revenue);
		this.getCharMove(currentPlayerNumber);
		Set<Card> build = null;
		while (!this.isValidBuild(currentPlayerNumber, build)) {
			build = currentPlayer.getBuild();
		}
		this.handleBuild(currentPlayerNumber, build);
		this.getCharMove(currentPlayerNumber);
	}
	
	/**
	 * This method will handle the building action of the given player. It will add the build cards
	 * to the players city and notify all players about the new city of the player.
	 * 
	 * @param playerNumber	An integer representing the current player who wants to build the given
	 * 						set of build cards.
	 * @param build			A set of cards the given player wants to build.
	 */
	private void handleBuild(int playerNumber, Set<Card> build) {
		this.addToCity(playerNumber, build);
	}
	
	/**
	 * This method will check if the given set of build cards is a valid set for the given player.
	 * The set is labeled valid if:
	 * - The set is not equal to the null object;
	 * - The set is empty
	 * - Or the set contains one card
	 * - Or the set contains max three cards if the player has the bouwmeester character
	 * - And if the player can pay for each card
	 * 
	 * @param playerNumber	An integer representing the player who wants to build the given set of
	 * 						cards.
	 * @param build			A set of cards the given player wants to build.
	 * 
	 * @return				A boolean representing if the given player can legally build the given 
	 * 						set of cards.
	 */
	private boolean isValidBuild(int playerNumber, Set<Card> build) {
		if (build == null) {
			return false;
		}
		if (build.size() == 0) {
			return true;
		}
		if (build.size() > 3) {
			return false;
		}
		if (build.size() > 1) {
			if (!(this.getCurrentPlayerNumber(Character.BOUWMEESTER) == playerNumber)) {
				return false;
			}
		}
		for (Card card : build) {
			if (this.alreadyBuilded(playerNumber, card)) {
				return false;
			}
		}
		int playerCoins = this.getCoins(playerNumber);
		for (Card card : build) {
			playerCoins -= card.cost;
		}
		if (playerCoins < 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * This method will handle the revenue of the given player. There are two options:
	 * - Coins: the player will get two coins. The method setCoins() will be invoked to all players
	 *   with the updated amount of coins this player has.
	 * - Cards: this will proceed in the following steps:
	 *          * the engine will invoke setCardsToChoose() providing the player with a set of cards
	 *            from which he can choose on or multiple, depending on which special cards he has.
	 *          * the engine will invoke getChosenCard(), asking for the player to return a set of
	 *            cards he has chosen. If the player chooses a wrong set, this method will be invoked
	 *            again.
	 *          * the engine will invoke setCards(int, int), to update all players with the
	 *            number of cards this player has now.
	 *          * the engine will invoke setCards(Set<Card>), to update the current active player
	 *            with his new set of cards.
	 *            
	 * @param currentPlayerNumber	A number representing the current active player.
	 * @param revenue				A Revenue object representing the choice of the player for his
	 * 								revenue type.
	 */
	private void handleRevenue(int currentPlayerNumber, Revenue revenue) {
		if (revenue.isCoins()) {
			this.setCoins(currentPlayerNumber, this.getCoins(currentPlayerNumber)+2);
		}
		else {
			Player currentPlayer = this.players.get(currentPlayerNumber);
			//For now, standard is give two cards and ask one back. This should be updated to also
			//handle the special cards.
			int numberToDraw = 2;
			int numberToChoose = 1;
			Set<Card> cardsToChoose = this.cardDeck.drawCard(numberToDraw);
			currentPlayer.setCardsToChoose(cardsToChoose);
			Set<Card> chosenCards = null;
			boolean validReturn = false;
			while (!validReturn) {
				chosenCards = currentPlayer.getChosenCard();
				if (chosenCards != null) {
					if (chosenCards.size() == numberToChoose) {
						if (cardsToChoose.containsAll(chosenCards)) {
							validReturn = true;
						}
					}
				}
			}
			this.addCards(currentPlayerNumber, chosenCards);
		}
	}
	
	/**
	 * This method asks for a CharMove object at the current player. It checks the validity of the
	 * CharMove object and executes the move.
	 * 
	 * @param currentPlayerNumber	An integer representing the current active player.
	 */
	private void getCharMove(int currentPlayerNumber) {
		Player currentPlayer = this.players.get(currentPlayerNumber);
		CharMove charMove = null;
		while (!this.isValidCharMove(currentPlayerNumber, charMove)) {
			charMove = currentPlayer.getCharMove();
		}
		this.handleCharMove(currentPlayerNumber, charMove);
	}
	
	/**
	 * 
	 * @param playerNumber
	 * @param move
	 */
	private void handleCharMove(int playerNumber, CharMove move) {
		
	}
	
	/**
	 * 
	 * @param playerNumber
	 * @param move
	 * @return
	 */
	private boolean isValidCharMove(int playerNumber, CharMove move) {
		return true;
	}
	
	/**
	 * This method handles the stealing action. It executes the following actions:
	 *  - Check if there is a stealing action available: this.stolenPlayer == currentPlayer
	 *  - Adjust the number of coins of both the stolen and the stealing players
	 *  - Reset the stealing and stolen characters
	 *  
	 *  @param currentPlayer	The player to be checked for the stealing action.
	 */
	private void handleStolen(int currentPlayer) {
		if (this.stolenPlayer == currentPlayer) {
			for (Player otherPlayers : this.players) {
				otherPlayers.setStolen(this.stolenPlayer, this.thiefPlayer);
			}
			this.setCoins(this.thiefPlayer, this.getCoins(this.stolenPlayer) + this.getCoins(this.thiefPlayer));
			this.setCoins(this.stolenPlayer, 0);
			this.stolenPlayer = -1;
			this.thiefPlayer = -1;
		}
	}
	
	/**
	 * This method returns the number of coins the player represented by playerNumber has.
	 * 
	 * @param playerNumber	The player for which the number of coins have to be returned.
	 * @return				An integer representing the number of coins this player has.
	 */
	private int getCoins(int playerNumber) {
		return this.coins.get(playerNumber);
	}
	
	/**
	 * Set the number of coins of the given player represented by playerNumber. This method also
	 * notifies all players about the change of number of coins.
	 * 
	 * @param playerNumber	The player that has a change of number of coins.
	 * @param coins			The number of coins this player has now.
	 */
	private void setCoins(int playerNumber, int coins) {
		this.coins.put(playerNumber, coins);
		for (Player player : this.players) {
			player.setCoins(playerNumber, coins);
		}
	}
	
	private void initializeCoins(int initCoins) {
		for (int i = 0; i < this.numberPlayers; i++) {
			this.setCoins(i, initCoins);
		}
	}
	
	private HashMap<Integer, Integer> coins;
	
	private void initializeCards(int numberCards) {
		for (int i = 0; i < this.numberPlayers; i++) {
			this.addCards(i, this.cardDeck.drawCard(numberCards));
		}
	}
	
	/**
	 * Add the given set of cards to this player's set of cards. This method will also notify all
	 * other players about the change of number of cards with the method notifyPlayersCards().
	 * 
	 * @param playerNumber	A number representing the player who now owns the given set of cards.
	 * @param cards			A set of cards to add to the given player.
	 */
	private void addCards(int playerNumber, Set<Card> cards) {
		Set<Card> oldCards = this.cards.get(playerNumber);
		//Make sure that cards that look the same are still different objects:
		oldCards.addAll(cards);
		this.cards.put(playerNumber, oldCards);
		this.notifyPlayersCards(playerNumber);
	}
	
	/**
	 * This method will notify all players about the number of cards the player represented by
	 * playerNumber has. This will be done by invoking the method setCards(int, int). This method
	 * will also invoke the method setCards(Set<Card>) to the player represented by playerNumber to
	 * update his current cards.
	 * 
	 * @param playerNumber	An integer representing the player for which the other players should be
	 * 						notified.
	 */
	private void notifyPlayersCards(int playerNumber) {
		int numberCards = this.getNumberCards(playerNumber);
		for (Player otherPlayer : this.players) {
			otherPlayer.setCards(playerNumber, numberCards);
		}
		this.players.get(playerNumber).setCards(this.getCards(playerNumber));
	}
	
	/**
	 * This method returns the number of cards the given player has.
	 * 
	 * @param playerNumber	An integer representing the player for who the number of cards will be
	 * 						returned.
	 * 
	 * @return				An integer containing the number of cards the given player has.
	 */
	private int getNumberCards(int playerNumber) {
		return this.cards.get(playerNumber).size();
	}
	
	/**
	 * This method returns the set of cards the given player owns.
	 * 
	 * @param playerNumber	An integer representing the player for who the owned set of cards should
	 * 						be returned.
	 * 
	 * @return				A set of cards ownedby the given player.
	 */
	private Set<Card> getCards(int playerNumber) {
		return this.cards.get(playerNumber);
	}
	
	/**
	 * This map contains the sets of cards that each player owns.
	 */
	private HashMap<Integer, Set<Card>> cards;
	
	/**
	 * This method will check if the given player already has built the given build card.
	 * 
	 * @param playerNumber	An integer representing the player for who this method will check if the
	 * 						given build card is already present in his city.
	 * @param buildCard		The build card that will be checked in the given player's city.
	 * @return
	 */
	private boolean alreadyBuilded(int playerNumber, Card buildCard) {
		return this.city.get(playerNumber).contains(buildCard);
	}
	
	/**
	 * This method will add the given set of build cards to the city of the given player. It will 
	 * also notify all other players about the changed city of the given player.
	 * 
	 * @param playerNumber	An integer representing the player for who the given set of cards will
	 * 						be added to his city.
	 * @param build			A set of build cards that will be added to the city of the given player.
	 */
	private void addToCity(int playerNumber, Set<Card> build) {
		Set<Card> oldCards = this.city.get(playerNumber);
		oldCards.addAll(build);
		this.city.put(playerNumber, oldCards);
		this.notifyPlayersCity(playerNumber);
	}
	
	/**
	 * This method will notify all players about the changed city of the given player. It will 
	 * invoke the method setCity() to all players.
	 * 
	 * @param playerNumber	An integer representing the player for who all other player will be
	 * 						notified about his city.
	 */
	private void notifyPlayersCity(int playerNumber) {
		Set<Card> city = this.city.get(playerNumber);
		for (Player player : this.players) {
			player.setCity(playerNumber, city);
		}
	}
	
	/**
	 * This method will return the number of buildings the given player has in his city.
	 * 
	 * @param playerNumber	An integer representing the player for who the number of buildings 
	 * 						should be returned.
	 * 
	 * @return				An integer representing the number of buildings the given player has in
	 * 						his city.
	 */
	private int getNumberBuildings(int playerNumber) {
		return this.city.get(playerNumber).size();
	}
	
	/**
	 * Initialize the city of all players with an empty city.
	 */
	private void initializeCity() {
		for (int i = 0; i < this.numberPlayers; i++) {
			this.city.put(i, new HashSet<Card>(8));
		}
	}
	
	/**
	 * This map contains a set of buildings this player has alread built.
	 */
	private HashMap<Integer, Set<Card>> city;
	
	/**
	 * Prepare this game to start the first round:
	 *  - Provide the player the number of players in this game
	 *  - Provide each player a unique player number
	 */
	private void initializeGame() {
		this.provideNumberPlayers();
		this.providePlayerNumber();
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
	 *  - Reset the available characters
	 *  - Provide the players the information who will be the king player for the next round
	 *  - Provide the players the information of the open characters (if any)
	 *  - Provide the king player with the information of the top character
	 *  - Provide each player with a set of characters, from which he can choose one or put one away
	 *    in case numberPlayers == 2
	 *  - Obtain the characters chosen by each player
	 *  - Obtain the characters put away by each player, in case numberPlayers == 2
	 */
	private void distributeChars() {
		this.characterDeck.resetAvailableChars();
		this.provideKing();
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
		this.initializeChosenChars();
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
