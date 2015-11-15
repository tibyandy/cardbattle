package cardbattle.battle.execution;

import static cardbattle.battle.definitions.Property.BOOST_SLASH;
import static cardbattle.battle.definitions.Property.KNOCKDOWN;
import cardbattle.battle.definitions.Skill;

public class SkillEvaluator {

	public void evaluateSkills(Character[] ch) {
		int[] speed = { ch[0].getSkillSpeed(), ch[1].getSkillSpeed() };

		if (speed[0] == speed[1]) {
			applySkillDamage(ch[0], ch[1]);
			applySkillDamage(ch[1], ch[0]);
		} else if (speed[0] > speed[1]) {
			executeSkillAndCheckDelayedEffects(ch, speed, 0);
		} else if (speed[1] > speed[0]) {
			executeSkillAndCheckDelayedEffects(ch, speed, 1);
		}

		applySkillEffects(ch[0], ch[1]);
		applySkillEffects(ch[1], ch[0]);
	}
	
	private void applySkillDamage(Character source, Character opponent) {
		opponent.hp -= source.skillDamage;
		opponent.damaged = source.skillDamage > 0;
	}

	private void executeSkillAndCheckDelayedEffects(Character[] ch, int[] speed, int attackingCharIndex) {
		int opponentCharIndex = 1 - attackingCharIndex;

		Character attackingChar = ch[attackingCharIndex];
		Character opponentChar = ch[opponentCharIndex];

		applySkillDamage(attackingChar, opponentChar);
		Integer attackingCharSkillDelay = attackingChar.getSkill().effectDelay;
		if (attackingCharSkillDelay != null) {
			if (speed[opponentCharIndex] > speed[attackingCharIndex] - attackingCharSkillDelay) {
				applySkillDamage(opponentChar, attackingChar);
			}
		}
	}

	private void applySkillEffects(Character source, Character opponent) {
		Skill sourceSkill = source.getSkill();
		source.slashing = sourceSkill.has(BOOST_SLASH) && !source.isDamaged();
		opponent.knockedDown = sourceSkill.has(KNOCKDOWN) && opponent.isDamaged();

		source.clearSkill();
	}
}
