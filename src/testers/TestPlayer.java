package testers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import game.player.Player;
import game.helpers.Card;
import game.helpers.Character;
import game.helpers.Revenue;
import game.helpers.charMove.CharMove;

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

	@Override
	public void setActiveChar(Character character, int playerNumber) {
		this.printMessage("active character: ".concat(character.toString()).concat(", player: ").concat(Integer.toString(playerNumber)));
	}

	@Override
	public void setStolen(int stolenPlayer, int thiefPlayer) {
		this.printMessage("stolen: ".concat(Integer.toString(thiefPlayer)).concat(" from ").concat(Integer.toString(stolenPlayer)));
	}

	@Override
	public void setCoins(int playerNumber, int coins) {
		this.printMessage("set coins: ".concat(Integer.toString(coins)).concat(" for player ").concat(Integer.toString(playerNumber)));
		if (playerNumber == this.number) {
			this.mycoins = coins;
		}
	}
	
	private int mycoins;

	@Override
	public void setCharMove(int activePlayer, CharMove charMove) {
		this.printMessage("charmove for player ".concat(Integer.toString(activePlayer)));
	}

	@Override
	public void setCardsToChoose(Set<Card> cards) {
		String message = "cards to choose:\r\n";
		this.cardsToChoose = new ArrayList<Card>(cards.size());
		for (Card card : cards) {
			message = message.concat(card.toString()).concat("\r\n");
			this.cardsToChoose.add(card);
		}
		this.printMessage(message);
	}
	
	private ArrayList<Card> cardsToChoose;

	@Override
	public void setCards(int playerNumber, int numberCards) {
		this.printMessage("player ".concat(Integer.toString(playerNumber)).concat(" has ").concat(Integer.toString(numberCards)).concat(" cards"));
	}

	@Override
	public void setCards(Set<Card> cards) {
		String message = "I have these cards:\r\n";
		this.myCards = new ArrayList<Card>(cards.size());
		for (Card card : cards) {
			message = message.concat(card.toString()).concat("\r\n");
			this.myCards.add(card);
		}
		this.printMessage(message);
	}
	
	private ArrayList<Card> myCards;

	@Override
	public void setCity(int playerNumber, Set<Card> cards) {
		String message = "player ".concat(Integer.toString(playerNumber)).concat(" has these buildings:\r\n");
		for (Card card : cards) {
			message = message.concat(card.toString()).concat("\r\n");
		}
		this.printMessage(message);
	}

	@Override
	public void setPoints(Map<Integer, Integer> points) {
		String message = "points:\r\n";
		for (int player : points.keySet()) {
			message = message.concat("player ").concat(Integer.toString(player)).concat(": ").concat(Integer.toString(points.get(player)));
		}
		this.printMessage(message);
	}

	@Override
	public void setWinner(int playerNumber) {
		this.printMessage("winner: ".concat(Integer.toString(playerNumber)));
	}

	@Override
	public CharMove getCharMove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Revenue getRevenue() {
		this.printMessage("input revenue (1=coins, 2=cards):");
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
			return new Revenue(true);
		default:
			return new Revenue(false);
		}
	}

	@Override
	public Set<Card> getChosenCard() {
		String message = "choose from these cards:\r\n";
		for (int i = 0; i < this.cardsToChoose.size(); i++) {
			message = message.concat(Integer.toString(i)).concat(": ").concat(this.cardsToChoose.get(i).toString()).concat("\r\n");
		}
		this.printMessage(message);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String charString = null;
		try {
			charString = reader.readLine();
		} catch (IOException e) {
			this.printMessage("a fatal error occured!");
			e.printStackTrace();
		}
		HashSet<Card> result = new HashSet<Card>(1);
		result.add(this.cardsToChoose.get(Integer.parseInt(charString)));
		return result;
	}

	@Override
	public Set<Card> getBuild() {
		String message = "choose from these cards to build (-1 if no build), coins: ".concat(Integer.toString(this.mycoins)).concat(":\r\n");
		for (int i = 0; i < this.myCards.size(); i++) {
			message = message.concat(Integer.toString(i)).concat(": ").concat(this.myCards.get(i).toString()).concat("\r\n");
		}
		this.printMessage(message);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String charString = null;
		try {
			charString = reader.readLine();
		} catch (IOException e) {
			this.printMessage("a fatal error occured!");
			e.printStackTrace();
		}
		HashSet<Card> result = new HashSet<Card>(1);
		if (charString.equals("-1")) {
			return result;
		}
		result.add(this.myCards.get(Integer.parseInt(charString)));
		return result;
	}

}
