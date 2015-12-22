package neocardbattle;

import static neocardbattle.CharacterName.AYLLAN;
import static neocardbattle.CharacterName.LASH;
import static neocardbattle.Energy.BLUE;
import static neocardbattle.Energy.RED;
import static neocardbattle.SkillName.FIREWALL;
import static neocardbattle.SkillName.HEAVY_SLASH;
import static neocardbattle.SkillName.METEOR;
import static neocardbattle.SkillName.MIND_BLAST;
import static neocardbattle.SkillName.SLASH;
import static neocardbattle.SkillName.TRIPLE_SLASH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Character {

	private static final Map<CharacterName, Character> ALL_CHARACTERS = new HashMap<>();

	static {
		Character.name(LASH).hp(25).energy(RED, RED)
			.skills(SLASH, HEAVY_SLASH, TRIPLE_SLASH);

		Character.name(AYLLAN).hp(18).energy(BLUE, BLUE)
			.skills(MIND_BLAST, FIREWALL, METEOR);
	}

	
	private CharacterName characterName;
	private int hp;
	private List<Energy> energy = new ArrayList<>();
	private List<SkillName> skills;
	
	public static Character get(CharacterName characterName) {
		return ALL_CHARACTERS.get(characterName);
	}
	
	private static Character name(CharacterName characterName) {
		Character newCharacter = new Character(characterName);
		ALL_CHARACTERS.put(characterName, newCharacter);
		return newCharacter;
	}
	
	private Character(CharacterName character) {
		this.characterName = character;
	}

	private Character hp(int hp) {
		this.hp = hp;
		return this;
	}

	private Character skills(SkillName...skills) {
		this.skills = Arrays.asList(skills);
		return this;
	}
	
	private Character energy(Energy...energy) {
		this.energy = Arrays.asList(energy);
		return this;
	}

	public CharacterName getCharacter() {
		return characterName;
	}
	public int getHP() {
		return hp;
	}
	public List<Energy> getEnergy() {
		return energy;
	}
	public List<SkillName> getSkills() {
		return skills;
	}
}
