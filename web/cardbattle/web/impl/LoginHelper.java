package cardbattle.web.impl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cardbattle.web.CardBattleSession;
import cardbattle.web.LoginServlet;

/**
 * Helper para o {@link LoginServlet}.
 * @author Andy
 */
public class LoginHelper {

	private static final String PREFIX_SUCCESS = "1";
	private static final String PREFIX_FAILURE = "0";

	private final CardBattleSession session;
	private final HttpServletResponse resp;

	public LoginHelper(HttpServletRequest req, HttpServletResponse resp) {
		this.resp = resp;
		session = CardBattleSession.getSession(req);
	}

	public boolean isPlayerLogged() {
		return session.getPlayer() == null;
	}
	
	public void attemptToLogin(String playerId, String password) {
		String playerLoggingIn = getPlayerWithIdAndPassword(playerId, password);
		if (playerLoggingIn != null) {
			session.setPlayer(playerLoggingIn);
			writeLoginSuccess();
		} else {
			writeLoginFailure();
		}
	}

	private void writeLoginFailure() {
		writeMessage(PREFIX_FAILURE);
	}

	public void writePlayersList() {
		writeMessage(PREFIX_FAILURE.concat(";Andy;Jeff"));
	}

	public void writeLoginSuccess() {
		writeMessage(PREFIX_SUCCESS.concat(session.getPlayer()));
	}

	private String getPlayerWithIdAndPassword(String playerId, String password) {
		// TODO: Login (check playerID and password)
		if ((playerId.equals("Andy") && password.equals("Johou"))
				|| (playerId.equals("Jeff") && password.equals("Fom"))) {
			return playerId;
		}
		return null;
	}

	private void writeMessage(String msg) {
		try {
			PrintWriter out = resp.getWriter();
			out.write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void logout() {
		session.invalidate();
		writePlayersList();
	}
}
