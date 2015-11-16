package cardbattle.player;

import cardbattle.battle.definitions.CharacterTemplate;

public class Player {

	private String name;
	private CharacterTemplate character;

	public Player(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			return name == ((Player) obj).name;
		} catch (NullPointerException | ClassCastException e) {
			return false;
		}
	}

	public void setCharacter(CharacterTemplate character) {
		this.character = character;
	}

	public CharacterTemplate getCharacter() {
		return character;
	}
}
