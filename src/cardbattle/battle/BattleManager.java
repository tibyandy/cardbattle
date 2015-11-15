package cardbattle.battle;

import static cardbattle.common.CardBattleError.INVALID_BATTLE_ID;
import static cardbattle.common.CardBattleException.error;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cardbattle.battle.definitions.CharacterTemplateInterface;
import cardbattle.battle.execution.CardBattle;
import cardbattle.common.CardBattleException;

public class BattleManager {

	private static List<CardBattle> battles = new ArrayList<>();

	private static final long STARTUP_TIME = new Date().getTime();
	
	public static void reset() {
		System.out.println("BattleManager restarting...");
		battles = new ArrayList<>();
	}

	public static CardBattle createBattle(CharacterTemplateInterface... characters) {
		CardBattle battle = new CardBattle(battles.size() + 1, characters[0], characters[1]);
		battles.add(battle);
		return battle; 
	}

	public static CardBattle getBattle(int battleId) throws CardBattleException {
		try {
			return battles.get(battleId - 1);
		} catch (IndexOutOfBoundsException e) {
			throw error(INVALID_BATTLE_ID, battleId);
		}
	}

	public static long getUptime() {
		return new Date().getTime() - STARTUP_TIME;
	}
}
