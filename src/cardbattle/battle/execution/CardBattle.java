package cardbattle.battle.execution;

import static cardbattle.common.CardBattleError.INVALID_CHARACTER_ID;
import static cardbattle.common.CardBattleException.error;
import cardbattle.battle.definitions.CharacterTemplateInterface;
import cardbattle.battle.definitions.Skill;
import cardbattle.battle.status.BattleEvent;
import cardbattle.battle.status.BattleStatus;
import cardbattle.common.CardBattleException;

public class CardBattle {

	private Character[] ch = new Character[2];
	private SkillEvaluator skillEvaluator = new SkillEvaluator();
	public final int id;
	private BattleStatus battleStatus;

	public CardBattle(CharacterTemplateInterface... characters) {
		this(0, characters);
	}

	public CardBattle(int id, CharacterTemplateInterface... characters) {
		this.id = id;
		for (int i = 0; i < 2; i++) {
			ch[i] = new Character(characters[i]);
		}
		battleStatus = new BattleStatus(BattleEvent.Battle_Created, ch);
	}

	public void setSkill(int i, Skill skill) throws CardBattleException {
		if (i == 1 || i == 2) {
			ch[i - 1].setSkill(skill);
		} else {
			throw error(INVALID_CHARACTER_ID, i);
		}
		BattleEvent event = i == 1 ? BattleEvent.Skill_Set_1 : BattleEvent.Skill_Set_2;
		battleStatus = new BattleStatus(event, ch);
	}

	public CardBattle endTurn() {
		skillEvaluator.evaluateSkills(ch);
		battleStatus = new BattleStatus(BattleEvent.End_Turn, ch);
		return this;
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

	@Override
	public boolean equals(Object obj) {
		try {
			if (this.id != 0) {
				return ((CardBattle) obj).id == this.id;
			} else {
				return this == ((CardBattle) obj);
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String toString() {
		return "CardBattle[" + id + "]";
	}

	public BattleStatus getBattleStatus() {
		return battleStatus;
	}
}
