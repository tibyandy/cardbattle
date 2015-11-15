package cardbattle.common;

public enum CardBattleError {

	UNKNOWN_COMMAND("Unknown command"),
	INVALID_CHARACTER_ID("Invalid Character Id: %d"),
	INVALID_CHARACTER_NAME("Invalid Character Name: %s"),
	INVALID_SKILL_NAME("Invalid Skill Name: %s"),
	INVALID_BATTLE_ID("Invalid Battle Id: %s");

	public final String errorMessageTemplate;

	private CardBattleError(String errorMessageTemplate) {
		this.errorMessageTemplate = errorMessageTemplate;
	}
}
