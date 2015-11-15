package cardbattle.web.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import cardbattle.service.CardBattleServiceName;

/**
 * Representa um chamado a serviço do CardBattle, contendo
 * o serviço a ser chamado e os argumentos dele.
 * @author Andy
 */
public class CardBattleServiceRequest {

	public final HttpServletRequest servletRequest;
	public final CardBattleServiceName service;
	public final CardBattleServiceRequestParameter[] parameters;

	public CardBattleServiceRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;

		String servletRequestQueryString = servletRequest.getQueryString();
		if (servletRequestQueryString == null) {
			service = CardBattleServiceName.welcome;
			parameters = null;
		} else {
			String[] serviceNameAndParameters = getServiceNameAndParameters(servletRequestQueryString);
			service = getService(serviceNameAndParameters);
			parameters = getParameters(serviceNameAndParameters);
		}
	}

	private CardBattleServiceName getService(String[] serviceNameAndParameters) {
		return CardBattleServiceName.getByName(serviceNameAndParameters[0]);
	}

	private CardBattleServiceRequestParameter[] getParameters(String[] serviceNameAndParameters) {
		int nParams = serviceNameAndParameters.length - 1;
		CardBattleServiceRequestParameter[] parameters = new CardBattleServiceRequestParameter[nParams];
		for (int i = 0; i < nParams; i++) {
			parameters[i] = new CardBattleServiceRequestParameter(serviceNameAndParameters[i + 1]);
		}
		return parameters;
	}

	private String[] getServiceNameAndParameters(String servletRequestQueryString) {
		try {
			return URLDecoder.decode(servletRequestQueryString, "UTF-8").trim().split(" ");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isAsync() {
		return service.isAsync();
	}
}
