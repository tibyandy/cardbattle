package cardbattle.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cardbattle.service.CardBattleServiceResult;
import cardbattle.service.CardBattleServices;
import cardbattle.web.impl.CardBattleConnection;
import cardbattle.web.impl.CardBattleConnectionsHandler;
import cardbattle.web.impl.CardBattleServiceRequest;

/**
 * Servlet responsável por receber todas chamadas web (ajax ou não) do client.
 * Realiza chamadas ao {@link CardBattleServices} via {@link CardBattleServiceIntegration}. 
 * @author Andy
 */
@WebServlet(urlPatterns = {"/CardBattle"}, asyncSupported = true)
public class CardBattleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final CardBattleConnectionsHandler heldConnections = CardBattleConnectionsHandler.getInstance();

	@Override
	protected void doGet(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		processRequest(new CardBattleServiceRequest(servletRequest), servletResponse);
	}

	private void processRequest(CardBattleServiceRequest cardBattleServiceRequest, HttpServletResponse servletResponse) {
		if (!cardBattleServiceRequest.isAsync()) {
			CardBattleServiceResult cbServiceResult = runService(cardBattleServiceRequest);
			if (cbServiceResult.notifiesQueuedConnections()) {
				freeConnections();
			}
			writeServletResponse(servletResponse, cbServiceResult);
		} else {
			holdConnection(cardBattleServiceRequest, servletResponse);
		}
	}

	private CardBattleServiceResult runService(CardBattleServiceRequest cbServiceRequest) {
		return CardBattleServiceIntegration.execute(cbServiceRequest);
	}

	private void writeServletResponse(ServletResponse servletResponse, CardBattleServiceResult cbServiceResult) {
		try {
			PrintWriter out = servletResponse.getWriter();
			out.print(cbServiceResult.getResponseMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void holdConnection(CardBattleServiceRequest cbServiceRequest, HttpServletResponse servletResponse) {
		CardBattleConnection asyncConnection = new CardBattleConnection(cbServiceRequest, servletResponse);
		heldConnections.add(asyncConnection);
	}

	private void freeConnections() {
		for (CardBattleConnection connection : heldConnections.getAndReleaseAll()) {
			CardBattleServiceResult cbServiceResult = runService(connection.cbRequest);
			writeServletResponse(connection.asyncContext.getResponse(), cbServiceResult);
			connection.asyncContext.complete();
		}
	}
}
