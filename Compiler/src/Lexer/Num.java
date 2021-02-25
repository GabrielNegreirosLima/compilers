package Lexer;

public class Num extends Token {
	public final int value;

	public Num(int value) {
		//TODO: Pensar no tipo da tag real ou integer
		super(Tag.REAL);
		this.value = value;
	}

	public String toString() {
		return "" + value;
	}
}
