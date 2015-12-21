package neocardbattle;

import java.util.HashMap;
import java.util.Map;

public class Battle {

	private Map<String, Player> playersByName = new HashMap<>();

	public Battle(Player p1, Player p2) {
		playersByName.put(p1.getName(), p1);
		playersByName.put(p2.getName(), p2);
		p1.engageInBattle(this);
		p2.engageInBattle(this, p1);
	}

	public void skill(String playerName, Skill skill) {
		getPlayerCharInBattle(playerName).setSkill(skill);
	}
	
	public void ready(String playerName) {
		CharacterInBattle chara = getPlayerCharInBattle(playerName);
		chara.setReady();
		CharacterInBattle opponent = chara.getOpponent();
		if (opponent.isReady()) {
			endTurn(opponent);
		}
	}

	private CharacterInBattle getPlayerCharInBattle(String playerName) {
		return playersByName.get(playerName).getCharacterInBattle();
	}

	public void endTurn(CharacterInBattle firstCharaReady) {
		firstCharaReady.clearReady();
		firstCharaReady.getOpponent().clearReady();
	}
}
