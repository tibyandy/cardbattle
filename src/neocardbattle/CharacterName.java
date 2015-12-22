package neocardbattle;

public enum CharacterName {

	LASH, AYLLAN;

	public static CharacterName get(String name) {
		try {
			return CharacterName.valueOf(name.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new CardBattleRuntimeException(CardBattleError.INVALID_CHARACTER_NAME);
		}
	}

}
