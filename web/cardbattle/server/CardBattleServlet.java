package cardbattle.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CardBattleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String reqString = req.getQueryString();
		String[] args;
		if (reqString != null) {
			args = java.net.URLDecoder.decode(reqString, "UTF-8").split(" ");
		} else {
			args = new String[0];
		}
		String reply = Services.process(args);
		PrintWriter out = resp.getWriter();
		out.println(reply);
	}
}