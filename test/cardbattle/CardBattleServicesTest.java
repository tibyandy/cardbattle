package cardbattle;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import cardbattle.service.CardBattleServiceResult;
import cardbattle.service.CardBattleServices;

public class CardBattleServicesTest {

	private final CardBattleServices services = CardBattleServices.getInstance();

	@Test
	public void testWelcome() {
		CardBattleServiceResult result = services.welcome();
		Assert.assertThat(result.success(), CoreMatchers.is(true));
	}
}
