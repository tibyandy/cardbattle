package cardbattle;

import static cardbattle.battle.definitions.CharacterTemplate.AYLLAN;
import static cardbattle.battle.definitions.CharacterTemplate.LASH;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cardbattle.battle.BattleManager;
import cardbattle.battle.execution.CardBattle;
import cardbattle.lobby.BattleRoom;
import cardbattle.lobby.Player;

public class BattleRoomTest {

	private BattleManager battleManager = BattleManager.getInstance();

	@Before
	public void setup() {
		battleManager.reset();
	}

	@Test
	public void testPlayerCreateAndJoinBattleRoom() {
		Player player1 = new Player("Andy");
		player1.setCharacter(AYLLAN);
		Player player2 = new Player("Jeff");
		player2.setCharacter(LASH);
		BattleRoom battleRoom = battleManager.createBattleRoom(player1);
		battleRoom.join(player2);
		CardBattle battleFromRoom = battleManager.createBattle(battleRoom);
		CardBattle battleExpected = new CardBattle(1, AYLLAN, LASH);
		assertThat(battleFromRoom, equalTo(battleExpected));
	}

	@Test
	public void testListBattleRooms() {
		List<BattleRoom> battleRooms = battleManager.getBattleRooms();
		assertThat(battleRooms.size(), is(0));

		Player player1 = new Player("Andy");
		player1.setCharacter(AYLLAN);
		Player player2 = new Player("Jeff");
		player2.setCharacter(LASH);
		BattleRoom battleExpected = battleManager.createBattleRoom(player1);
		battleRooms = battleManager.getBattleRooms();
		assertThat(battleRooms.size(), is(1));
		assertThat(battleRooms.get(0), equalTo(battleExpected));
	}

	@Test
	public void testJoinBattleRoom() {
		List<BattleRoom> battleRooms = battleManager.getBattleRooms();
		assertThat(battleRooms.size(), is(0));

		Player player1 = new Player("Andy");
		player1.setCharacter(AYLLAN);
		battleManager.createBattleRoom(player1);

		Player player2 = new Player("Jeff");
		player2.setCharacter(LASH);
		BattleRoom battleRoom = battleManager.getBattleRooms().get(0);
		battleRoom.join(player2);
		CardBattle battleFromRoom = battleManager.createBattle(battleRoom);

		CardBattle battleExpected = new CardBattle(1, AYLLAN, LASH);
		assertThat(battleFromRoom, equalTo(battleExpected));		
	}
}
