package cardbattle;

import java.util.HashSet;
import java.util.Set;

import static cardbattle.Property.DELAYED_EFFECT_2;
import static cardbattle.Property.SLASH_BOOSTED;

public enum Skill {

	// Se não leva dano, aumenta V+1 para outros golpes
	SLASH(3, Speed.B, Property.BOOST_SLASH),
	TRIPLE_SLASH(7, Speed.B, SLASH_BOOSTED),
	// Se acertar, diminui V-1 para o oponente no próximo turno
	HEAVY_SLASH(5, Speed.C, SLASH_BOOSTED, Property.KNOCKDOWN),

	ATTACK(10, Speed.B),

	ATTACK_QUICK(5, Speed.A),
	ATTACK_SLOW(15, Speed.C),
	ATTACK_D_SPEED(15, Speed.D),
	ATTACK_E_SPEED(15, Speed.E),
//	SKILL_FIREBALL(12, Speed.AVERAGE, PROJECTILE),

	METEOR(0, Speed.B, DELAYED_EFFECT_2),

	NONE(0, Speed.E),
	MIND_BLAST(3, Speed.B),
	FIREWALL(6, Speed.B);

	public final Integer damage;
	public final Integer speed;
	public final Integer effectDelay;
	private final Set<Property> properties;

	private Skill(Integer damage, Speed speed, Property... properties) {
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
}
