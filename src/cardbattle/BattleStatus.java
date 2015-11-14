package cardbattle;

public class BattleStatus {

	private final CharacterStatus[] characterStatus = new CharacterStatus[2];
	private long time;
	
	public BattleStatus(Character[] ch) {
		this.time = BattleManager.getUptime();
		characterStatus[0] = new CharacterStatus(ch[0]);
		characterStatus[1] = new CharacterStatus(ch[1]);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(time).append('\n');
		s.append(characterStatus[0]);
		s.append('\n');
		s.append(characterStatus[1]);
		return s.toString();
	}

	public long getTime() {
		return time;
	}
}
