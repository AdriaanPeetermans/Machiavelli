package game.player;

import java.util.Map;
import java.util.Set;

import game.helpers.Card;
import game.helpers.Character;
import game.helpers.Revenue;
import game.helpers.charMove.CharMove;

public interface Player {
	
	/**
	 * Provide the player with the information of the number of players playing in the game.
	 * 
	 * @param numberPlayers	An integer representing the number of players in this game.
	 */
	public void setNumberPlayers(int numberPlayers);
	
	/**
	 * Every player has a unique number ranging from zero to numberPlayers-1. From this number,
	 * the player knows its order of choosing a character.
	 * 
	 * @param number	An integer representing the number assigned to this player.
	 */
	public void setPlayerNumber(int number);
	
	/**
	 * Provide the player with the information which player is assigned king in the round that will
	 * follow.
	 * 
	 * @param number	An integer representing the king for the next round.
	 */
	public void setKing(int number);
	
	/**
	 * Provide the player a set of characters that are open on the table and cannot be chosen by
	 * any player.
	 * 
	 * @param characters	A set containing all characters that cannot be chosen.
	 */
	public void setOpenChars(Set<Character> characters);
	
	/**
	 * Provide the king with the information which character was on the top of the shuffled
	 * character card deck. This method will only be invoked for the king of the next round. Other
	 * players will not have this information. This character cannot be chosen by any player.
	 * Note: in case numberPlayers == 7, the last player can still choose the given character.
	 * 
	 * @param character	The character which was on top of the character card deck.
	 */
	public void setKingChar(Character character);
	
	/**
	 * Provide the player with a set of characters from which he can choose one for its own use
	 * during the next round. The method getChosenChar() will be called to get the player
	 * selection. If numberPlayers == 2, the player will also have to choose one character to put
	 * away without using it. This will happen via invocation of the method getPutAwayChar(). If
	 * this is the first selection of the king in a game with 2 players, the king cannot put away
	 * a character and the getPutAwayChar() method will not be invoked.
	 * 
	 * @param characters	A set of characters from which this player can choose one.
	 */
	public void setCharsToChoose(Set<Character> characters);
	
	/**
	 * Ask this player for a character to choose from the set of characters acquired via the method
	 * setCharsToChoose(). This method will always be invoked after the method setCharsToChoose().
	 * If the player returns an illegal character (not in the given set), this method will be
	 * invoked again.
	 * 
	 * @return	A single character chosen by this player to play in the next round.
	 */
	public Character getChosenChar();
	
	/**
	 * Ask this player for a character to put away and not to be used by any player. The player can
	 * choose from the acquired set of characters via the method setCharsToChoose() minus the
	 * character that was chosen by the player via the method getChosenChar(). This method will
	 * only be called after a sequence of the methods setCharsToChoose() and getChosenChar() in
	 * that order and only in a game of two players.
	 * If the player returns an illegal character (not in the given set or already chosen), this
	 * method will be invoked again.
	 * 
	 * @return
	 */
	public Character getPutAwayChar();
	
	/**
	 * This method notifies all player which is the current character and the player, who picked this
	 * character. All players will be notified, also the current active player.
	 * 
	 * @param character		The character that is chosen by the current player.
	 * @param playerNumber	An integer representing the current active player.
	 */
	public void setActiveChar(Character character, int playerNumber);
	
	/**
	 * Notify all players that the current active player gets stolen by the thief. The stolenPlayer
	 * will be the current active player. This method will be followed by an invocation of the
	 * methods setCoins(stolenPlayer, coins) and setCoins(thiefPlayer, coins).
	 * 
	 * @param stolenPlayer	The player that looses all its coins at the beginning of his turn.
	 * @param thiefPlayer	The player that steals the coins.
	 */
	public void setStolen(int stolenPlayer, int thiefPlayer);
	
	/**
	 * Notify all players about the number of coins a player represented by playerNumber has. This
	 * method can be invoked multiple times during a player's turn.
	 * 
	 * @param playerNumber	The player to which the number of coins are related to.
	 * @param coins			The number of coins.
	 */
	public void setCoins(int playerNumber, int coins);
	
