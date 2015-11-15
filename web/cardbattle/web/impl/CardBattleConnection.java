package cardbattle.web.impl;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Representa uma conexão assíncrona mantida ativa com o cliente.
 * @author Andy
 */
public class CardBattleConnection {

	private static final int CONNECTION_TIMEOUT =  10 * 60 * 1000; // 10 minutos

	public final AsyncContext asyncContext;
	public final CardBattleServiceRequest cbRequest;

	public CardBattleConnection(CardBattleServiceRequest cbRequestToQueue, HttpServletResponse servletResponse) {
		this.cbRequest = cbRequestToQueue;
		HttpServletRequest servletRequest = cbRequestToQueue.servletRequest;
		asyncContext = servletRequest.startAsync(servletRequest, servletResponse);
		asyncContext.setTimeout(CONNECTION_TIMEOUT);
	}
}
