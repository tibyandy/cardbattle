package cardbattle.web;

import static cardbattle.common.CardBattleError.UNKNOWN_COMMAND;
import cardbattle.common.CardBattleException;
import cardbattle.service.CardBattleServiceName;
import cardbattle.service.CardBattleServiceResult;
import cardbattle.service.CardBattleServices;
import cardbattle.web.impl.CardBattleServiceRequest;
import cardbattle.web.impl.CardBattleServiceRequestParameter;

/**
 * Responsável por invocar os serviços do CardBattleServicesFacade
 * a partir da camada web.
 *
 * Utilizada pelo {@link CardBattleServlet}.
 *
 * @author Andy
 */
public class CardBattleServiceIntegration {

	private CardBattleServiceName service;
	private CardBattleServiceRequestParameter[] parameters;
	private int parameterIteratorIndex;

	private CardBattleServiceIntegration(CardBattleServiceName service, CardBattleServiceRequestParameter[] parameters) {
		this.service = service;
		this.parameters = parameters;
		parameterIteratorIndex = 0;
	}

	private CardBattleServiceResult run() throws CardBattleException {
		CardBattleServices services = CardBattleServices.getInstance();
		switch (service) {
		case listCharacters:
			return services.listCharacters();
		case welcome:
			return services.welcome();
		case resetServer:
			return services.resetServer();
		case createBattle:
			return services.createBattle(string(), string());
		case setSkill:
			return services.setSkill(integer(), integer(), string());
		case endTurn:
			return services.endTurn(integer());
		case uptime:
			return services.uptime();
		case status:
			return services.status(integer());
		default:
			throw CardBattleException.error(UNKNOWN_COMMAND);
		}
	}

	public static CardBattleServiceResult execute(CardBattleServiceRequest cbRequest) {
		try {
			return new CardBattleServiceIntegration(cbRequest.service, cbRequest.parameters).run();
		} catch (CardBattleException e) {
			return new CardBattleServiceResult(cbRequest, e);
		}
	}

	private String string() {
		String result = parameters[parameterIteratorIndex].asString();
		parameterIteratorIndex++;
		return result;
	}

	private int integer() {
		int result = parameters[parameterIteratorIndex].asInt();
		parameterIteratorIndex++;
		return result;
	}
}
