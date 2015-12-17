package neocardbattle;

public enum CardBattleError {

	INVALID_PLAYER_TOKEN("CBE_001"),
	INVALID_CHARACTER_NAME("CBE_002"),
	PLAYER_HAS_NO_CHARACTER_SELECTED("CBE_003"),
	PLAYER_IS_NOT_READY("CBE_004"),
	CHALLENGED_PLAYER_IS_NOT_READY("CBE_005"),
	;

	private String code;

	private CardBattleError(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
