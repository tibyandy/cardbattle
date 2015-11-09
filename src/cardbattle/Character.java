package cardbattle;

import static cardbattle.Property.SLASH_BOOSTED;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cardbattle.exceptions.InvalidSkillException;

public class Character {

	int skillDamage = 0;
	int skillSpeed = 0;
	int hp;

	boolean damaged = false;
	boolean slashing = false;
	boolean knockedDown = false;

	private Skill skill = Skill.NONE;
	private final List<Skill> skills = new ArrayList<>();

	public Character() {
		hp = 30;
	}

	public Character(CharacterTemplateInterface character) {
		hp = character.getHP();
		skills.addAll(Arrays.asList(character.getSkills()));
	}

	public void clearSkill() {
		skill = Skill.NONE;
		skillDamage = 0;
		skillSpeed = 0;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) throws InvalidSkillException {
		if (!skills.contains(skill)) {
			throw new InvalidSkillException();
		}
		this.skill = skill;
		skillDamage = skill.damage;
		skillSpeed = skill.speed;

		if (slashing && skill.has(SLASH_BOOSTED)) {
			skillSpeed++;
		}
		if (knockedDown) {
			skillSpeed--;
		}
	}

	public int getSkillSpeed() {
		return skillSpeed;
	}

	public int getHP() {
		return hp;
	}

	public int getSkillDamage() {
		return skillDamage;
	}
	public void nullifySkillDamage() {
		this.skillDamage = 0;
	}
}
