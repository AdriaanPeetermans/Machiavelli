package game.helpers;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

public class CharacterDeck implements Iterable<Character> {
	
	public CharacterDeck(Set<Character> characters) {
		if (characters == null) {
			this.characters = this.initializeCharacters();
		}
		else {
			this.characters = characters;
		}
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
