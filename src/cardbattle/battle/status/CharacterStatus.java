package cardbattle.battle.status;

import cardbattle.battle.definitions.Skill;
import cardbattle.battle.execution.Character;

public class CharacterStatus {

	private String name;
	private int hp;
	private boolean skillSelected;
	private boolean knockedDown;
	private boolean slashing;
	private long skillSetTime;

	public CharacterStatus(Character character) {
		name = character.getName();
		hp = character.getHP();
		skillSelected = character.getSkill() != Skill.NONE;
		knockedDown = character.isKnockedDown();
		slashing = character.isSlashing();
		skillSetTime = character.getSkillSetTime();
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(name).append('=').append(hp);
		if (knockedDown) { s.append(" [KnockedDown]"); }
		if (slashing) { s.append(" [Slashing]"); }
		if (skillSelected) { s.append(" (SkillSet@").append(skillSetTime).append(")"); }
		return s.toString();
	}
}
