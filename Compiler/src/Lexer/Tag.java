package Lexer;

public class Tag {
    public final static int
        
        //Reserved words
        INIT = 256,
        STOP = 257,
        READ = 258,
        WRITE = 259,
        TYPE = 260,

        //Variables Types
        IS = 261,
        INTEGER = 262,
        STRING = 263,
        REAL = 264,
        CONSTANT = 265,
        ASSIGN = 266, // :=

        //Conditionals
        IF = 267,
        ELSE = 268,
        BEGIN = 269,
        END = 270,
        DO = 271,
        WHILE = 272,

        //Logical Operators
        GREATER = 273, // >
        GREATER_EQUAL = 274, // >=
        LESS = 275, // <
        LESS_EQUAL = 276, //<=
        NOT_EQUAL = 277, // <>
        EQUALS = 278, // =

        //Arithmetic Operators
        NOT = 279,
        OR = 280,
        AND = 281,
        SUM = 282, // +
        MINUS = 283, // -
        TIMES = 284, // *
        DIVIDE = 285, // /
        COMMA = 286, // ,

        //Grammar
        QUOTE = 287, // "
        OPEN_PARENTHESIS = 288, // (
        CLOSE_PARENTHESIS = 289, // )
        COMMENT = 290, // %
        END_COMMAND = 291, // ;

        ID = 292;

}

