package cardbattle;

import static cardbattle.battle.definitions.CharacterTemplate.AYLLAN;
import static cardbattle.battle.definitions.CharacterTemplate.LASH;
import static cardbattle.common.CardBattleError.INVALID_BATTLE_ID;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import cardbattle.battle.BattleManager;
import cardbattle.battle.execution.CardBattle;
import cardbattle.common.CardBattleException;

public class BattleManagerTest {

	private BattleManager battleManager = BattleManager.getInstance();

	@Before
	public void setup() {
		battleManager.reset();
	}
	
	@Test
	public void testCreateBattles() {
		CardBattle battle1 = battleManager.createBattle(LASH, AYLLAN);
		CardBattle battle2 = battleManager.createBattle(LASH, AYLLAN);
		assertThat(battle1, not(equalTo(battle2)));
	}

	@Test
	public void testGetBattles() throws CardBattleException {
		CardBattle battle1 = battleManager.createBattle(LASH, AYLLAN);
		CardBattle battle2 = battleManager.createBattle(LASH, AYLLAN);
		assertThat(battle1, equalTo(battleManager.getBattle(1)));
		assertThat(battle2, equalTo(battleManager.getBattle(2)));
	}

	@Test
	public void testGetInvalidBattle() throws CardBattleException {
		assertBattleNotExists(0);
		assertBattleNotExists(1);

		CardBattle battle1 = battleManager.createBattle(LASH, AYLLAN);
		assertThat(battle1, equalTo(battleManager.getBattle(1)));

		assertBattleNotExists(2);
	}

	private void assertBattleNotExists(int battleId) {
		try {
			battleManager.getBattle(battleId);
			fail("Expected CardBattleException");
		} catch (CardBattleException e) {
			assertThat(e.error(), is(INVALID_BATTLE_ID));
		}
	}
}
