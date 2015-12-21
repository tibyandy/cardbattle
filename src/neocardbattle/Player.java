package neocardbattle;

public class Player {

	private final String name;
	private Character character;
	private CharacterInBattle characterInBattle;
	private Player opponent;

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
	
	public void engageInBattle(Battle battle) {
		this.characterInBattle = CharacterInBattle.createFrom(battle, character);
	}

	public void engageInBattle(Battle battle, Player opponent) {
		this.opponent = opponent;
		opponent.setOpponent(this);
		characterInBattle = CharacterInBattle.createFrom(battle, character);
		CharacterInBattle opponentCharacter = opponent.getCharacterInBattle();
		characterInBattle.setOpponent(opponentCharacter);
		opponentCharacter.setOpponent(characterInBattle);
	}

	private void setOpponent(Player opponent) {
		this.opponent = opponent;
	}

	public CharacterInBattle getCharacterInBattle() {
		return characterInBattle;
	}
}
