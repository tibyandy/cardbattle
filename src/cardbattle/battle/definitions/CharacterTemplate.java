package cardbattle.battle.definitions;

import static cardbattle.common.CardBattleError.INVALID_CHARACTER_NAME;
import static cardbattle.common.CardBattleException.error;

import java.util.HashMap;
import java.util.Map;

import cardbattle.common.CardBattleException;

public enum CharacterTemplate implements CharacterTemplateInterface {
	LASH("Lash", 25, Skill.SLASH, Skill.TRIPLE_SLASH, Skill.HEAVY_SLASH),
	AYLLAN("Ayllan", 20, Skill.MIND_BLAST, Skill.FIREWALL, Skill.METEOR);

	private final String name;
	private final int hp;
	private final Skill[] skills;
	private static final Map<String, CharacterTemplate> charactersByName = new HashMap<>();

	private CharacterTemplate(String name, int hp, Skill... skills) {
		this.name = name;
		this.hp = hp;
		this.skills = skills;
	}

	public String getName() {
		return name;
	}
	public int getHP() {
		return hp;
	}
	public Skill[] getSkills() {
		return skills;
	}

	public static CharacterTemplate get(String s) throws CardBattleException {
		if (charactersByName.isEmpty()) {
			for (CharacterTemplate chara : CharacterTemplate.values()) {
				charactersByName.put(chara.toString(), chara);
			}
		}
		String charName = s.toUpperCase();
		CharacterTemplate chara = charactersByName.get(charName);
		if (chara == null) {
			throw error(INVALID_CHARACTER_NAME, charName);
		}
		return chara;
	}
}
