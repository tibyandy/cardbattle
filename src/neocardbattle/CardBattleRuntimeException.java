package neocardbattle;

public class CardBattleRuntimeException extends RuntimeException {

	private CardBattleError error;

	public CardBattleRuntimeException(CardBattleError error) {
		this.error = error;
	}

	public String getErrorCode() {
		return error.getCode();
	}
}
