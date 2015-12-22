package neocardbattle;

import java.util.ArrayList;
import java.util.List;

public class CharacterInBattle {

	private Skill skill;
	private CharacterInBattle opponent;
	private boolean ready;
	private Character characterTemplate;
	private final List<Energy> energy = new ArrayList<>();

	private int hp;
	
	private CharacterInBattle(Character characterTemplate) {
		this.characterTemplate = characterTemplate;
		hp = characterTemplate.getHP();
		energy.clear();
		energy.addAll(characterTemplate.getEnergy());
	}
	
	public static CharacterInBattle createFrom(CharacterName characterName) {
		return new CharacterInBattle(Character.get(characterName));
	}

	public void setSkill(SkillName skillName) {
		if (skillName == null) {
			this.skill = null;
		} else if (characterTemplate.getSkills().contains(skillName)) {
			throw new CardBattleRuntimeException(CardBattleError.INVALID_CHARACTER_SKILL);
		} else {
			this.skill = Skill.get(skillName);
		}
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

	public int getHP() {
		return hp;
	}

	public Character getTemplate() {
		return characterTemplate;
	}

	public int getEnergy() {
		return energy.size();
	}

	public List<Energy> getEnergies() {
		return energy;
	}
}
