package testers;

import java.util.HashSet;
import java.util.Set;

import game.engine.Engine;
import game.player.Player;

public class TB_Engine {

	public static void main(String[] args) {
		System.out.println("Test player shuffling:");
		Set<Player> players = new HashSet<Player>(4);
		for (int i = 0; i < 4; i++) {
			players.add(new TestPlayer(i));
		}
		Engine engine = new Engine(players, null, null);
		System.out.println(engine.players);
	}

}
