package game.helpers.charMove;

import java.util.Set;

import game.engine.Engine;
import game.helpers.Card;
import game.helpers.CardColor;
import game.helpers.Character;

public class KoningMove extends CharMove {

	public KoningMove(Set<Card> coinBuildings) {
		super(Character.KONING);
		this.coinBuildings = coinBuildings;
	}
	
	private final Set<Card> coinBuildings;

	@Override
	public boolean isValid(Engine engine, int currentPlayerNumber) {
		for (Card building : this.coinBuildings) {
			if (engine.alreadyAskedBuildingCoin(building)) {
				return false;
			}
			if (!building.color.equals(CardColor.YELLOW)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void execute(Engine engine, int currentPlayerNumber) {
		engine.setKingPlayer(currentPlayerNumber);
		for (Card building : this.coinBuildings) {
			engine.addAskedBuildingCoin(building);
		}
		engine.addCoins(currentPlayerNumber, this.coinBuildings.size());
	}
}
