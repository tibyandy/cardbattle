package cardbattle;

public enum Speed {
	E(0),
	D(1),
	C(2),
	B(3),
	A(4);

	public final int n;

	private Speed(int n) {
		this.n = n;
	}
}
