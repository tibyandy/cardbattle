package neocardbattle;

import static neocardbattle.Effect.ATTACK_HALVED;
import static neocardbattle.Effect.KNOCKDOWN;
import static neocardbattle.Effect.STUN;
import static neocardbattle.SkillName.FIREWALL;
import static neocardbattle.SkillName.HEAVY_SLASH;
import static neocardbattle.SkillName.METEOR;
import static neocardbattle.SkillName.MIND_BLAST;
import static neocardbattle.SkillName.TRIPLE_SLASH;
import static neocardbattle.Speed.A;
import static neocardbattle.Speed.B;
import static neocardbattle.Speed.C;
import static neocardbattle.Speed.D;

import java.util.HashMap;
import java.util.Map;

public class Skill {

	private static final Map<SkillName, Skill> SKILLS = new HashMap<>();

	static {
		Skill.name(SkillName.SLASH).speed(B).damage(3).inflictOnHit(STUN).activateOnHit(Effect.SLASH);
		Skill.name(HEAVY_SLASH).speed(C).damage(5).boostedBy(Effect.SLASH).inflictOnHit(KNOCKDOWN);
		Skill.name(TRIPLE_SLASH).speed(B).damage(7).boostedBy(Effect.SLASH).inflictOnHit(STUN);
		
		Skill.name(MIND_BLAST).speed(B).damage(4).inflictOnHit(STUN);
		Skill.name(FIREWALL).speed(A).effectSpeedIs(B).damage(5).inflictOnHit(ATTACK_HALVED);
		Skill.name(METEOR).speed(B).effectSpeedIs(D).delayedDamageIs(8).inflictOnHit(KNOCKDOWN);
	}

	private static Skill name(SkillName skillName) {
		Skill skill = new Skill(skillName);
		SKILLS.put(skillName, skill);
		return skill;
	}

	private Skill delayedDamageIs(int d) {
		this.delayedDamage = d;
		return this;
	}

	private Skill boostedBy(Effect effect) {
		this.isBoostedBy = effect;
		return this;
	}

	private Skill activateOnHit(Effect effect) {
		this.effectOnHit = effect;
		return this;
	}

	private Skill inflictOnHit(Effect effect) {
		this.effectOnOpponentOnHit = effect;
		return this;
	}

	private Skill damage(int d) {
		this.damage = d;
		return this;
	}

	private Skill speed(Speed s) {
		this.activationSpeed = s;
		this.effectSpeed = s;
		return this;
	}

	private Skill effectSpeedIs(Speed s) {
		this.effectSpeed = s;
		return this;
	}

	private SkillName skillName;
	private int damage;
	private int delayedDamage;
	private Effect effectOnHit;
	private Effect effectOnOpponentOnHit;
	private Speed activationSpeed;
	private Speed effectSpeed;	
	private Effect isBoostedBy;

	private Skill(SkillName skill) {
		this.skillName = skill;
	}

	public static Skill get(SkillName skillName) {
		return SKILLS.get(skillName);
	}

	public SkillName getName() {
		return skillName;
	}

	@Override
	public String toString() {
		return skillName + ":" + activationSpeed + ":" + damage;
	}
}
