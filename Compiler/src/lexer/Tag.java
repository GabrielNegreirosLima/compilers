package lexer;
public class Tag {
    public final static int
        
		//Reserved words
        INIT = 256,
        STOP = 257,
        READ = 258,
        WRITE = 259,

        //Variables Types
        IS = 260,
        INTEGER = 261,
        STRING = 262,
        REAL = 263,

        //Conditionals
        IF = 264,
        ELSE = 265,
        BEGIN = 266,
        END = 267,
        DO = 268,
        WHILE = 269,

        //Logical Operators
        NOT = 270,
        OR = 271,
        AND = 272,
        ASSIGN = 273, // :=
        GREATER = 274, // >
        GREATER_EQUAL = 275, // >=
        LESS = 276, // <
        LESS_EQUAL = 277, //<=
        NOT_EQUAL = 278, // <>
        EQUALS = 279, // =

        //Arithmetic Operators
        SUM = 280, // +
        MINUS = 281, // -
        TIMES = 282, // *
        DIVIDE = 283, // /

        //Grammar
        QUOTE = 284, // "
        OPEN_PARENTHESIS = 285, // (
        CLOSE_PARENTHESIS = 286, // )
        COMMENT = 287, // %
        END_COMMAND = 288; // ;

        // TYPE = 259,
        // BOOL = 262,
        // EQ = 288,
		// TRUE = 294,
        // FALSE = 295, 

}

