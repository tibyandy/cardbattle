package cardbattle.player;

import static cardbattle.common.CardBattleError.PLAYER_NAME_ALREADY_IN_USE;
import static cardbattle.common.CardBattleException.error;

import java.util.HashMap;
import java.util.Map;

import cardbattle.common.CardBattleException;

public class PlayerManager {

	/* Singleton */
	private static PlayerManager instance;
	private PlayerManager() {}

	public static PlayerManager getInstance() {
		if (instance == null) {
			instance = new PlayerManager();
		}
		return instance;
	}

	private final Map<String, Player> players = new HashMap<>();

	public void join(String playerName) throws CardBattleException {
		playerName = playerName.toUpperCase();
		if (players.containsKey(playerName)) {
			throw error(PLAYER_NAME_ALREADY_IN_USE, playerName);
		}
		players.put(playerName, new Player(playerName));
	}
}
