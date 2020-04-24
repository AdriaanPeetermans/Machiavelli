package game.helpers.charMove;

import game.engine.Engine;
import game.helpers.Character;

public abstract class CharMove {
	
	public CharMove(Character type) {
		this.type = type;
	}
	
	public final Character type;
	
	/**
	 * This method will check if this CharMove object represents a valid character move for the
	 * game situation.
	 * 
	 * @param engine				The current game engine that will check validity.
	 * @param currentPlayerNumber	The current active player who wants to play this move.
	 * 
	 * @return						A boolean stating if this CharMove object is valid.
	 */
	public abstract boolean isValid(Engine engine, int currentPlayerNumber);
	
	/**
	 * This method will execute this CharMove.
	 * 
	 * @param engine				The current game engine that will check validity.
	 * @param currentPlayerNumber	The current active player who wants to play this move.
	 */
	public abstract void execute(Engine engine, int currentPlayerNumber);

}
