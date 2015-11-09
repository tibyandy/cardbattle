package cardbattle;

public enum CharacterTemplate implements CharacterTemplateInterface {
	LASH(25, Skill.SLASH, Skill.TRIPLE_SLASH, Skill.HEAVY_SLASH),
	AYLLAN(20, Skill.MIND_BLAST, Skill.FIREWALL, Skill.METEOR);

	private final int hp;
	private final Skill[] skills;
	
	private CharacterTemplate(int hp, Skill... skills) {
		this.hp = hp;
		this.skills = skills;
	}

	public int getHP() {
		return hp;
	}
	public Skill[] getSkills() {
		return skills;
	}
}
