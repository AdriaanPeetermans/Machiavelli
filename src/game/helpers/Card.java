package game.helpers;

public class Card {
	
	public Card(int cost, int points, CardColor color, String name, String text) {
		this.cost = cost;
		this.points = points;
		this.color = color;
		this.name = name;
		this.text = text;
	}
	
	public final int cost;
	
	public final int points;
	
	public final CardColor color;
	
	public final String name;
	
	public final String text;
	
	public String toString() {
		String result = "Card: cost=".concat(Integer.toString(this.cost));
		result = result.concat(" points=").concat(Integer.toString(this.points));
		result = result.concat(" color=").concat(this.color.toString());
		result = result.concat(" name=").concat(this.name);
		result = result.concat(" text=").concat(this.text);
		return result;
	}
}
