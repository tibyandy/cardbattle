package cardbattle;

import exceptions.InvalidSkillException;

public class CardBattle {

	private Character[] ch = new Character[2];
	private SkillEvaluator skillEvaluator = new SkillEvaluator();

	public CardBattle(Character... characters) {
		for (int i = 0; i < 2; i++) {
			ch[i] = characters[i];
		}
	}

	public void setSkill(int i, Skill skill) throws InvalidSkillException {
		ch[i - 1].setSkill(skill);
	}

	public void endTurn() {
		skillEvaluator.evaluateSkills(ch);
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
