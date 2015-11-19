package cardbattle.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Wrapper para todos os parâmetros armazenados na sessão.
 * @author Andy
 */
public class CardBattleSession {

	private final HttpSession httpSession;

	private static final String PLAYER = "player";

	private CardBattleSession(HttpSession session) {
		this.httpSession = session;
	}

	public static CardBattleSession getSession(HttpServletRequest req) {
		return new CardBattleSession(req.getSession());
	}

	public String getPlayer() {
		return (String) httpSession.getAttribute(PLAYER);
	}

	public void setPlayer(String username) {
		httpSession.setAttribute(PLAYER, username);
	}

	public void invalidate() {
		httpSession.invalidate();
	}
}
