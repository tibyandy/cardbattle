package neocardbattle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class CardBattleTest {

	@Test
	public void flowTest() throws CardBattleRuntimeException {
		CardBattleEngine cb = new CardBattleEngine();
		String andyId = cb.login("Andy");
		String jeffId = cb.login("Jeff");
		cb.select(andyId, "Ayllan");
		cb.select(jeffId, "Lash");
		cb.readyForBattle(andyId);
		cb.readyForBattle(jeffId);
		String[] andyReadyPlayers = cb.listReadyPlayers(andyId);
		assertEquals(1, andyReadyPlayers.length);
		String[] jeffReadyPlayers = cb.listReadyPlayers(jeffId);
		assertEquals(1, jeffReadyPlayers.length);
		assertNull(cb.challenge(andyId, andyReadyPlayers[0]));
		Battle battle = cb.challenge(jeffId, jeffReadyPlayers[0]);
		assertNotNull(battle);
		do {
			cb.skill(andyId, Skill.SLASH);
			cb.skill(jeffId, Skill.MIND_BLAST);
			cb.ready(andyId);
			cb.ready(jeffId);
		} while (cb.winner(andyId) == 0);
	}

	@Test
	public void serverThreadTest() throws InterruptedException {
		CardBattleServer cs = new CardBattleServer();
		String playerToken = cs.requestToken("Andy");
		System.out.println("Connect");
		cs.connect(playerToken);
		Thread.sleep(100L);
		System.out.println("Com 1");
		cs.sendCommand(playerToken, "com 1");
		Thread.sleep(100L);
		System.out.println("Com 2");
		cs.sendCommand(playerToken, "com 2");
		Thread.sleep(100L);
		System.out.println("Com 3");
		cs.sendCommand(playerToken, "com 3");
		Thread.sleep(100L);
		System.out.println("Com 4");
		cs.sendCommand(playerToken, "com 4");
		Thread.sleep(100L);
		System.out.println("Disconnect");
		cs.disconnect(playerToken);
		Thread.sleep(100L);
	}
}
