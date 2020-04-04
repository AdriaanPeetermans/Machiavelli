package game.player;

import java.util.Set;

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
	 * follow. This method will only be used at the start of the game before the first round.
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
}
