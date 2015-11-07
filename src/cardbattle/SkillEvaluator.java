package cardbattle;

import static cardbattle.Property.BOOST_SLASH;
import static cardbattle.Property.KNOCKDOWN;

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
		source.slashing = source.getSkill().properties.contains(BOOST_SLASH) && !source.damaged;
		opponent.knockedDown = source.getSkill().properties.contains(KNOCKDOWN) && opponent.damaged;

		source.clearSkill();
	}
}
