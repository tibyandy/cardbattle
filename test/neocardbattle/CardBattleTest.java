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
		cb.ready(andyId);
		cb.ready(jeffId);
		String[] andyReadyPlayers = cb.listReadyPlayers(andyId);
		assertEquals(1, andyReadyPlayers.length);
		String[] jeffReadyPlayers = cb.listReadyPlayers(jeffId);
		assertEquals(1, jeffReadyPlayers.length);
		assertNull(cb.challenge(andyId, andyReadyPlayers[0]));
		Battle battle = cb.challenge(jeffId, jeffReadyPlayers[0]);
		assertNotNull(battle);
		do {
			cb.skill(andyId, 1);
			cb.skill(jeffId, 1);
		} while (cb.winner() == 0);
	}
}
