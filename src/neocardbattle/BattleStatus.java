package neocardbattle;

import static neocardbattle.BattlePhase.SKILL_EVALUATION;

public class BattleStatus {

	public static void showStatus(Battle battle) {
		System.out.print(battle.getPhase() + " [");
		for (Energy e : battle.getTableEnergy()) {
			System.out.print(" " + e.name());
		}
		System.out.println(" ]");
		for (CharacterInBattle character : battle.getCharacters()) {
			System.out.print("  H:" + character.getHP());
			System.out.print(" E:" + character.getEnergy());
			if (battle.getPhase() == SKILL_EVALUATION || character.isReady()) {
				System.out.print(" OK!");
			} else if (character.getSkill() != null) {
				System.out.print(" Set");
			} else {
				System.out.print("    ");
			}
			if (battle.getPhase() == SKILL_EVALUATION) {
				System.out.print(" <" + character.getSkill() + ">");
			}
			System.out.print(" " + character.getTemplate().getCharacter().toString());
			System.out.println();
		}
		System.out.println();
		
	}

}
