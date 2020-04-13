package game.helpers;

public enum CardColor {
	
	YELLOW("Yellow"),
	BLUE("Blue"),
	GREEN("Green"),
	RED("Red"),
	PURPLE("Purple");
	
	CardColor(String name) {
		this.name = name;
	}
	
	private final String name;
	
	public String toString() {
		return this.name;
	}
	
	public static CardColor getColor(String name) {
		switch (name) {
		case "YELLOW":
			return CardColor.YELLOW;
		case "BLUE":
			return CardColor.BLUE;
		case "GREEN":
			return CardColor.GREEN;
		case "RED":
			return CardColor.RED;
		case "PURPLE":
			return CardColor.PURPLE;
		}
		return null;
	}
	
	
}
