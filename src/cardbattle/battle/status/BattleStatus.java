package cardbattle.battle.status;

import cardbattle.battle.BattleManager;
import cardbattle.battle.execution.Character;

public class BattleStatus {

	private final CharacterStatus[] characterStatus = new CharacterStatus[2];
	private long time;
	private String event;
	
	public BattleStatus(BattleEvent event, Character[] ch) {
		this.event = event.toString();
		this.time = BattleManager.getInstance().getUptime();
		characterStatus[0] = new CharacterStatus(ch[0]);
		characterStatus[1] = new CharacterStatus(ch[1]);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(time).append('\n').append(event).append('\n');
		s.append(characterStatus[0]);
		s.append('\n');
		s.append(characterStatus[1]);
		return s.toString();
	}

	public long getTime() {
		return time;
	}
}
