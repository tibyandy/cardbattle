package cardbattle.lobby;

import cardbattle.battle.execution.CardBattle;
import cardbattle.player.Player;

public class BattleRoom {

	public Player host;
	public Player guest;
	private CardBattle cardBattle;

	public BattleRoom(Player host) {
		this.host = host;
	}

	public void join(Player guest) {
		this.guest = guest;
	}

	public void setBattle(CardBattle cardBattle) {
		this.cardBattle = cardBattle;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			return this == obj;
		} catch (NullPointerException | ClassCastException e) {
			return false;
		}
	}
}
