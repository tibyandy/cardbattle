package cardbattle.battle;

import static cardbattle.common.CardBattleError.INVALID_BATTLE_ID;
import static cardbattle.common.CardBattleException.error;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cardbattle.battle.definitions.CharacterTemplateInterface;
import cardbattle.battle.execution.CardBattle;
import cardbattle.common.CardBattleException;
import cardbattle.lobby.BattleRoom;
import cardbattle.lobby.Player;

public class BattleManager {

	/* Singleton */
	private static BattleManager instance;
	private BattleManager() {}

	public static BattleManager getInstance() {
		if (instance == null) {
			instance = new BattleManager();
		}
		return instance;
	}

	private final List<CardBattle> battles = new ArrayList<>();
	private final List<BattleRoom> battleRooms = new ArrayList<>();

	private final long STARTUP_TIME = new Date().getTime();
	
	public void reset() {
		System.out.println("BattleManager restarting...");
		battles.clear();
		battleRooms.clear();
	}

	public BattleRoom createBattleRoom(Player host) {
		BattleRoom battleRoom = new BattleRoom(host);
		battleRooms.add(battleRoom);
		return battleRoom;
	}

	public CardBattle createBattle(CharacterTemplateInterface... characters) {
		CardBattle battle = new CardBattle(battles.size() + 1, characters[0], characters[1]);
		battles.add(battle);
		return battle; 
	}

	public CardBattle createBattle(BattleRoom battleRoom) {
		CardBattle battle = new CardBattle(battles.size() + 1, battleRoom);
		battles.add(battle);
		return battle;
	}

	public CardBattle getBattle(int battleId) throws CardBattleException {
		try {
			return battles.get(battleId - 1);
		} catch (IndexOutOfBoundsException e) {
			throw error(INVALID_BATTLE_ID, battleId);
		}
	}

	public long getUptime() {
		return new Date().getTime() - STARTUP_TIME;
	}

	public List<BattleRoom> getBattleRooms() {
		return new ArrayList<>(battleRooms);
	}
}
