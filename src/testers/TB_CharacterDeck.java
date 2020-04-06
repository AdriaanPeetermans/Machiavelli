package testers;

import game.helpers.CharacterDeck;

import java.util.HashSet;

import game.helpers.Character;

public class TB_CharacterDeck {

	public static void main(String[] args) {
		System.out.println("Test 0: all characters");
		CharacterDeck deck0 = new CharacterDeck(null);
		for (Character character : deck0) {
			System.out.println(character);
		}
		
		System.out.println(" ");
		
		System.out.println("Test 1: some characters");
		HashSet<Character> chars = new HashSet<Character>(3);
		chars.add(Character.BOUWMEESTER);
		chars.add(Character.MAGIER);
		chars.add(Character.CONDOTTIERE);
		CharacterDeck deck1 = new CharacterDeck(chars);
		for (Character character : deck1) {
			System.out.println(character);
		}
		
		System.out.println(" ");
		System.out.println("Test 2: available characters");
		CharacterDeck deck2 = new CharacterDeck(null);
		for (int i = 0; i < 4; i++) {
			System.out.println(deck2.popChar());
		}
		deck2.resetAvailableChars();
		for (int i = 0; i < 4; i++) {
			System.out.println(deck2.popChar());
		}
	}
}
