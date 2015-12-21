package neocardbattle;

public enum Speed {
	S(1), A(2), B(3), C(4), D(5);

	private int s;

	private Speed (int s) {
		this.s = s;
	}

	public int getS() {
		return s;
	}
}
