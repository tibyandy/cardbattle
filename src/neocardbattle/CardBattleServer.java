package neocardbattle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardBattleServer {

	private CardBattleEngine engine = new CardBattleEngine();

	private Map<String, List<String>> queuedCommandsByPlayerName = new HashMap<>();
	private Map<String, List<Thread>> queuedThreadsByPlayerName = new HashMap<>();
	private Map<String, Integer> threadNumberByPlayerName = new HashMap<>();
	
	public CardBattleServer() {}

	public String requestToken(String playerName) {
		return engine.login(playerName);
	}

	public void connect(String playerToken) {
		String playerName = getPlayer(playerToken);
		if (playerName != null) {
			System.out.println("<" + playerName + ":" + playerToken + "> Connected.");
			queuedCommandsByPlayerName.put(playerName, new ArrayList<>());
			queuedThreadsByPlayerName.put(playerName, new ArrayList<>());
			threadNumberByPlayerName.put(playerName, 0);
			startThreadForPlayer(playerToken, playerName);
		}
	}

	public void sendCommand(String playerToken, String command) {
		String playerName = getPlayer(playerToken);
		if (playerName != null) {
			queuedCommandsByPlayerName.get(playerName).add(command);
			queuedThreadsByPlayerName.get(playerName).remove(0).interrupt();
			startThreadForPlayer(playerToken, playerName);
		}
	}

	public void disconnect(String playerToken) {
		String playerName = getPlayer(playerToken);
		if (playerName != null) {
			queuedThreadsByPlayerName.get(playerName).remove(0).interrupt();
		}
	}

	private String getPlayer(String playerToken) {
		Player loggedPlayer = engine.isLogged(playerToken);
		if (loggedPlayer == null) {
			System.out.println(playerToken + ": invalid token attempt.");
		}
		return loggedPlayer == null ? null : loggedPlayer.getName();
	}
	
	private void startThreadForPlayer(String playerToken, String playerName) {
		final int threadNumber = threadNumberByPlayerName.get(playerName) + 1;
		final String tokenAndNameAndThread = "<" + playerName + "|" + playerToken + "[" + threadNumber + "]> ";
		Runnable runnable = () -> {
			System.out.println(tokenAndNameAndThread + "Started.");
			try {
				Thread.sleep(200_000_000_000L);
				System.out.println(tokenAndNameAndThread + "Thread timed out!");
			} catch (InterruptedException e) {
				List<String> commands = queuedCommandsByPlayerName.get(playerName);
				if (commands.isEmpty()) {
					System.out.println(tokenAndNameAndThread + "Disconnected!");
				} else {
					String command = commands.remove(0);
					System.out.println(tokenAndNameAndThread + "-> Command: " + command);
				}
			}
			System.out.println(tokenAndNameAndThread + "Ended.");
		};
		Thread thread = new Thread(runnable);
		thread.start();
		queuedThreadsByPlayerName.get(playerName).add(thread);
		threadNumberByPlayerName.put(playerName, threadNumber);
	}
}
