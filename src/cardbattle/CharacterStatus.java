package cardbattle;

public class CharacterStatus {

	private String name;
	private int hp;
	private boolean skillSelected;
	private boolean knockedDown;
	private boolean slashing;
	private long skillSetTime;

	public CharacterStatus(Character character) {
		name = character.getName();
		hp = character.hp;
		skillSelected = character.getSkill() != Skill.NONE;
		knockedDown = character.knockedDown;
		slashing = character.slashing;
		skillSetTime = character.getSkillSetTime();
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(name).append('=').append(hp);
		if (knockedDown) { s.append(" [KnockedDown]"); }
		if (slashing) { s.append(" [Slashing]"); }
		if (skillSelected) { s.append(" (").append(skillSetTime).append(")"); }
		return s.toString();
	}
}
