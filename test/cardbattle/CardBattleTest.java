package cardbattle;

import static cardbattle.CharacterTemplate.AYLLAN;
import static cardbattle.CharacterTemplate.LASH;
import static cardbattle.Skill.ATTACK;
import static cardbattle.Skill.ATTACK_D_SPEED;
import static cardbattle.Skill.ATTACK_E_SPEED;
import static cardbattle.Skill.ATTACK_QUICK;
import static cardbattle.Skill.ATTACK_SLOW;
import static cardbattle.Skill.FIREWALL;
import static cardbattle.Skill.HEAVY_SLASH;
import static cardbattle.Skill.METEOR;
import static cardbattle.Skill.MIND_BLAST;
import static cardbattle.Skill.SLASH;
import static cardbattle.Skill.TRIPLE_SLASH;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import cardbattle.exceptions.InvalidSkillException;

public class CardBattleTest {

	private static final CharacterTemplateInterface DUMMY = new TestCharacterTemplate();

	private int calcLife(CharacterTemplateInterface chara, Skill... skills) {
		int hp = chara.getHP();
		for (Skill skill : skills) {
			hp -= skill.damage;
		}
		return hp;
	}

	@Test
	public void testSkill() {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, ATTACK_QUICK);
		b.endTurn();
		assertThat(b.hp(2), is(calcLife(DUMMY, ATTACK_QUICK)));
		assertThat(b.hp(1), is(calcLife(DUMMY)));
	}

	@Test
	public void testSkill2() {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(2, ATTACK);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY, ATTACK)));
		assertThat(b.hp(2), is(calcLife(DUMMY)));
	}

	@Test
	public void testBattleOver() {
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
	public void testBattleOver2() {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, ATTACK_SLOW);
		b.endTurn();
		b.setSkill(1, ATTACK_SLOW);
		b.endTurn();
		assertThat(b.winner(), is(1));
	}

	@Test
	public void testBattleDraw() {
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
	public void testSkillSpeed1() {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, ATTACK_QUICK);
		b.setSkill(2, ATTACK);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY)));
		assertThat(b.hp(2), is(calcLife(DUMMY, ATTACK_QUICK)));
		assertThat(b.winner(), nullValue());
	}

	@Test
	public void testSkillSpeed2() {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, ATTACK_SLOW);
		b.setSkill(2, ATTACK);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY, ATTACK)));
		assertThat(b.hp(2), is(calcLife(DUMMY)));
		assertThat(b.winner(), nullValue());
	}

	@Test
	public void testBattleNotOver() {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		assertThat(b.winner(), nullValue());
	}

	@Test
	public void testSlashSlash() {
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
	public void testSlashHeavySlash() {
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
		assertThat(b.winner(), nullValue());
	}

	@Test
	public void testHeavySlashKnockdownDraw() {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, HEAVY_SLASH);
		b.endTurn();
		b.setSkill(1, HEAVY_SLASH);
		b.setSkill(2, ATTACK);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY, ATTACK)));
		assertThat(b.hp(2), is(calcLife(DUMMY, HEAVY_SLASH, HEAVY_SLASH)));
		assertThat(b.winner(), nullValue());
	}

	@Test
	public void testHeavySlashKnockdownWin() {
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
	public void testHeavySlashVsHeavySlash() {
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
	public void testHeavySlashUnsuccessfull() {
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
	public void testSlashUnsuccessfull() {
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
	public void testSlashTripleSlash() {
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
	public void testMeteorSlash() {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, METEOR);
		b.setSkill(2, SLASH);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY, SLASH)));
		assertThat(b.hp(2), is(calcLife(DUMMY, METEOR)));
	}

	@Test
	public void testMeteorHeavySlash() {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, METEOR);
		b.setSkill(2, HEAVY_SLASH);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY, HEAVY_SLASH)));
		assertThat(b.hp(2), is(calcLife(DUMMY, METEOR)));
	}

	@Test
	public void testMeteorQuickAttack() {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, METEOR);
		b.setSkill(2, ATTACK_QUICK);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY, ATTACK_QUICK)));
		assertThat(b.hp(2), is(calcLife(DUMMY)));
	}

	@Test
	public void testMeteorAttackDSpeed() {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, METEOR);
		b.setSkill(2, ATTACK_D_SPEED);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY)));
		assertThat(b.hp(2), is(calcLife(DUMMY, METEOR)));
	}

	@Test
	public void testMeteorAttackESpeed() {
		CardBattle b = new CardBattle(DUMMY, DUMMY);
		b.setSkill(1, METEOR);
		b.setSkill(2, ATTACK_E_SPEED);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(DUMMY)));
		assertThat(b.hp(2), is(calcLife(DUMMY, METEOR)));
	}

	@Test
	public void testCharacterSkillsValid() {
		CardBattle b = new CardBattle(LASH, AYLLAN);
		b.setSkill(1, SLASH);
		b.setSkill(2, MIND_BLAST);
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(LASH, MIND_BLAST)));
		assertThat(b.hp(2), is(calcLife(AYLLAN, SLASH)));
	}

	@Test
	public void testCharacterSkillsInvalid() {
		CardBattle b = new CardBattle(LASH, AYLLAN);
		b.setSkill(1, SLASH);
		b.setSkill(2, MIND_BLAST);
		try {
			b.setSkill(1, FIREWALL);
			org.junit.Assert.fail("Expected InvalidSkillException");
		} catch (InvalidSkillException e) {
			// ok
		}
		try {
			b.setSkill(2, Skill.TRIPLE_SLASH);
			org.junit.Assert.fail("Expected InvalidSkillException");
		} catch (InvalidSkillException e) {
			// ok
		}		
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(LASH, MIND_BLAST)));
		assertThat(b.hp(2), is(calcLife(AYLLAN, SLASH)));
		try {
			b.setSkill(1, FIREWALL);
			org.junit.Assert.fail("Expected InvalidSkillException");
		} catch (InvalidSkillException e) {
			// ok
		}
		try {
			b.setSkill(2, Skill.TRIPLE_SLASH);
			org.junit.Assert.fail("Expected InvalidSkillException");
		} catch (InvalidSkillException e) {
			// ok
		}		
		b.endTurn();
		assertThat(b.hp(1), is(calcLife(LASH, MIND_BLAST)));
		assertThat(b.hp(2), is(calcLife(AYLLAN, SLASH)));
	}
}
