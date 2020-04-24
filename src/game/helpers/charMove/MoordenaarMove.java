package game.helpers.charMove;

import game.engine.Engine;
import game.helpers.Character;

public class MoordenaarMove extends CharMove {
	
	/**
	 * This constructor generates a new MOORDENAAR move.
	 * 
	 * @param killedChar	The character this player chooses to kill.
	 */
	public MoordenaarMove(Character killedChar) {
		super(Character.MOORDENAAR);
		this.killedChar = killedChar;
	}
	
	public Character getKilledChar() {
		return this.killedChar;
	}

	private final Character killedChar;
	
	public boolean isValid(Engine engine, int currentPlayerNumber) {
		return !this.killedChar.equals(Character.MOORDENAAR);
	}

	@Override
	public void execute(Engine engine, int currentPlayerNumber) {
		engine.setKilledChar(this.killedChar);
	}

}
