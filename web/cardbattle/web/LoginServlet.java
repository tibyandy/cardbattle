package cardbattle.web;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cardbattle.web.impl.LoginHelper;

/**
 * Servlet responsável por receber as chamadas web de antes do cliente ter se autenticado. 
 * @author Andy
 */
@WebServlet(urlPatterns = {"/Login"}, asyncSupported = true)
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final String PARAM_Q = "q";
	private static final String PARAM_W = "w";

	private static final String LOGOUT_COMMAND = "logout";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		LoginHelper helper = new LoginHelper(req, resp);
		if (req.getParameterMap().isEmpty()) {
			if (helper.isPlayerLogged()) {
				helper.writePlayersList();
			} else {
				helper.writeLoginSuccess();
			}
		} else {
			String paramQ = req.getParameter(PARAM_Q);
			if (LOGOUT_COMMAND.equals(paramQ)) {
				helper.logout();
			} else {
				helper.attemptToLogin(paramQ, req.getParameter(PARAM_W));
			}
		}
	}
}
