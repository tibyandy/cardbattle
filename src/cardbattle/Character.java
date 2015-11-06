package cardbattle;

import static cardbattle.Skill.HEAVY_SLASH;
import static cardbattle.Skill.SLASH;

public class Character extends BattleEntity {

	private boolean slashing = false;
	private boolean knockedDown = false;

	private Skill skill = Skill.NONE;
	private Character opponent = null;
	private CardBattle battle;

	public Character(CardBattle battle) {
		hp = 30;
		this.battle = battle;
	}

	public Character getOpponent() {
		return opponent;
	}
	public void setOpponent(Character opponent) {
		this.opponent = opponent;
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

	public void executeSkill() {
		opponent.hp -= skillDamage;
		opponent.damaged = skillDamage > 0;
	}

	public int getHP() {
		return hp;
	}

	public boolean isSlashing() {
		return slashing;
	}
	public void setSlashing(boolean slashing) {
		this.slashing = slashing;
	}

	public void applySkillEffects() {
		if (skill == SLASH && !damaged) {
			slashing = true;
		} else if (skill == HEAVY_SLASH && opponent.damaged) {
			opponent.knockedDown = true;
		}
		skill = Skill.NONE;
		skillDamage = 0;
		skillSpeed = 0;
	}

	public int getSkillDamage() {
		return skillDamage;
	}
	public void nullifySkillDamage() {
		this.skillDamage = 0;
	}
}
