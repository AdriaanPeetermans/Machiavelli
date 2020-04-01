package game.helpers;

import java.util.Set;

public class CharacterDeck {
	
	public CharacterDeck(Set<Character> characters) {
		if (characters == null) {
			this.characters = this.initializeCharacters();
		}
		else {
			this.characters = characters;
		}
	}
	
	private Set<Character> initializeCharacters() {
		
	}
	
	private Set<Character> characters;
}
