package cardbattle.web.impl;

/**
 * Wrapper de parâmetro de requisição de serviço.
 * @author Andy
 */
public class CardBattleServiceRequestParameter {

	private String value;

	public CardBattleServiceRequestParameter(String value) {
		this.value = value;
	}

	public int asInt() {
		return Integer.valueOf(value);
	}

	public long asLong() {
		return Long.valueOf(value);
	}

	public String asString() {
		return value;
	}
}
