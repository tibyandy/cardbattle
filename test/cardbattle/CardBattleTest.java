package cardbattle;

import static cardbattle.battle.definitions.CharacterTemplate.AYLLAN;
import static cardbattle.battle.definitions.CharacterTemplate.LASH;
import static cardbattle.battle.definitions.Skill.ATTACK;
import static cardbattle.battle.definitions.Skill.ATTACK_D_SPEED;
import static cardbattle.battle.definitions.Skill.ATTACK_E_SPEED;
import static cardbattle.battle.definitions.Skill.ATTACK_QUICK;
import static cardbattle.battle.definitions.Skill.ATTACK_SLOW;
import static cardbattle.battle.definitions.Skill.FIREWALL;
import static cardbattle.battle.definitions.Skill.HEAVY_SLASH;
import static cardbattle.battle.definitions.Skill.METEOR;
import static cardbattle.battle.definitions.Skill.MIND_BLAST;
import static cardbattle.battle.definitions.Skill.SLASH;
import static cardbattle.battle.definitions.Skill.TRIPLE_SLASH;
import static cardbattle.common.CardBattleError.INVALID_SKILL_NAME;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import cardbattle.battle.definitions.CharacterTemplateInterface;
import cardbattle.battle.definitions.DummyCharacterTemplate;
import cardbattle.battle.definitions.Skill;
import cardbattle.battle.execution.CardBattle;
import cardbattle.common.CardBattleException;

public class CardBattleTest {

	private static final CharacterTemplateInterface DUMMY = new DummyCharacterTemplate();

	private int calcLife(CharacterTemplateInterface chara, Skill... skills) {
		int hp = chara.getHP();
		for (Skill skill : skills) {
			hp -= skill.damage;
		}
		return hp;
	}

