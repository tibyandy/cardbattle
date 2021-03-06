package cardbattle.battle.execution;

import static cardbattle.battle.definitions.Property.SLASH_BOOSTED;
import static cardbattle.common.CardBattleError.INVALID_SKILL_NAME;
import static cardbattle.common.CardBattleException.error;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cardbattle.battle.BattleManager;
import cardbattle.battle.definitions.CharacterTemplateInterface;
import cardbattle.battle.definitions.Skill;
import cardbattle.common.CardBattleException;

public class Character {

	int skillDamage = 0;
	int skillSpeed = 0;
	int hp;

	boolean damaged = false;
	boolean slashing = false;
	boolean knockedDown = false;

	private Skill skill = Skill.NONE;
	private final List<Skill> skills = new ArrayList<>();
	private CharacterTemplateInterface template;

	private long skillSetTime = 0;
	
	public Character() {
		hp = 30;
	}

	public Character(CharacterTemplateInterface character) {
		template = character;
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

	public void setSkill(Skill skill) throws CardBattleException {
		if (!skills.contains(skill)) {
			throw error(INVALID_SKILL_NAME, skill.name().toUpperCase());
		}
		this.skill = skill;
		skillDamage = skill.damage;
		skillSpeed = skill.speed;

		if (isSlashing() && skill.has(SLASH_BOOSTED)) {
			skillSpeed++;
		}
		if (isKnockedDown()) {
			skillSpeed--;
		}
		skillSetTime = BattleManager.getInstance().getUptime();
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

	public String getName() {
		return template.toString();
	}

	public long getSkillSetTime() {
		return skillSetTime;
	}

	public boolean isDamaged() {
		return damaged;
	}

	public boolean isSlashing() {
		return slashing;
	}

	public boolean isKnockedDown() {
		return knockedDown;
	}
}
