package cardbattle;

import static cardbattle.Property.SLASH_BOOSTED;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	public Character(CharacterTemplate character) {
		hp = character.hp;
		skills.addAll(Arrays.asList(character.skills));
	}

	public void clearSkill() {
		skill = Skill.NONE;
		skillDamage = 0;
		skillSpeed = 0;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
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
