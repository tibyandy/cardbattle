package neocardbattle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayersManager {

	private final Map<String, String> playersTokenByName = new HashMap<>();
	private final Map<String, Player> playersByToken = new HashMap<>();
	private final Map<String, Player> readyPlayersByName = new HashMap<>();

	private final Map<String, List<String>> challengedPlayerNamesByChallengerPlayerNames = new HashMap<>();

	public String login(String playerName) {
		if (playersTokenByName.containsKey(playerName)) {
			playersTokenByName.remove(playerName);
		}
		// token = string de 2 chars dentre 1000 possibilidades + playerName
		String token = Integer.toString((int) (Math.random() * 1000), 32);
		playersByToken.put(token, new Player(playerName));
		playersTokenByName.put(playerName, token);
		return token;
	}

	public Player getPlayer(String playerToken) {
		Player player = playersByToken.get(playerToken);
		if (player == null) {
			throw new CardBattleRuntimeException(CardBattleError.INVALID_PLAYER_TOKEN);
		}
		return player;
	}

	public Player getReadyPlayerByToken(String playerToken) {
		Player player = getPlayer(playerToken);
		if (!readyPlayersByName.containsKey(player.getName())) {
			throw new CardBattleRuntimeException(CardBattleError.PLAYER_IS_NOT_READY);
		}
		return player;
	}

	public void ready(String playerToken, boolean ready) {
		Player player = getPlayer(playerToken);
		if (ready) {
			if (player.getCharacter() == null) {
				throw new CardBattleRuntimeException(CardBattleError.PLAYER_HAS_NO_CHARACTER_SELECTED);
			}
			readyPlayersByName.put(player.getName(), player);
		} else {
			readyPlayersByName.remove(player.getName(), player);
		}
	}

	public String[] getReadyPlayers(String playerToken) {
		List<String> playerNames = new ArrayList<String>(readyPlayersByName.keySet());
		String playerName = getPlayer(playerToken).getName();
		playerNames.remove(playerName);
		return playerNames.toArray(new String[0]);
	}

	public Player[] challenge(String playerToken, String opponentName) {
		Player challenger = getReadyPlayerByToken(playerToken);
		Player opponent = readyPlayersByName.get(opponentName);
		if (opponent == null) {
			throw new CardBattleRuntimeException(CardBattleError.CHALLENGED_PLAYER_IS_NOT_READY);
		}
		String challengerName = challenger.getName();

		// Checks if opponent player already challenged the challenger
		List<String> listOfPlayersChallengedByTheOpponent = challengedPlayerNamesByChallengerPlayerNames.get(opponentName);
		if (listOfPlayersChallengedByTheOpponent != null
				&& listOfPlayersChallengedByTheOpponent.indexOf(challengerName) > -1) {
			// Opponent has already challenged the challenger. Challenge accepted!
			refuseAllChallenges(challengerName, opponentName);
			return new Player[] {opponent, challenger};
		}

		// Creates the challenge
		List<String> challengesFromChallenger = challengedPlayerNamesByChallengerPlayerNames.get(challengerName);
		if (challengesFromChallenger == null) {
			challengedPlayerNamesByChallengerPlayerNames.put(challengerName, Arrays.<String>asList(new String[] { opponentName }));
		} else {
			challengesFromChallenger.add(opponentName);
		}
		return null;
	}

	private void refuseAllChallenges(String...playerNames) {
		// Remove all challenges proposed by the players
		for (String playerName : playerNames) {
			challengedPlayerNamesByChallengerPlayerNames.remove(playerName);
		}
		// Leave all challenges from the other players
		for (List<String> challengedPlayers : challengedPlayerNamesByChallengerPlayerNames.values()) {
			for (String playerName : playerNames) {
				challengedPlayers.remove(playerName);
			}
		}
	}
}
