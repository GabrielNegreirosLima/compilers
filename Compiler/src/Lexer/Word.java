package Lexer;

public class Word extends Token {
    private String lexeme = "";

    //Logical Operators
    public static final Word equals = new Word("=", Tag.EQUALS);
    public static final Word notEqual = new Word("<>", Tag.NOT_EQUAL);
    public static final Word less = new Word("<", Tag.LESS);
    public static final Word lessEqual = new Word("<=", Tag.LESS_EQUAL);
    public static final Word greater = new Word(">", Tag.GREATER);
    public static final Word greaterEqual = new Word(">=", Tag.GREATER_EQUAL);
    
    //Arithmetic Operators
    public static final Word assign = new Word(":=", Tag.ASSIGN);
    public static final Word sum = new Word("+", Tag.SUM);
    public static final Word minus = new Word("-", Tag.MINUS);
    public static final Word times = new Word("*", Tag.TIMES);
    public static final Word divide = new Word("/", Tag.DIVIDE);

    //Grammar
    // public static final Word quote = new Word("\"", Tag.QUOTE);
    public static final Word openParenthesis = new Word("(", Tag.OPEN_PARENTHESIS);
    public static final Word closeParenthesis = new Word(")", Tag.CLOSE_PARENTHESIS);
    public static final Word endCommand = new Word(";", Tag.END_COMMAND);

    public Word(String s, int tag) {
        super(tag);
        lexeme = s;
    }

    public String toString() {
        return "<" + tag + ", " + lexeme + ">";
    }

    public String getLexeme() {
        return lexeme;
    }
}
