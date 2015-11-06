package cardbattle;


public class CardBattle {

	private Character[] ch = new Character[2];

	public CardBattle() {
		for (int i = 0; i < 2; i++) {
			ch[i] = new Character(this);
		}
		ch[0].setOpponent(ch[1]);
		ch[1].setOpponent(ch[0]);
	}
	
	public void setSkill(int i, Skill skill) {
		ch[i - 1].setSkill(skill);
	}

	public void evaluateSkills() {
		executeSkills();
		applySkillEffects();
	}

	private void executeSkills() {
		int[] speed = { ch[0].getSkillSpeed(), ch[1].getSkillSpeed() };
		if (speed[0] == speed[1]) {
			ch[0].executeSkill();
			ch[1].executeSkill();
		} else if (speed[0] > speed[1]) {
			executeSkillAndCheckDelayedEffects(speed, 0, 1);
		} else if (speed[1] > speed[0]) {
			executeSkillAndCheckDelayedEffects(speed, 1, 0);
		}
	}

	private void executeSkillAndCheckDelayedEffects(int[] speed, int attackingCharIndex, int opponentCharIndex) {
		Character attackingChar = ch[attackingCharIndex];
		Character opponentChar = ch[opponentCharIndex];

		attackingChar.executeSkill();
		Integer attackingCharSkillDelay = attackingChar.getSkill().effectDelay;
		if (attackingCharSkillDelay != null) {
			if (speed[opponentCharIndex] > speed[attackingCharIndex] - attackingCharSkillDelay) {
				opponentChar.executeSkill();
			}
		}
	}

	private void applySkillEffects() {
		ch[0].applySkillEffects();
		ch[1].applySkillEffects();
	}

	public int hp(int i) {
		return ch[i - 1].getHP();
	}

	public Integer winner() {
		if (ch[0].getHP() <= 0) {
			if (ch[1].getHP() <= 0) {
				return 0;
			} else {
				return 2;
			}
		} else if (ch[1].getHP() <= 0) {
			return 1;
		} else {
			return null;
		}
	}
}
