package neocardbattle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CharacterInBattle {

	private Skill skill;
	private final Set<Skill> knownSkills = new HashSet<Skill>();
	private CharacterInBattle opponent;
	private Battle battle;
	private boolean ready;

	private CharacterInBattle(Battle battle, Skill[] skills) {
		this.battle = battle;
		knownSkills.clear();
		knownSkills.addAll(Arrays.asList(skills));
	}
	
	public static CharacterInBattle createFrom(Battle battle, Character character) {
		return new CharacterInBattle(battle, character.getSkills());
	}

	public void setSkill(Skill skill) {
		if (knownSkills.contains(skill)) {
			throw new CardBattleRuntimeException(CardBattleError.INVALID_CHARACTER_SKILL);
		}
		this.skill = skill;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setOpponent(CharacterInBattle opponent) {
		this.opponent = opponent;
	}

	public CharacterInBattle getOpponent() {
		return opponent;
	}

	public void setReady() {
		if (skill == null) {
			throw new CardBattleRuntimeException(CardBattleError.CHARACTER_SKILL_NOT_SET);
		}
		ready = true;
	}

	public boolean isReady() {
		return ready;
	}

	public void clearReady() {
		ready = false;
	}
}
