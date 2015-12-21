package neocardbattle;

public class CardBattleEngine {

	private final PlayersManager players = new PlayersManager();
	private final BattlesManager battles = new BattlesManager();

	public String login(String string) {
		return players.login(string);
	}

	public void select(String playerToken, String characterName) {
		players.getPlayer(playerToken).setCharacter(Character.get(characterName));
	}

	public void readyForBattle(String playerToken) {
		players.ready(playerToken, true);
	}

	public String[] listReadyPlayers(String playerToken) {
		return players.getReadyPlayers(playerToken);
	}

	public Battle challenge(String playerToken, String challengedPlayerName) {
		Player[] listOfPlayersInBattle = players.challenge(playerToken, challengedPlayerName);
		if (listOfPlayersInBattle != null) {
			return battles.create(listOfPlayersInBattle[0], listOfPlayersInBattle[1]);
		}
		return null;
	}

	public void skill(String playerToken, Skill skill) {
		battles.skill(players.getPlayer(playerToken).getName(), skill);
	}

	public int winner(String playerToken) {
		return 1;
	}

	public void ready(String playerToken) {
		battles.ready(players.getPlayer(playerToken).getName());
	}

}
