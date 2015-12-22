package neocardbattle;

import static neocardbattle.BattlePhase.ACTION_PHASE;
import static neocardbattle.BattlePhase.BATTLE_START;
import static neocardbattle.BattlePhase.END_OF_TURN;
import static neocardbattle.BattlePhase.SKILL_EVALUATION;
import static neocardbattle.Energy.BLUE;
import static neocardbattle.Energy.EMPTY;
import static neocardbattle.Energy.RED;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Battle {

	private Map<String, Player> playersByName = new HashMap<>();

	private BattlePhase battlePhase;
	private List<Energy> tableEnergy = new ArrayList<>();

	public Battle(Player p1, Player p2) {
		playersByName.put(p1.getName(), p1);
		playersByName.put(p2.getName(), p2);
		p1.engageInBattle(this);
		p2.engageInBattle(this, p1);
		battlePhase = BATTLE_START;
	}

	public List<CharacterInBattle> getCharacters() {
		List<CharacterInBattle> characters = new ArrayList<CharacterInBattle>();
		for (Player player : playersByName.values()) {
			characters.add(player.getCharacterInBattle());
		}
		return characters;
	}
	
	public void shuffleTable() {
		if (battlePhase == BATTLE_START || battlePhase == END_OF_TURN) {
			tableEnergy.clear();
			for (int i = 0; i < 5; i++) {
				tableEnergy.add(Energy.getRandom());
			}
			battlePhase = ACTION_PHASE;
		}
	}

	public void skill(String playerName, SkillName skill) {
		if (battlePhase == ACTION_PHASE) {
			getPlayerCharInBattle(playerName).setSkill(skill);
		}
	}
	
	public void ready(String playerName) {
		if (battlePhase == ACTION_PHASE) {
			CharacterInBattle chara = getPlayerCharInBattle(playerName);
			chara.setReady();
			CharacterInBattle opponent = chara.getOpponent();
			if (opponent.isReady()) {
				bothPlayersReady(opponent);
			}
		}
	}

	private CharacterInBattle getPlayerCharInBattle(String playerName) {
		return playersByName.get(playerName).getCharacterInBattle();
	}

	public void bothPlayersReady(CharacterInBattle firstCharaReady) {
		firstCharaReady.clearReady();
		firstCharaReady.getOpponent().clearReady();
		battlePhase = SKILL_EVALUATION;
	}

	public void evaluateSkills() {
		battlePhase = BattlePhase.PRIZE_DISTRIBUTION;
		for (Player player : playersByName.values()) {
			player.getCharacterInBattle().setSkill(null);
		}
	}

	public void getPrize(String playerName, int slot) {
		CharacterInBattle characterInBattle = playersByName.get(playerName).getCharacterInBattle();
		Energy prize = tableEnergy.get(slot);
		if (prize == RED || prize == BLUE) {
			characterInBattle.getEnergies().add(prize);
		}
		tableEnergy.set(slot, EMPTY);
		boolean tableHasEnergy = false;
		for (Energy energy : tableEnergy) {
			if (energy == RED || energy == BLUE) {
				tableHasEnergy = true;
				break;
			}
		}
		if (!tableHasEnergy) {
			battlePhase = BattlePhase.END_OF_TURN;
		}
	}

	public BattlePhase getPhase() {
		return battlePhase;
	}

	public List<Energy> getTableEnergy() {
		return tableEnergy;
	}

}
