package cardbattle.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/CardBattle"}, asyncSupported = true)
public class CardBattleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final List<AsyncContext> contexts = new LinkedList<>();
	private final List<String[]> updateRequests = new ArrayList<>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String reqString = req.getQueryString();
		String[] args;

		if (reqString != null) {
			reqString = java.net.URLDecoder.decode(reqString, "UTF-8");
			args = reqString.split(" ");			
		} else {
			reqString = "Welcome to the CardBattle Server";
			args = new String[0];
			System.out.println("Welcoming ".concat(req.getRemoteAddr()));
		}

		System.out.println("doGet " + reqString);

		if (args != null && args.length > 0 && args[0].equals("status")) {
	        createAsyncConnection(req, resp, reqString, args);
		} else {
			ServiceResponse response = executeService(resp, reqString, args);
			if (response.synchedResponse) {
		        updateAsyncConnections();
			}
		}
	}

	private ServiceResponse executeService(HttpServletResponse resp, String reqString, String[] args) throws IOException {
		ServiceResponse response = Services.process(args);
		PrintWriter out = resp.getWriter();
		out.println(reqString);
		out.println(response.message);
		return response;
	}

	private void updateAsyncConnections() {
		List<AsyncContext> contexts = new ArrayList<>(this.contexts);
		this.contexts.clear();
		
		ServiceResponse response;
		int i = 0;
		for (AsyncContext asyncContext : contexts) {
		    PrintWriter writer = null;
			try {
				writer = asyncContext.getResponse().getWriter();
			} catch (IOException e) {
				System.out.println(e);
			}
	    	String[] update = updateRequests.remove(0);
			System.out.println("Updating poll " + i++ + ": " + update[0] + " " + update[1] + " " + update[2]);
			response = Services.process(update);
			System.out.println(response.message);
			if (writer != null) {
		        writer.println(response.message);
		        writer.flush();
			}
	        asyncContext.complete();
		}
	}

	private void createAsyncConnection(HttpServletRequest req, HttpServletResponse resp, String updateString, String[] updateParameters) {
		String index = String.valueOf(contexts.size());
		System.out.println("[".concat(req.getRemoteAddr()).concat("] Polling ").concat(index).concat(" ").concat(updateString));
		AsyncContext asyncContext = req.startAsync(req, resp);
		asyncContext.setTimeout(10 * 60 * 1000);
		contexts.add(asyncContext);
		updateRequests.add(updateParameters);
		System.out.println("[".concat(req.getRemoteAddr()).concat("] Polling ").concat(index).concat(" completed."));
	}
}
