package cardbattle.service;

import java.util.HashMap;
import java.util.Map;

public enum CardBattleServiceName {
	welcome,
	uptime,
	resetServer(true),
	status(false, true),
	endTurn(true),
	createBattle(true),
	setSkill(true),
	listCharacters,
	listPlayers,
	unknown;

	private static Map<String, CardBattleServiceName> byName;
	private final boolean asynchronous;
	private final boolean notifiesQueuedConnections;

	private CardBattleServiceName() {
		this(false, false);
	}
	private CardBattleServiceName(boolean notifiesQueuedConnections) {
		this(notifiesQueuedConnections, false);
	}
	private CardBattleServiceName(boolean notifiesQueuedConnections, boolean asynchronous) {
		this.notifiesQueuedConnections = notifiesQueuedConnections;
		this.asynchronous = asynchronous;
	}
	
	public static CardBattleServiceName getByName(String serviceName) {
		if (byName == null) {
			byName = new HashMap<>();
			for (CardBattleServiceName service : CardBattleServiceName.values()) {
				byName.put(service.name().toUpperCase(), service);
			}
		}
		if (serviceName == null) {
			return unknown;
		} else {
			return byName.get(serviceName.toUpperCase());
		}
	}

	public boolean isAsync() {
		return asynchronous;
	}

	public boolean notifiesQueuedConnections() {
		return notifiesQueuedConnections;
	}
}
