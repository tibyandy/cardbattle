package neocardbattle;

import java.util.HashMap;
import java.util.Map;

public class BattlesManager {

	private final Map<String, Battle> battlesByPlayerName = new HashMap<>();
	
	public Battle create(Player challenger, Player opponent) {
		Battle newBattle = new Battle(challenger, opponent);
		battlesByPlayerName.put(challenger.getName(), newBattle);
		battlesByPlayerName.put(opponent.getName(), newBattle);
		return newBattle;
	}

	public void skill(String playerName, Skill skill) {
		battlesByPlayerName.get(playerName).skill(playerName, skill);
	}

	public void ready(String playerName) {
		battlesByPlayerName.get(playerName).ready(playerName);
	}
}
