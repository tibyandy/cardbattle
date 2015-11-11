package cardbattle;

import java.util.ArrayList;
import java.util.List;

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

	public static CardBattle getBattle(int battleId) {
		return battles.get(battleId - 1); 
	}
}
