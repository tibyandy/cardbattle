package neocardbattle;

public enum Energy {
	RED, BLUE, NULL, EMPTY;

	public static Energy getRandom() {
		int r = (int) (Math.random() * 3);
		return Energy.values()[r];
	}
}
