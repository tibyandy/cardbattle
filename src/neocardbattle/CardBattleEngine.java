package neocardbattle;

public class CardBattleEngine {

	private final PlayersManager players = new PlayersManager();
	private final BattlesManager battles = new BattlesManager();

	public String login(String string) {
		return players.login(string);
	}

	public Player isLogged(String playerToken) {
		try {
			return players.getPlayer(playerToken);
		} catch (CardBattleRuntimeException e) {
			return null;
		}
	}

	public void select(String playerToken, String characterName) {
		players.getPlayer(playerToken).setCharacter(CharacterName.get(characterName));
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

	public void skill(String playerToken, SkillName skill) {
		battles.skill(players.getPlayer(playerToken).getName(), skill);
	}

	public int winner(String playerToken) {
		return 1;
	}

	public void ready(String playerToken) {
		battles.ready(players.getPlayer(playerToken).getName());
	}

	public void shuffleTable(String playerToken) {
		battles.shuffleTable(players.getPlayer(playerToken).getName());
	}

	public void evaluateSkills(String playerToken) {
		battles.evaluateSkills(players.getPlayer(playerToken).getName());
	}

	public void getPrize(String playerToken, int i) {
		battles.getPrize(players.getPlayer(playerToken).getName(), i);
	}
}
