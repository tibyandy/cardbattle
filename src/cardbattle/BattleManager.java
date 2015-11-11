package cardbattle;

import static cardbattle.exceptions.CardBattleException.INVALID_BATTLE_ID;
import static cardbattle.exceptions.CardBattleException.error;

import java.util.ArrayList;
import java.util.List;

import cardbattle.exceptions.CardBattleException;

public class BattleManager {

	private static List<CardBattle> battles = new ArrayList<>();

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
}
