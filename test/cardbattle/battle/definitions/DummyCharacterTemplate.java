package cardbattle.battle.definitions;

import cardbattle.battle.definitions.CharacterTemplateInterface;
import cardbattle.battle.definitions.Skill;

public class DummyCharacterTemplate implements CharacterTemplateInterface {
	public int getHP() {
		return 30;
	}
	public Skill[] getSkills() {
		return Skill.values();
	}

	@Override
	public String toString() {
		return "DUMMY_CHARACTER";
	}
}
