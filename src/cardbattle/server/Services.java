package cardbattle.server;

import static cardbattle.BattleManager.getBattle;
import static java.lang.String.format;
import cardbattle.BattleManager;
import cardbattle.CardBattle;
import cardbattle.CharacterTemplate;
import cardbattle.Skill;
import cardbattle.exceptions.CardBattleException;

public class Services {

	public static String process(String[] args) {
		if (args == null || args.length == 0) {
			return "";
		}
		String msg;
		try {
			msg = runServiceMethod(args);
		} catch (CardBattleException e) {
			msg = format("ERROR\n%s", e.getMessage());
		}
		if (msg == null) {
			msg = format("ERROR\nUnknown method name %s with %d arguments", args[0], args.length - 1);
		}
		return msg;
	}

	private static String runServiceMethod(String[] x) throws CardBattleException {
		String methodName = x[0];
		switch (x.length) {
		case 4:
			switch (methodName) {
			case "setSkill": return setSkill(i(x[1]), i(x[2]), x[3]);
			}
		case 3:
			switch (methodName) {
			case "createBattle": return createBattle(x[1], x[2]);
			}
		case 2:
			switch (methodName) {
			case "endTurn": return endTurn(i(x[1]));
			}
		case 1:
			switch (methodName) {
			case "resetServer": return resetServer();
			}
		}
		return null;
	}

	private static String resetServer() throws CardBattleException {
		BattleManager.reset();
		return "OK";
	}

	private static String endTurn(int battleId) throws CardBattleException {
		CardBattle b = getBattle(battleId).endTurn();
		return format("OK\nChara1HP = %d\nChara2HP = %d", b.hp(1), b.hp(2));
	}

	private static String setSkill(int battleId, int charNumber, String skillName) throws CardBattleException {
		getBattle(battleId).setSkill(charNumber, Skill.get(skillName));
		return "OK";
	}

	private static String createBattle(String char1name, String char2name) throws CardBattleException {
		int battleId = BattleManager.createBattle(
				CharacterTemplate.get(char1name),
				CharacterTemplate.get(char2name)).id;
		return format("OK\nBattleId = %d", battleId);
	}

	private static int i(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
