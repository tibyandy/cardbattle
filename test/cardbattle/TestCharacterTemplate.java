package cardbattle;

public class TestCharacterTemplate implements CharacterTemplateInterface {
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
