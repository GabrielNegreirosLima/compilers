package lexer;
public class Tag {
    public final static int
        
        //Reserved words
        INIT = 256,
        STOP = 257,
        READ = 268,
        WRITE = 269,

        //Variables Types
        IS = 258,
        INTERGER = 259,
        STRING = 260,
        REAL = 261,

        //Conditionals
        IF = 262,
        ELSE = 263,
        BEGIN = 264,
        END = 265,
        DO = 266,
        WHILE = 267,

        //Logical Operators
        NOT = 270,
        OR = 271,
        AND = 272,
        ASSIGN = 273, // :=
        GREATER = 274, // >
        GREATER_EQUAL = 275, // >=
        LESS = 276, // <
        LESS_EQUAL = 277, //<=
        DIFFERENT = 278, // <>
        EQUALS = 288,

        //Arithmetic Operators
        SUM = 279, // +
        MINUS = 280, // -
        TIMES = 281, // *
        DIVIDE = 282, // /

        //Grammar
        QUOTE = 283, // "
        OPEN_PARENTHESIS = 284, // (
        CLOSE_PARENTHESIS = 285, // )
        COMMENT = 286, // %
        END_COMMAND = 287; // ;

        // TYPE = 259,
        // BOOL = 262,
        // EQ = 288,
		// TRUE = 294,
        // FALSE = 295, 

}

