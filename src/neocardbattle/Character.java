package neocardbattle;

public enum Character {
	LASH(Skill.SLASH, Skill.HEAVY_SLASH, Skill.TRIPLE_SLASH),
	AYLLAN(Skill.MIND_BLAST, Skill.FIREWALL, Skill.METEOR),
	;

	private final Skill[] skills;

	private Character(Skill...skills) {
		this.skills = skills;
	}
	
	public static Character get(String name) {
		try {
			return Character.valueOf(name.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new CardBattleRuntimeException(CardBattleError.INVALID_CHARACTER_NAME);
		}
	}

	public Skill[] getSkills() {
		return skills;
	}
}
