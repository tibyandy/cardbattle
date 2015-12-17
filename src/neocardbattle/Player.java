package neocardbattle;

public class Player {

	private final String name;
	private Character character;

	public Player(String name) {
		this.name = name;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public Character getCharacter() {
		return character;
	}

	public String getName() {
		return name;
	}
}
