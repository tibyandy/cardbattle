package cardbattle.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet responsável por receber as chamadas web de antes do cliente ter se autenticado. 
 * @author Andy
 */
@WebServlet(urlPatterns = {"/Login"}, asyncSupported = true)
public class LoginServlet extends HttpServlet {

	private static final String PREFIX_SUCCESS = "1";
	private static final String PREFIX_FAILURE = "0";

	private static final String SESSION_PLAYER = "player";
	private static final String LOGOUT_COMMAND = "logout";
	
	private static final String PARAM_Q = "q";
	private static final String PARAM_W = "w";

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		if (req.getParameterMap().isEmpty()) {
			firstConnectionOrReconnecting(req, resp);
		} else {
			loginOrLogoutAttempt(req, resp);
		}
	}

	private void firstConnectionOrReconnecting(HttpServletRequest req, HttpServletResponse resp) {
		String loggedPlayer = getLoggedPlayerFromSession(req.getSession());
		if (loggedPlayer == null) {
			returnPlayersList(resp);
		} else {
			returnLoginSuccess(resp, loggedPlayer);
		}
	}

	private String getLoggedPlayerFromSession(HttpSession session) {
		return (String) session.getAttribute(SESSION_PLAYER);
	}

	private void returnPlayersList(HttpServletResponse resp) {
		// TODO: Players List
		writeMessage(resp, PREFIX_FAILURE.concat(";Andy;Jeff"));
	}

	private void loginOrLogoutAttempt(HttpServletRequest req, HttpServletResponse resp) {
		String paramQ = req.getParameter(PARAM_Q);
		if (LOGOUT_COMMAND.equals(paramQ)) {
			logout(req.getSession());
			returnPlayersList(resp);
		} else {
			attemptToLogin(req, resp, paramQ);
		}
	}

	private void logout(HttpSession session) {
		session.invalidate();
	}

	private void attemptToLogin(HttpServletRequest req, HttpServletResponse resp, String playerId) {
		String password = req.getParameter(PARAM_W);
		String playerLoggingIn = getPlayerWithIdAndPassword(playerId, password);
		if (playerLoggingIn != null) {
			loginPlayer(req.getSession(), playerLoggingIn);
			returnLoginSuccess(resp, playerLoggingIn);
		} else {
			returnLoginFailure(resp);
		}
	}

	private String getPlayerWithIdAndPassword(String playerId, String password) {
		// TODO: Login (check playerID and password)
		if ((playerId.equals("Andy") && password.equals("Johou"))
				|| (playerId.equals("Jeff") && password.equals("Fom"))) {
			return playerId;
		}
		return null;
	}

	private void loginPlayer(HttpSession session, String username) {
		session.setAttribute(SESSION_PLAYER, username);
	}

	private void returnLoginSuccess(HttpServletResponse resp, String playerName) {
		writeMessage(resp, PREFIX_SUCCESS.concat(playerName));
	}

	private void returnLoginFailure(HttpServletResponse resp) {
		writeMessage(resp, PREFIX_FAILURE);
	}

	private void writeMessage(HttpServletResponse resp, String msg) {
		try {
			PrintWriter out = resp.getWriter();
			out.write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
