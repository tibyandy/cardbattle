package neocardbattle;

public enum Character {
	AYLLAN, LASH;
	
	public static Character get(String name) {
		try {
			return Character.valueOf(name.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new CardBattleRuntimeException(CardBattleError.INVALID_CHARACTER_NAME);
		}
	}
}
