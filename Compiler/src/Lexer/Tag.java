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

        //Conditionals
        IF = 266,
        ELSE = 267,
        BEGIN = 268,
        END = 269,
        DO = 270,
        WHILE = 271,

        //Logical Operators
        NOT = 272,
        OR = 273,
        AND = 274,
        ASSIGN = 275, // :=
        GREATER = 276, // >
        GREATER_EQUAL = 277, // >=
        LESS = 278, // <
        LESS_EQUAL = 279, //<=
        NOT_EQUAL = 280, // <>
        EQUALS = 281, // =

        //Arithmetic Operators
        SUM = 282, // +
        MINUS = 283, // -
        TIMES = 284, // *
        DIVIDE = 285, // /
        COMA = 286, // ,

        //Grammar
        QUOTE = 287, // "
        OPEN_PARENTHESIS = 288, // (
        CLOSE_PARENTHESIS = 289, // )
        COMMENT = 290, // %
        END_COMMAND = 291, // ;

        ID = 292;

}

