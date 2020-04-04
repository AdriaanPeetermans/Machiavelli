package testers;

import java.util.Set;

import game.player.Player;

public class TestPlayer implements Player {
	
	public TestPlayer(int number) {
		this.number = number;
	}
	
	private final int number;
	
	public String toString() {
		return "player ".concat(Integer.toString(this.number));
	}

	@Override
	public void setNumberPlayers(int numberPlayers) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPlayerNumber(int number) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setKing(int number) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOpenChars(Set<Character> characters) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setKingChar(Character character) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCharsToChoose(Set<Character> characters) {
		// TODO Auto-generated method stub

	}

	@Override
	public Character getChosenChar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Character getPutAwayChar() {
		// TODO Auto-generated method stub
		return null;
	}

}
