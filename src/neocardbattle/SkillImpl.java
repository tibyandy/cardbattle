package neocardbattle;

import static neocardbattle.Effect.ATTACK_HALVED;
import static neocardbattle.Effect.KNOCKDOWN;
import static neocardbattle.Effect.STUN;
import static neocardbattle.Skill.FIREWALL;
import static neocardbattle.Skill.HEAVY_SLASH;
import static neocardbattle.Skill.METEOR;
import static neocardbattle.Skill.MIND_BLAST;
import static neocardbattle.Skill.TRIPLE_SLASH;
import static neocardbattle.Speed.A;
import static neocardbattle.Speed.B;
import static neocardbattle.Speed.C;
import static neocardbattle.Speed.D;

import java.util.HashMap;
import java.util.Map;

public class SkillImpl {

	private static final Map<Skill, SkillImpl> SKILLS = new HashMap<>();

	static {
		define(Skill.SLASH).speedIs(B).damageIs(3).effectOnOpponentOnHit(STUN).effectOnHit(Effect.SLASH);
		define(HEAVY_SLASH).speedIs(C).damageIs(5).boostedBy(Effect.SLASH).effectOnOpponentOnHit(KNOCKDOWN);
		define(TRIPLE_SLASH).speedIs(B).damageIs(7).boostedBy(Effect.SLASH).effectOnOpponentOnHit(STUN);
		
		define(MIND_BLAST).speedIs(B).damageIs(4).effectOnOpponentOnHit(STUN);
		define(FIREWALL).speedIs(A).effectSpeedIs(B).damageIs(5).effectOnOpponentOnHit(ATTACK_HALVED);
		define(METEOR).speedIs(B).effectSpeedIs(D).delayedDamageIs(8).effectOnOpponentOnHit(KNOCKDOWN);
	}

	private static SkillImpl define(Skill skill) {
		SkillImpl impl = new SkillImpl(skill);
		SKILLS.put(skill, impl);
		return impl;
	}

	private SkillImpl delayedDamageIs(int d) {
		this.delayedDamage = d;
		return this;
	}

	private SkillImpl boostedBy(Effect effect) {
		this.isBoostedBy = effect;
		return this;
	}

	private SkillImpl effectOnHit(Effect effect) {
		this.effectOnHit = effect;
		return this;
	}

	private SkillImpl effectOnOpponentOnHit(Effect effect) {
		this.effectOnOpponentOnHit = effect;
		return this;
	}

	private SkillImpl damageIs(int d) {
		this.damage = d;
		return this;
	}

	private SkillImpl speedIs(Speed s) {
		this.activationSpeed = s;
		this.effectSpeed = s;
		return this;
	}

	private SkillImpl effectSpeedIs(Speed s) {
		this.effectSpeed = s;
		return this;
	}

	private Skill skill;
	private int damage;
	private int delayedDamage;
	private Effect effectOnHit;
	private Effect effectOnOpponentOnHit;
	private Speed activationSpeed;
	private Speed effectSpeed;	
	private Effect isBoostedBy;

	private SkillImpl(Skill skill) {
		this.skill = skill;
	}
}
