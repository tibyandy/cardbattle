package cardbattle.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/CardBattle"})
public class CardBattleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String reqString = req.getQueryString();
		String[] args;
		if (reqString != null) {
			reqString = java.net.URLDecoder.decode(reqString, "UTF-8");
			args = reqString.split(" ");
			System.out.println("[".concat(req.getRemoteAddr()).concat("] ").concat(reqString));
		} else {
			reqString = "Welcome to the CardBattle Server";
			args = new String[0];
			System.out.println("Welcoming ".concat(req.getRemoteAddr()));
		}
		String reply = Services.process(args);
		PrintWriter out = resp.getWriter();
		out.println(reqString);
		out.println(reply);
	}
}
