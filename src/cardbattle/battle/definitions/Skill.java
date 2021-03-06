package cardbattle.battle.definitions;

import static cardbattle.battle.definitions.Property.DELAYED_EFFECT_2;
import static cardbattle.battle.definitions.Property.SLASH_BOOSTED;
import static cardbattle.common.CardBattleError.INVALID_SKILL_NAME;
import static cardbattle.common.CardBattleException.error;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cardbattle.common.CardBattleException;

public enum Skill {

	// Se n�o leva dano, aumenta V+1 para outros golpes
	SLASH("Slash", 3, Speed.B, Property.BOOST_SLASH),
	TRIPLE_SLASH("Triple Slash", 7, Speed.B, SLASH_BOOSTED),
	// Se acertar, diminui V-1 para o oponente no pr�ximo turno
	HEAVY_SLASH("Heavy Slash", 5, Speed.C, SLASH_BOOSTED, Property.KNOCKDOWN),

	ATTACK(10, Speed.B),

	ATTACK_QUICK(5, Speed.A),
	ATTACK_SLOW(15, Speed.C),
	ATTACK_D_SPEED(15, Speed.D),
	ATTACK_E_SPEED(15, Speed.E),
//	SKILL_FIREBALL(12, Speed.AVERAGE, PROJECTILE),

	METEOR("Meteor", 0, Speed.B, DELAYED_EFFECT_2),

	NONE(0, Speed.E),
	MIND_BLAST("Mind Blast", 3, Speed.B),
	FIREWALL("Firewall", 6, Speed.B);

	public final Integer damage;
	public final Integer speed;
	public final Integer effectDelay;
	public final String name;

	private final Set<Property> properties;

	private static final Map<String, Skill> SKILLS_BY_NAME = new HashMap<>(); 

	@Deprecated
	private Skill(Integer damage, Speed speed, Property... properties) {
		this(null, damage, speed, properties);
	}

	private Skill(String name, Integer damage, Speed speed, Property... properties) {
		this.name = name;
		this.damage = damage;
		this.speed = speed.n;

		Integer effectDelay = null;
		this.properties = new HashSet<Property>();
		for (Property property : properties) {
			this.properties.add(property);

			switch (property) {
			case DELAYED_EFFECT_2:
				effectDelay = 2;
				break;
			}
		}
		this.effectDelay = effectDelay;
	}

	public boolean has(Property property) {
		return properties.contains(property);
	}

	public static Skill get(String s) throws CardBattleException {
		if (SKILLS_BY_NAME.isEmpty()) {
			for (Skill skill : Skill.values()) {
				SKILLS_BY_NAME.put(skill.toString(), skill);
			}
		}
		String skillName = s.toUpperCase();
		Skill skill = SKILLS_BY_NAME.get(skillName);
		if (skill == null) {
			throw error(INVALID_SKILL_NAME, skillName);
		}
		return skill;
	}
}
