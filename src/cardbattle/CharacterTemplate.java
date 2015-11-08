package cardbattle;

public enum CharacterTemplate {
	LASH(25, Skill.SLASH, Skill.TRIPLE_SLASH, Skill.HEAVY_SLASH),
	AYLLAN(20, Skill.MIND_BLAST, Skill.FIREWALL, Skill.METEOR);

	public final int hp;
	public final Skill[] skills;
	
	private CharacterTemplate(int hp, Skill... skills) {
		this.hp = hp;
		this.skills = skills;
	}
}
