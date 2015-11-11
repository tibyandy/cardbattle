package cardbattle;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BattleManagerTest {

	@Before
	public void setup() {
		BattleManager.reset();
	}
	
	@Test
	public void testCreateBattles() {
		CardBattle battle1 = BattleManager.createBattle(CharacterTemplate.LASH, CharacterTemplate.AYLLAN);
		CardBattle battle2 = BattleManager.createBattle(CharacterTemplate.LASH, CharacterTemplate.AYLLAN);
		assertThat(battle1, not(equalTo(battle2)));
	}

	@Test
	public void testGetBattles() {
		CardBattle battle1 = BattleManager.createBattle(CharacterTemplate.LASH, CharacterTemplate.AYLLAN);
		CardBattle battle2 = BattleManager.createBattle(CharacterTemplate.LASH, CharacterTemplate.AYLLAN);
		assertThat(battle1, equalTo(BattleManager.getBattle(1)));
		assertThat(battle2, equalTo(BattleManager.getBattle(2)));
	}

	@Test
	public void testGetInvalidBattle() {
		assertBattleNotExists(0);
		assertBattleNotExists(1);

		CardBattle battle1 = BattleManager.createBattle(CharacterTemplate.LASH, CharacterTemplate.AYLLAN);
		assertThat(battle1, equalTo(BattleManager.getBattle(1)));

		assertBattleNotExists(2);
	}

	private void assertBattleNotExists(int battleId) {
		try {
			BattleManager.getBattle(battleId);
			Assert.fail("Expected IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
			// ok
		}
	}
}
