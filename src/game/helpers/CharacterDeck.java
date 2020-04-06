package game.helpers;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class CharacterDeck implements Iterable<Character> {
	
	/**
	 * Create a new instance of the CharacterDeck class. This object enables to iterate over the
	 * known characters in the order given by the character number. It will also enable to pop new
	 * characters from a set of not yet chosen characters. The set of available characters will be
	 * populated by the given deck.
	 * 
	 * @param characters	A set of characters to initialize this deck. Provide the null object 
	 * 						to initialize with the standard deck of 8 characters.
	 */
	public CharacterDeck(Set<Character> characters) {
		if (characters == null) {
			this.characters = this.initializeCharacters();
		}
		else {
			this.characters = characters;
		}
		this.resetAvailableChars();
	}
	
	/**
	 * This method returns a set of characters containing the standard 8 Machiavelli characters.
	 * 
	 * @return A set containing the 8 characters.
	 */
	private Set<Character> initializeCharacters() {
		return EnumSet.allOf(Character.class);
	}
	
	private Set<Character> characters;
	
	/**
	 * Provide a characterIterator to iterate over this deck of characters.
	 * 
	 * @return	A CharacterIterator instance to iterate in proper order over this deck of
	 * 			characters.
	 */
	public CharacterIterator iterator() {
		return new CharacterIterator(this.characters);
	}
	
	/**
	 * Generate a random character from the set of characters that have not yet been chosen.
	 * 
	 * @return	A random character that has not yet been chosen.
	 */
	public Character popChar() {
		int item = new Random().nextInt(this.availableChars.size());
		int i = 0;
		for (Character character : this.availableChars) {
			if (i == item) {
				this.availableChars.remove(character);
				return character;
			}
			i ++;
		}
		return null;
	}
	
	/**
	 * Refresh the available character, such that all characters can again be chosen.
	 */
	public void resetAvailableChars() {
		this.availableChars = new HashSet<Character>(8);
		for (Character character : this) {
			this.availableChars.add(character);
		}
	}
	
	/**
	 * This method returns a set containing all characters that have not yet been chosen.
	 * 
	 * @return	A HashSet instance containing all characters ready to choose.
	 */
	public HashSet<Character> getAvailableChars() {
		HashSet<Character> result = new HashSet<Character>(this.availableChars.size());
		for (Character character : this.availableChars) {
			result.add(character);
		}
		return result;
	}
	
	/**
	 * Remove the given character from the list of available characters.
	 * 
	 * @param character	The character that has to be removed from the set of available characters.
	 */
	public void removeAvailableChar(Character character) {
		this.availableChars.remove(character);
	}
	
	/**
	 * Keep track of which characters have already been chosen by the players.
	 */
	private HashSet<Character> availableChars;
	
	/**
	 * This class provides an implementation of the java.util.Iterator interface, to iterate
	 * over the contents of the given set of characters.
	 */
	public class CharacterIterator implements Iterator<Character> {
		
		public CharacterIterator(Set<Character> deck) {
			this.currentChar = 0;
			this.deck = deck;
		}
		
		private final Set<Character> deck;
		
		private int currentChar;
		
		@Override
		public boolean hasNext() {
			for (Character character : this.deck) {
				if (character.number > this.currentChar) {
					return true;
				}
			}
			return false;
		}

		@Override
		public Character next() {
			Character result = null;
			int smallest = Integer.MAX_VALUE;
			for (Character character : this.deck) {
				if ((character.number > this.currentChar) && (character.number < smallest)) {
					smallest = character.number;
					result = character;
				}
			}
			this.currentChar = result.number;
			return result;
		}
	}
}
