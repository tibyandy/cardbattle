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

	public void skill(String playerName, SkillName skill) {
		battlesByPlayerName.get(playerName).skill(playerName, skill);
	}

	public void ready(String playerName) {
		battlesByPlayerName.get(playerName).ready(playerName);
	}

	public void shuffleTable(String playerName) {
		battlesByPlayerName.get(playerName).shuffleTable();
	}

	public void evaluateSkills(String playerName) {
		battlesByPlayerName.get(playerName).evaluateSkills();
	}

	public void getPrize(String playerName, int i) {
		battlesByPlayerName.get(playerName).getPrize(playerName, i);
	}
}
