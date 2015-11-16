package cardbattle.service;

import cardbattle.battle.BattleManager;
import cardbattle.battle.definitions.CharacterTemplate;
import cardbattle.battle.definitions.Skill;
import cardbattle.battle.execution.CardBattle;
import cardbattle.battle.status.BattleStatus;
import cardbattle.common.CardBattleException;
import cardbattle.player.PlayerManager;

public class CardBattleServices {

	/* Singleton */
	private static CardBattleServices instance;
	private CardBattleServices() {}

	public static CardBattleServices getInstance() {
		if (instance == null) {
			instance = new CardBattleServices();
		}
		return instance;
	}

	private BattleManager battleManager = BattleManager.getInstance();
	private PlayerManager playerManager = PlayerManager.getInstance();
	
	public CardBattleServiceResult welcome() {
		return new CardBattleServiceResult("CardBattleServer is up and running.");
	}

	public CardBattleServiceResult resetServer() {
		battleManager.reset();
		return new CardBattleServiceResult("CardBattleServer is reset!");
	}

	public CardBattleServiceResult createBattle(String char1name, String char2name) throws CardBattleException {
		CharacterTemplate chara1 = CharacterTemplate.get(char1name);
		CharacterTemplate chara2 = CharacterTemplate.get(char2name);
		CardBattle newBattle = battleManager.createBattle(chara1, chara2);
		return new CardBattleServiceResult(true, "Battle with ID %d created.", newBattle.id);
	}

	public CardBattleServiceResult setSkill(int battleId, int charaId, String skillName) throws CardBattleException {
		getBattle(battleId).setSkill(charaId, Skill.get(skillName));
		return new CardBattleServiceResult(true, "Character %d from battle %d is preparing to use %s", charaId, battleId, skillName);
	}

	public CardBattleServiceResult endTurn(int battleId) throws CardBattleException {
		getBattle(battleId).endTurn();
		return new CardBattleServiceResult(true, "Battle %d turn ended.", battleId);
	}

	public CardBattleServiceResult uptime() {
		long uptime = battleManager.getUptime();
		return new CardBattleServiceResult(String.valueOf(uptime));
	}
	
	public CardBattleServiceResult status(int battleId) throws CardBattleException {
		BattleStatus battleStatus = getBattle(battleId).getBattleStatus();
		return new CardBattleServiceResult(battleStatus.toString());
	}

	private CardBattle getBattle(int battleId) throws CardBattleException {
		return battleManager.getBattle(battleId);
	}

	public CardBattleServiceResult joinPlayer(String playerName) {
		try {
			playerManager.join(playerName);
		} catch (CardBattleException e) {
			// TODO tratamento de erros e sucesso para os outros serviços
			return new CardBattleServiceResult(e.getMessage()).success(false);
		}
		return new CardBattleServiceResult("Player joined.");
	}
}
