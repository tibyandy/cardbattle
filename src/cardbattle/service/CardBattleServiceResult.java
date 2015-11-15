package cardbattle.service;

import java.util.Arrays;

import cardbattle.common.CardBattleException;
import cardbattle.web.impl.CardBattleServiceRequest;

public class CardBattleServiceResult {

	private boolean notifiesQueuedConnections = false;
	private String responseMessage;
	private boolean success = true;

	public CardBattleServiceResult() {}

	public CardBattleServiceResult(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public CardBattleServiceResult(String responseMessage, Object... messageArguments) {
		this.responseMessage = String.format(responseMessage, messageArguments);
	}

	public CardBattleServiceResult(boolean notifiesQueuedConnections, String responseMessage, Object... messageArguments) {
		this.notifiesQueuedConnections = notifiesQueuedConnections;
		this.responseMessage = String.format(responseMessage, messageArguments);
	}

	public CardBattleServiceResult(CardBattleServiceRequest cbRequest, CardBattleException e) {
		StringBuilder message = new StringBuilder();
		message.append("[").append(cbRequest.service).append("]: Invalid command arguments: ");
		message.append(Arrays.asList(cbRequest.parameters)).append(".\nReason: ").append(e.getMessage());
		this.responseMessage = message.toString();
		this.success = false;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public boolean notifiesQueuedConnections() {
		return notifiesQueuedConnections;
	}

	public CardBattleServiceResult notifiesQueuedConnections(boolean notifiesQueuedConnections) {
		this.notifiesQueuedConnections = notifiesQueuedConnections;
		return this;
	}

	public boolean success() {
		return success;
	}

	public CardBattleServiceResult success(boolean success) {
		this.success = success;
		return this;
	}
}
