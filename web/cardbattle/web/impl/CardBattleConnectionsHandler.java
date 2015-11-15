package cardbattle.web.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe singleton responsável por tratar todas as conexões assíncronas
 * mantidas ativas com os clientes para posterior notificação "push".
 * @author Andy
 */
public class CardBattleConnectionsHandler {

	/* Singleton */
	private static CardBattleConnectionsHandler instance;
	private CardBattleConnectionsHandler() {}

	public static CardBattleConnectionsHandler getInstance() {
		if (instance == null) {
			instance = new CardBattleConnectionsHandler();
		}
		return instance;
	}

	private final List<CardBattleConnection> connectionQueue = new LinkedList<>();

	public void add(CardBattleConnection asyncConnection) {
		connectionQueue.add(asyncConnection);
	}

	public List<CardBattleConnection> getAndReleaseAll() {
		List<CardBattleConnection> result = new ArrayList<>(connectionQueue);
		connectionQueue.clear();
		return result;
	}

}
