package cardbattle;

import static cardbattle.Skill.SLASH;

public class Character {

	int skillDamage = 0;
	int skillSpeed = 0;
	int hp;
	boolean damaged = false;
	
	boolean slashing = false;
	boolean knockedDown = false;

	private Skill skill = Skill.NONE;

	public Character() {
		hp = 30;
	}

	public Skill getSkill() {
		return skill;
	}
	public void setSkill(Skill skill) {
		this.skill = skill;
		skillDamage = skill.damage;
		skillSpeed = skill.speed;

		if (slashing && skill != SLASH) {
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
