package game.helpers;

public class Card {
	
	public Card(int cost, int points, CardColor color, String name) {
		this.cost = cost;
		this.points = points;
		this.color = color;
		this.name = name;
	}
	
	public final int cost;
	
	public final int points;
	
	public final CardColor color;
	
	public final String name;
}