	/**
	 * Notify all players about the special character move that has been chosen by the current 
	 * active player. All players, including the current active player will be notified by this
	 * method.
	 * 
	 * @param activePlayer	The current active player that plays the given move.
	 * @param charMove		The move object played by the current active player.
	 */
	public void setCharMove(int activePlayer, CharMove charMove);
	
	/**
	 * Provide the current active player with a set of cards, from which he can choose one. Or
	 * multiple if the player has special cards. This method will only be invoked if the player
	 * chooses for cards as a revenue. The player will be able to give his choice via the method
	 * getChosenCards().
	 * 
	 * @param cards	A set of cards from which the player can choose one or multiple.
	 */
	public void setCardsToChoose(Set<Card> cards);
	
	/**
	 * This method notifies all players about the number of cards the player represented by
	 * playerNumber has. This method will be invoked if the number of cards of a certain player
	 * changes. This method can be called multiple times during a player's turn.
	 * 
	 * @param playerNumber	The player which number of cards are updated.
	 * @param numberCards	The number of cards the given player has.
	 */
	public void setCards(int playerNumber, int numberCards);
	
	/**
	 * This method will be called to one player at a time and gives the player the current status
	 * of the cards he holds in his hand. This method can be called multiple times during a player's
	 * round and also to non active players (magier). This method always reflects a change of cards
	 * for this player.
	 * 
	 * @param cards	A set of cards representing the current player's hand.
	 */
	public void setCards(Set<Card> cards);
	
	/**
	 * Notifies all player about the current city of the player represented by playerNumber. This
	 * method will by invoked to all players when one player has a change of number of buildings.
	 * 
	 * @param playerNumber	The playerNumber which has this set of buildings.
	 * @param cards			The current city of the player represented by playerNumber.
	 */
	public void setCity(int playerNumber, Set<Card> cards);
	
	/**
	 * This method will be called to all players at the end of the game to distribute the score of
	 * each individual player.
	 * 
	 * @param points	A map containing a key-value pair for each player. The key represents the
	 * 					playerNumber and the value the number of points.
	 */
	public void setPoints(Map<Integer, Integer> points);
	
	/**
	 * Notify all players at the end of the game which player has won this game.
	 * 
	 * @param playerNumber	An integer representing the player who has won this game.
	 */
	public void setWinner(int playerNumber);
	
	/**
	 * Ask the current active player what will be his move related to his chosen character. This
	 * method will be invoked three times during a player's turn. A player has to return a CharMove
	 * object once, and the null object twice. If the player does not do this, this method will be
	 * invoked again.
	 * 
	 * @return	A CharMove object containing all information about the chosen move of the active
	 * 			player.
	 */
	public CharMove getCharMove();
	
	/**
	 * Ask the current player which type of revenue he wants in this round. The player can choose
	 * between two coins or two cards, from which he can pick only one. This method will only be
	 * invoked at the current active player.
	 * 
	 * @return	A Revenue object representing the choice of the current active player.
	 */
	public Revenue getRevenue();
	
	/**
	 * This method will be invoked only to the current active player if he has chosen for cards as
	 * a revenue. The returned set should be a subset of the set obtained by an invocation of the
	 * method setCardsToChoose().If the player returns a wrong set, this method will be called
	 * again.
	 * 
	 * @return	A set of cards the current active player wants to keep in his hand.
	 */
	public Set<Card> getChosenCard();
	
	/**
	 * This method will only be called at the current active player. It asks the player for which
	 * buildings he wants to build during this round. If the player wants to build no building, the
	 * null object should be returned. The returned set should only contain one card that was 
	 * previously in the current player's hand. Only if this player has chosen the character
	 * bouwmeester, he can build two additional cards during this round. If the player returns a
	 * wrong set of cards, this method will be invoked again. If the player cannot pay for this set,
	 * this method will also be called again.
	 * 
	 * @return	A set of cards this player wants to build during this round.
	 */
	public Set<Card> getBuild();
}
