package cardbattle;

import static cardbattle.Skill.ATTACK;
import static cardbattle.Skill.ATTACK_D_SPEED;
import static cardbattle.Skill.ATTACK_E_SPEED;
import static cardbattle.Skill.ATTACK_QUICK;
import static cardbattle.Skill.ATTACK_SLOW;
import static cardbattle.Skill.HEAVY_SLASH;
import static cardbattle.Skill.METEOR;
//import static cardbattle.Skill.SKILL_FIREBALL;
import static cardbattle.Skill.SLASH;
import static cardbattle.Skill.TRIPLE_SLASH;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CardBattleTest {

	@Test
	public void testSkill() {
		CardBattle b = new CardBattle();
		b.setSkill(1, ATTACK_QUICK);
		b.endTurn();
		assertThat(b.hp(2), is(25));
		assertThat(b.hp(1), is(30));
	}

	@Test
	public void testSkill2() {
		CardBattle b = new CardBattle();
		b.setSkill(2, ATTACK);
		b.endTurn();
		assertThat(b.hp(1), is(20));
		assertThat(b.hp(2), is(30));
	}

	@Test
	public void testBattleOver() {
		CardBattle b = new CardBattle();
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
		CardBattle b = new CardBattle();
		b.setSkill(1, ATTACK_SLOW);
		b.endTurn();
		b.setSkill(1, ATTACK_SLOW);
		b.endTurn();
		assertThat(b.winner(), is(1));
	}

	@Test
	public void testBattleDraw() {
		CardBattle b = new CardBattle();
		b.setSkill(2, ATTACK_SLOW);
		b.setSkill(1, ATTACK_SLOW);
		b.endTurn();
		b.setSkill(1, ATTACK_SLOW);
		b.setSkill(2, ATTACK_SLOW);
		b.endTurn();
		assertThat(b.winner(), is(0));
	}

	@Test
	public void testBattleSkillSpeed1() {
		CardBattle b = new CardBattle();
		b.setSkill(1, ATTACK_QUICK);
		b.setSkill(2, ATTACK);
		b.endTurn();
		assertThat(b.hp(1), is(30));
		assertThat(b.hp(2), is(25));
		assertThat(b.winner(), nullValue());
	}

	@Test
	public void testBattleSkillSpeed2() {
		CardBattle b = new CardBattle();
		b.setSkill(1, ATTACK_SLOW);
		b.setSkill(2, ATTACK);
		b.endTurn();
		assertThat(b.hp(1), is(20));
		assertThat(b.hp(2), is(30));
		assertThat(b.winner(), nullValue());
	}

	@Test
	public void testBattleNotOver() {
		CardBattle b = new CardBattle();
		assertThat(b.winner(), nullValue());
	}
	
//	@Test
	public void testProjectile2Vs1() {
		CardBattle b = new CardBattle();
		b.setSkill(1, ATTACK);
//		b.setSkill(2, SKILL_FIREBALL);
		b.endTurn();
		assertThat(b.hp(1), is(18));
		assertThat(b.hp(2), is(20));
		assertThat(b.winner(), nullValue());
	}

//	@Test
	public void testProjectile1Vs2() {
		CardBattle b = new CardBattle();
		b.setSkill(2, ATTACK);
//		b.setSkill(1, SKILL_FIREBALL);
		b.endTurn();
		assertThat(b.hp(2), is(18));
		assertThat(b.hp(1), is(20));
		assertThat(b.winner(), nullValue());
	}

//	@Test
	public void testProjectile3() {
		CardBattle b = new CardBattle();
		b.setSkill(2, ATTACK_QUICK);
//		b.setSkill(1, SKILL_FIREBALL);
		b.endTurn();
		assertThat(b.hp(2), is(30));
		assertThat(b.hp(1), is(25));
		assertThat(b.winner(), nullValue());
	}

//	@Test
	public void testProjectiles() {
		CardBattle b = new CardBattle();
//		b.setSkill(2, SKILL_FIREBALL);
//		b.setSkill(1, SKILL_FIREBALL);
		b.endTurn();
		assertThat(b.hp(2), is(30));
		assertThat(b.hp(1), is(30));
		assertThat(b.winner(), nullValue());
	}

	@Test
	public void testSlashSlash() {
		CardBattle b = new CardBattle();
		b.setSkill(1, SLASH);
		b.setSkill(2, ATTACK);
		b.endTurn();
		assertThat(b.hp(1), is(20));
		assertThat(b.hp(2), is(27));
		b.setSkill(1, SLASH);
		b.setSkill(2, ATTACK_QUICK);
		b.endTurn();
		assertThat(b.hp(1), is(15));
		assertThat(b.hp(2), is(27));
		assertThat(b.winner(), nullValue());
	}

	@Test
	public void testSlashHeavySlash() {
		CardBattle b = new CardBattle();
		b.setSkill(1, SLASH);
		b.endTurn();
		assertThat(b.hp(1), is(30));
		assertThat(b.hp(2), is(30 - SLASH.damage));
		b.setSkill(1, HEAVY_SLASH); // Mais rápido
		b.setSkill(2, HEAVY_SLASH); 
		b.endTurn();
		assertThat(b.hp(1), is(30));
		assertThat(b.hp(2), is(30 - SLASH.damage - HEAVY_SLASH.damage));
		assertThat(b.winner(), nullValue());
	}

	@Test
	public void testHeavySlashKnockdownDraw() {
		CardBattle b = new CardBattle();
		b.setSkill(1, HEAVY_SLASH);
		b.endTurn();
		b.setSkill(1, HEAVY_SLASH);
		b.setSkill(2, ATTACK);
		b.endTurn();
		assertThat(b.hp(1), is(30 - ATTACK.damage));
		assertThat(b.hp(2), is(30 - 2 * HEAVY_SLASH.damage));
		assertThat(b.winner(), nullValue());
	}

	@Test
	public void testHeavySlashKnockdownWin() {
		CardBattle b = new CardBattle();
		b.setSkill(1, HEAVY_SLASH);
		b.endTurn();
		b.setSkill(1, HEAVY_SLASH);
		b.setSkill(2, ATTACK_SLOW);
		b.endTurn();
		assertThat(b.hp(1), is(30));
		assertThat(b.hp(2), is(30 - 2 * HEAVY_SLASH.damage));
		assertThat(b.winner(), nullValue());
	}

	@Test
	public void testHeavySlashVsHeavySlash() {
		CardBattle b = new CardBattle();
		b.setSkill(1, HEAVY_SLASH);
		b.setSkill(2, HEAVY_SLASH);
		b.endTurn();
		assertThat(b.hp(1), is(30 - HEAVY_SLASH.damage));
		assertThat(b.hp(2), is(30 - HEAVY_SLASH.damage));
		b.setSkill(1, HEAVY_SLASH);
		b.setSkill(2, HEAVY_SLASH);
		b.endTurn();
		assertThat(b.hp(1), is(30 - 2 * HEAVY_SLASH.damage));
		assertThat(b.hp(2), is(30 - 2 * HEAVY_SLASH.damage));
		assertThat(b.winner(), nullValue());
	}

	@Test
	public void testHeavySlashUnsuccessfull() {
		CardBattle b = new CardBattle();
		b.setSkill(1, HEAVY_SLASH);
		b.setSkill(2, ATTACK_QUICK);
		b.endTurn();
		assertThat(b.hp(1), is(30 - ATTACK_QUICK.damage));
		assertThat(b.hp(2), is(30));
		b.setSkill(1, HEAVY_SLASH);
		b.setSkill(2, ATTACK);
		b.endTurn();
		assertThat(b.hp(1), is(30 - ATTACK_QUICK.damage - ATTACK.damage));
		assertThat(b.hp(2), is(30));
		assertThat(b.winner(), nullValue());
	}

	@Test
	public void testSlashUnsuccessfull() {
		CardBattle b = new CardBattle();
		b.setSkill(1, SLASH);
		b.setSkill(2, ATTACK_QUICK);
		b.endTurn();
		assertThat(b.hp(1), is(30 - ATTACK_QUICK.damage));
		assertThat(b.hp(2), is(30));
		b.setSkill(1, TRIPLE_SLASH);
		b.setSkill(2, ATTACK_QUICK);
		b.endTurn();
		assertThat(b.hp(1), is(30 - 2*ATTACK_QUICK.damage));
		assertThat(b.hp(2), is(30));
	}

	@Test
	public void testSlashTripleSlash() {
		CardBattle b = new CardBattle();
		b.setSkill(1, SLASH);
		b.setSkill(2, ATTACK_SLOW);
		b.endTurn();
		assertThat(b.hp(1), is(30));
		assertThat(b.hp(2), is(30 - SLASH.damage));
		b.setSkill(1, TRIPLE_SLASH);
		b.setSkill(2, ATTACK_QUICK);
		b.endTurn();
		assertThat(b.hp(1), is(30 - ATTACK_QUICK.damage));
		assertThat(b.hp(2), is(30 - SLASH.damage - TRIPLE_SLASH.damage));
	}

	@Test
	public void testMeteorSlash() {
		CardBattle b = new CardBattle();
		b.setSkill(1, METEOR);
		b.setSkill(2, SLASH);
		b.endTurn();
		assertThat(b.hp(1), is(30 - SLASH.damage));
		assertThat(b.hp(2), is(30 - METEOR.damage));
	}

	@Test
	public void testMeteorHeavySlash() {
		CardBattle b = new CardBattle();
		b.setSkill(1, METEOR);
		b.setSkill(2, HEAVY_SLASH);
		b.endTurn();
		assertThat(b.hp(1), is(30 - HEAVY_SLASH.damage));
		assertThat(b.hp(2), is(30 - METEOR.damage));
	}

	@Test
	public void testMeteorQuickAttack() {
		CardBattle b = new CardBattle();
		b.setSkill(1, METEOR);
		b.setSkill(2, ATTACK_QUICK);
		b.endTurn();
		assertThat(b.hp(1), is(30 - ATTACK_QUICK.damage));
		assertThat(b.hp(2), is(30));
	}

	@Test
	public void testMeteorAttackDSpeed() {
		CardBattle b = new CardBattle();
		b.setSkill(1, METEOR);
		b.setSkill(2, ATTACK_D_SPEED);
		b.endTurn();
		assertThat(b.hp(1), is(30));
		assertThat(b.hp(2), is(30 - METEOR.damage));
	}

	@Test
	public void testMeteorAttackESpeed() {
		CardBattle b = new CardBattle();
		b.setSkill(1, METEOR);
		b.setSkill(2, ATTACK_E_SPEED);
		b.endTurn();
		assertThat(b.hp(1), is(30));
		assertThat(b.hp(2), is(30 - METEOR.damage));
	}
}
