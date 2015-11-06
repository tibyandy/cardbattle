package cardbattle;

public enum Property {

	DELAYED_EFFECT_2(Type.DELAYED_EFFECT, 2);

	enum Type {
		DELAYED_EFFECT;
	}

	public final Type type;
	public final Integer arg;

	private Property(Type type, Integer arg) {
		this.type = type;
		this.arg = arg;
	}
}
