package neocardbattle;

public class Player {

	private final String name;
	private CharacterName characterName;
	private CharacterInBattle characterInBattle;
	private Player opponent;

	public Player(String name) {
		this.name = name;
	}

	public void setCharacter(CharacterName characterName) {
		this.characterName = characterName;
	}

	public CharacterName getCharacterName() {
		return characterName;
	}

	public String getName() {
		return name;
	}
	
	public void engageInBattle(Battle battle) {
		this.characterInBattle = CharacterInBattle.createFrom(characterName);
	}

	public void engageInBattle(Battle battle, Player opponent) {
		this.opponent = opponent;
		opponent.setOpponent(this);
		characterInBattle = CharacterInBattle.createFrom(characterName);
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
