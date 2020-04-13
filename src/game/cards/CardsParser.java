package game.cards;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import game.helpers.Card;
import game.helpers.CardColor;

public class CardsParser {
	
	public CardsParser(String fileName) {
		this.fileName = fileName;
	}
	
	private final String fileName;
	
	/**
	 * This method returns a set of all cards available in the database, according the correct
	 * multitudes.
	 * 
	 * @return	A set of cards that is available in the database.
	 */
	public Set<Card> getCards() {
		List<String> lines = this.getLines();
		int numberCards = lines.size()/7;
		Set<Card> result = new HashSet<Card>();
		for (int i = 0; i < numberCards; i++) {
			String name = lines.get(i*7);
			int points = Integer.parseInt(lines.get(i*7+1));
			int cost = Integer.parseInt(lines.get(i*7+2));
			CardColor color = CardColor.getColor(lines.get(i*7+3));
			String text = lines.get(i*7+4);
			int mult = Integer.parseInt(lines.get(i*7+5));
			for (int j = 0; j < mult; j++) {
				result.add(new Card(cost, points, color, name, text));
			}
		}
		return result;
	}
	
	private List<String> getLines() {
		File file = new File(this.fileName);
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ArrayList<String> result = new ArrayList<String>();
		while (scanner.hasNextLine()) {
			result.add(scanner.nextLine());
		}
		scanner.close();
		return result;
	}

}
