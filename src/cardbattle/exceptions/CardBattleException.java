package cardbattle.exceptions;

public class CardBattleException extends Exception {

	private static final long serialVersionUID = 1L;

	public static final String INVALID_CHARACTER_ID = "Invalid Character Id: %d";
	public static final String INVALID_CHARACTER_NAME = "Invalid Character Name: %s ";
	public static final String INVALID_SKILL_NAME = "Invalid Skill Name: %s ";
	public static final String INVALID_BATTLE_ID = "Invalid Battle Id: %s ";

	private String error;
	
	public static CardBattleException error(String error, Object o) {
		return new CardBattleException(error, String.format(error, o));
	}

	private CardBattleException(String error, String errorMsg) {
		super(errorMsg);
		this.error = error;
	}

	public String error() {
		return error;
	}
}