	@Test
	public void testSkill() throws Exception {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, ATTACK_QUICK);
		b.endTurn();
		assertThat(b.hp(2), is(calcLife(DUMMY, ATTACK_QUICK)));
		assertThat(b.hp(1), is(calcLife(DUMMY)));
	}

	@Test
	public void testSkill2() throws Exception {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(2, ATTACK);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY, ATTACK)));
		assertThat(b.hp(2), is(calcLife(DUMMY)));
	}

	@Test
	public void testBattleOver() throws Exception {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(2, ATTACK_QUICK);
		b.endTurn();
		b.setSkill(2, ATTACK);
		b.endTurn();
		b.setSkill(2, ATTACK_SLOW);
		b.endTurn();
		assertThat(b.winner(), is(2));
	}

	@Test
	public void testBattleOver2() throws Exception {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, ATTACK_SLOW);
		b.endTurn();
		b.setSkill(1, ATTACK_SLOW);
		b.endTurn();
		assertThat(b.winner(), is(1));
	}

	@Test
	public void testBattleDraw() throws Exception {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(2, ATTACK_SLOW);
		b.setSkill(1, ATTACK_SLOW);
		b.endTurn();
		b.setSkill(1, ATTACK_SLOW);
		b.setSkill(2, ATTACK_SLOW);
		b.endTurn();
		assertThat(b.winner(), is(0));
	}

	@Test
	public void testSkillSpeed1() throws Exception {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, ATTACK_QUICK);
		b.setSkill(2, ATTACK);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY)));
		assertThat(b.hp(2), is(calcLife(DUMMY, ATTACK_QUICK)));
		assertThat(b.winner(), nullValue());
	}

	@Test
	public void testSkillSpeed2() throws Exception {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, ATTACK_SLOW);
		b.setSkill(2, ATTACK);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY, ATTACK)));
		assertThat(b.hp(2), is(calcLife(DUMMY)));
		assertThat(b.winner(), nullValue());
	}

	@Test
	public void testBattleNotOver() throws Exception {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		assertThat(b.winner(), nullValue());
	}

	@Test
	public void testSlashSlash() throws Exception {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, SLASH);
		b.setSkill(2, ATTACK);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY, ATTACK)));
		assertThat(b.hp(2), is(calcLife(DUMMY, SLASH)));
		b.setSkill(1, SLASH);
		b.setSkill(2, ATTACK_QUICK);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY, ATTACK, ATTACK_QUICK)));
		assertThat(b.hp(2), is(calcLife(DUMMY, SLASH)));
		assertThat(b.winner(), nullValue());
	}

	@Test
	public void testSlashHeavySlashHeavySlash() throws Exception {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, SLASH);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY)));
		assertThat(b.hp(2), is(calcLife(DUMMY, SLASH)));
		b.setSkill(1, HEAVY_SLASH); // Mais rápido
		b.setSkill(2, HEAVY_SLASH);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY)));
		assertThat(b.hp(2), is(calcLife(DUMMY, SLASH, HEAVY_SLASH)));
		b.setSkill(1, HEAVY_SLASH);
		b.setSkill(2, SLASH); // Mesma velocidade, pq Knockdown
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY, SLASH)));
		assertThat(b.hp(2), is(calcLife(DUMMY, SLASH, HEAVY_SLASH, HEAVY_SLASH)));
		assertThat(b.winner(), nullValue());
	}

	@Test
	public void testHeavySlashKnockdownDrawDraw() throws Exception {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, HEAVY_SLASH);
		b.endTurn();
		b.setSkill(1, TRIPLE_SLASH);
		b.setSkill(2, ATTACK_QUICK);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY, ATTACK_QUICK)));
		assertThat(b.hp(2), is(calcLife(DUMMY, HEAVY_SLASH, TRIPLE_SLASH)));
		b.setSkill(1, ATTACK);
		b.setSkill(2, ATTACK);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY, ATTACK_QUICK, ATTACK)));
		assertThat(b.hp(2), is(calcLife(DUMMY, HEAVY_SLASH, TRIPLE_SLASH, ATTACK)));
	}

	@Test
	public void testHeavySlashKnockdownWin() throws Exception {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, HEAVY_SLASH);
		b.endTurn();
		b.setSkill(1, HEAVY_SLASH);
		b.setSkill(2, ATTACK_SLOW);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY)));
		assertThat(b.hp(2), is(calcLife(DUMMY, HEAVY_SLASH, HEAVY_SLASH)));
		assertThat(b.winner(), nullValue());
	}

	@Test
	public void testHeavySlashVsHeavySlash() throws Exception {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, HEAVY_SLASH);
		b.setSkill(2, HEAVY_SLASH);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY, HEAVY_SLASH)));
		assertThat(b.hp(2), is(calcLife(DUMMY, HEAVY_SLASH)));
		b.setSkill(1, HEAVY_SLASH);
		b.setSkill(2, HEAVY_SLASH);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY, HEAVY_SLASH, HEAVY_SLASH)));
		assertThat(b.hp(2), is(calcLife(DUMMY, HEAVY_SLASH, HEAVY_SLASH)));
		assertThat(b.winner(), nullValue());
	}

	@Test
	public void testHeavySlashUnsuccessfull() throws Exception {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, HEAVY_SLASH);
		b.setSkill(2, ATTACK_QUICK);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY, ATTACK_QUICK)));
		assertThat(b.hp(2), is(calcLife(DUMMY)));
		b.setSkill(1, HEAVY_SLASH);
		b.setSkill(2, ATTACK);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY, ATTACK_QUICK, ATTACK)));
		assertThat(b.hp(2), is(calcLife(DUMMY)));
		assertThat(b.winner(), nullValue());
	}

	@Test
	public void testSlashUnsuccessfull() throws Exception {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, SLASH);
		b.setSkill(2, ATTACK_QUICK);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY, ATTACK_QUICK)));
		assertThat(b.hp(2), is(calcLife(DUMMY)));
		b.setSkill(1, TRIPLE_SLASH);
		b.setSkill(2, ATTACK_QUICK);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY, ATTACK_QUICK, ATTACK_QUICK)));
		assertThat(b.hp(2), is(calcLife(DUMMY)));
	}

	@Test
	public void testSlashTripleSlash() throws Exception {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, SLASH);
		b.setSkill(2, ATTACK_SLOW);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY)));
		assertThat(b.hp(2), is(calcLife(DUMMY, SLASH)));
		b.setSkill(1, TRIPLE_SLASH);
		b.setSkill(2, ATTACK_QUICK);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY, ATTACK_QUICK)));
		assertThat(b.hp(2), is(calcLife(DUMMY, SLASH, TRIPLE_SLASH)));
	}

	@Test
	public void testMeteorSlash() throws Exception {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, METEOR);
		b.setSkill(2, SLASH);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY, SLASH)));
		assertThat(b.hp(2), is(calcLife(DUMMY, METEOR)));
	}

	@Test
	public void testMeteorHeavySlash() throws Exception {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, METEOR);
		b.setSkill(2, HEAVY_SLASH);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY, HEAVY_SLASH)));
		assertThat(b.hp(2), is(calcLife(DUMMY, METEOR)));
	}

	@Test
	public void testMeteorQuickAttack() throws Exception {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, METEOR);
		b.setSkill(2, ATTACK_QUICK);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY, ATTACK_QUICK)));
		assertThat(b.hp(2), is(calcLife(DUMMY)));
	}

	@Test
	public void testMeteorAttackDSpeed() throws Exception {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, METEOR);
		b.setSkill(2, ATTACK_D_SPEED);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY)));
		assertThat(b.hp(2), is(calcLife(DUMMY, METEOR)));
	}

	@Test
	public void testMeteorAttackESpeed() throws Exception {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, METEOR);
		b.setSkill(2, ATTACK_E_SPEED);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY)));
		assertThat(b.hp(2), is(calcLife(DUMMY, METEOR)));
	}

	@Test
	public void testCharacterSkillsValid() throws Exception {
		CardBattle b = new CardBattle(LASH, AYLLAN);
		b.setSkill(1, SLASH);
		b.setSkill(2, MIND_BLAST);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(LASH, MIND_BLAST)));
		assertThat(b.hp(2), is(calcLife(AYLLAN, SLASH)));
	}

	@Test
	public void testCharacterSkillsInvalid() throws Exception {
		CardBattle b = new CardBattle(LASH, AYLLAN);

		b.setSkill(1, SLASH);
		b.setSkill(2, MIND_BLAST);
		setInvalidSkill(b, 1, FIREWALL);
		setInvalidSkill(b, 2, TRIPLE_SLASH);		
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(LASH, MIND_BLAST)));
		assertThat(b.hp(2), is(calcLife(AYLLAN, SLASH)));

		setInvalidSkill(b, 1, FIREWALL);
		setInvalidSkill(b, 2, TRIPLE_SLASH);		
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(LASH, MIND_BLAST)));
		assertThat(b.hp(2), is(calcLife(AYLLAN, SLASH)));
	}

	private void setInvalidSkill(CardBattle b, int c, Skill firewall) {
		try {
			b.setSkill(c, firewall);
			fail("Expected CardBattleException");
		} catch (CardBattleException e) {
			assertThat(e.error(), is(INVALID_SKILL_NAME));
		}
	}

	@Test
	public void testCardBattleId() throws Exception {
		CardBattle b1 = new CardBattle(1, DUMMY, DUMMY);
		CardBattle b2 = new CardBattle(2, DUMMY, DUMMY);
		assertThat(b1, CoreMatchers.not(CoreMatchers.equalTo(b2)));
		assertThat(b1, CoreMatchers.equalTo(b1));

		CardBattle b01 = new CardBattle(DUMMY, DUMMY);
		CardBattle b02 = new CardBattle(DUMMY, DUMMY);
		assertThat(b01, CoreMatchers.equalTo(b01));
		assertThat(b02, CoreMatchers.equalTo(b02));
		assertThat(b01, CoreMatchers.not(CoreMatchers.equalTo(b02)));
	}
}
