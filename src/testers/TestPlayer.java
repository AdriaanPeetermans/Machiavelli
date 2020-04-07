package testers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import game.player.Player;
import game.helpers.Character;

public class TestPlayer implements Player {
	
	private int number = -1;
	
	private void printMessage(String message) {
		System.out.println("Player ".concat(Integer.toString(this.number)).concat(": ").concat(message));
	}
	
	public String toString() {
		return "player ".concat(Integer.toString(this.number));
	}

	@Override
	public void setNumberPlayers(int numberPlayers) {
		this.printMessage("number players: ".concat(Integer.toString(numberPlayers)));
	}

	@Override
	public void setPlayerNumber(int number) {
		this.number = number;
		this.printMessage("own number: ".concat(Integer.toString(number)));
	}

	@Override
	public void setKing(int number) {
		this.printMessage("king number: ".concat(Integer.toString(number)));
	}

	@Override
	public void setOpenChars(Set<Character> characters) {
		String message = "open characters:\r\n";
		for (Character character : characters) {
			message = message.concat("\t".concat(character.toString().concat("\r\n")));
		}
		this.printMessage(message);
	}

	@Override
	public void setKingChar(Character character) {
		this.printMessage("first character on deck: ".concat(character.toString()));
	}

	@Override
	public void setCharsToChoose(Set<Character> characters) {
		String message = "characters to choose:\r\n";
		for (Character character : characters) {
			message = message.concat("\t").concat(character.toString()).concat("\r\n");
		}
		this.printMessage(message);
	}

	@Override
	public Character getChosenChar() {
		this.printMessage("input character to choose:");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String charString = null;
		try {
			charString = reader.readLine();
		} catch (IOException e) {
			this.printMessage("a fatal error occured!");
			e.printStackTrace();
		}
		switch (charString) {
		case "1":
			this.printMessage("chosen character: ".concat(Character.MOORDENAAR.toString()));
			return Character.MOORDENAAR;
		case "2":
			this.printMessage("chosen character: ".concat(Character.DIEF.toString()));
			return Character.DIEF;
		case "3":
			this.printMessage("chosen character: ".concat(Character.MAGIER.toString()));
			return Character.MAGIER;
		case "4":
			this.printMessage("chosen character: ".concat(Character.KONING.toString()));
			return Character.KONING;
		case "5":
			this.printMessage("chosen character: ".concat(Character.PREDIKER.toString()));
			return Character.PREDIKER;
		case "6":
			this.printMessage("chosen character: ".concat(Character.KOOPMAN.toString()));
			return Character.KOOPMAN;
		case "7":
			this.printMessage("chosen character: ".concat(Character.BOUWMEESTER.toString()));
			return Character.BOUWMEESTER;
		case "8":
			this.printMessage("chosen character: ".concat(Character.CONDOTTIERE.toString()));
			return Character.CONDOTTIERE;
		default:
			return null;
		}
	}

	@Override
	public Character getPutAwayChar() {
		this.printMessage("input character to put away:");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String charString = null;
		try {
			charString = reader.readLine();
		} catch (IOException e) {
			this.printMessage("a fatal error occured!");
			e.printStackTrace();
		}
		switch (charString) {
		case "1":
			this.printMessage("chosen character: ".concat(Character.MOORDENAAR.toString()));
			return Character.MOORDENAAR;
		case "2":
			this.printMessage("chosen character: ".concat(Character.DIEF.toString()));
			return Character.DIEF;
		case "3":
			this.printMessage("chosen character: ".concat(Character.MAGIER.toString()));
			return Character.MAGIER;
		case "4":
			this.printMessage("chosen character: ".concat(Character.KONING.toString()));
			return Character.KONING;
		case "5":
			this.printMessage("chosen character: ".concat(Character.PREDIKER.toString()));
			return Character.PREDIKER;
		case "6":
			this.printMessage("chosen character: ".concat(Character.KOOPMAN.toString()));
			return Character.KOOPMAN;
		case "7":
			this.printMessage("chosen character: ".concat(Character.BOUWMEESTER.toString()));
			return Character.BOUWMEESTER;
		case "8":
			this.printMessage("chosen character: ".concat(Character.CONDOTTIERE.toString()));
			return Character.CONDOTTIERE;
		default:
			return null;
		}
	}

}
