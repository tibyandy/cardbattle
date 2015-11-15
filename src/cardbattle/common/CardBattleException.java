package cardbattle.common;

public class CardBattleException extends Exception {

	private static final long serialVersionUID = 1L;

	private CardBattleError error;
	
	public static CardBattleException error(CardBattleError error) {
		return new CardBattleException(error, error.errorMessageTemplate);
	}

	public static CardBattleException error(CardBattleError error, Object o) {
		return new CardBattleException(error, String.format(error.errorMessageTemplate, o));
	}

	private CardBattleException(CardBattleError error, String errorMsg) {
		super(errorMsg);
		this.error = error;
	}

	public CardBattleError error() {
		return error;
	}
}
