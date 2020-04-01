package game.helpers;

public enum Character {
	
	MOORDENAAR(1, "Moordenaar"),
	DIEF(2, "Dief"),
	MAGIER(3, "Magiër"),
	KONING(4, "Koning"),
	PREDIKER(5, "Prediker"),
	KOOPMAN(6, "Koopman"),
	BOUWMEESTER(7, "Bouwmeester"),
	CONDOTTIERE(8, "Condottiere");
	
	Character(int number, String name) {
		this.number = number;
		this.name = name;
	}
	
	public final int number;
	
	public final String name;
}
