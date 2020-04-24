package game.helpers.charMove;

import game.engine.Engine;
import game.helpers.Character;

public class DiefMove extends CharMove {
	
	/**
	 * This constructor generates a new DIEF move.
	 * 
	 * @param stolenChar	The character this player wants to steal from.
	 */
	public DiefMove(Character stolenChar) {
		super(Character.DIEF);
		this.stolenChar = stolenChar;
	}
	
	public Character getStolenChar() {
		return this.stolenChar;
	}
	
	private final Character stolenChar;

	@Override
	public boolean isValid(Engine engine, int currentPlayerNumber) {
		if (this.stolenChar.equals(engine.getKilledChar())) {
			return false;
		}
		if (this.stolenChar.equals(Character.MOORDENAAR)) {
			return false;
		}
		if (this.stolenChar.equals(Character.DIEF)) {
			return false;
		}
		return true;
	}

	@Override
	public void execute(Engine engine, int currentPlayerNumber) {
		engine.setThiefPlayer(currentPlayerNumber);
		engine.setStolenPlayer(this.stolenChar, currentPlayerNumber);
	}

}
