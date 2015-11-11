package cardbattle.server;

import java.util.Date;


public class Services {

	public static String process(String[] args) {
		if (args.length == 1) {
			String arg = args[0];
			switch(arg) {
			case "BLA":
				return bla();
			default:
				return "ok";
			}
		} else {
			return "CardBattle Server is running - " + new Date();
		}
	}

	private static String bla() {
		return "bla";
	}
}
