package lexer;

import java.io.*;
import java.util.*;

/**
 * Token formation pattern:
 * constant → integer_const | literal
 * integer_const → nonzero{digit}| “0”
 * real_const → interger_const "." digit+
 * literal → "“" caractere*"”" 
 * identifier → letter{letter | digit| "_ "}
 * letter → [A-Za-z]
 * digit → [0-9]
 * nonzero→ [1-9]
 * caractere → um  dos  256  caracteres  do  conjunto  ASCII,  
 * exceto as aspas e quebra de linha
 */

public class Lexer {
    public static int line = 1;
    private char ch = ' ';
    private FileReader file;

    private Hashtable words = new Hashtable();

    private void reserve(Word w) {
        words.put(w.getLexeme(), w);
    }

    public Lexer(String fileName) throws FileNotFoundException {
        try {
            file = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo nao encontrado");
            throw e;
        }

		// TODO: reservar the correct Words for the language 
        // reserve(new Word("if", Tag.IF));
        // reserve(new Word("program", Tag.PRG));
        // reserve(new Word("begin", Tag.BEG));
        // reserve(new Word("end", Tag.END));
        // reserve(new Word("type", Tag.TYPE));
        // reserve(new Word("int", Tag.INT));
    }

    private void readch() throws IOException {
        ch = (char) file.read();
    }

    private boolean readch(char c) throws IOException {
        readch();

        if (ch != c)
            return false;

        ch = ' ';
        return true;
    }

    public Token scan() throws IOException {

        // Ignore delimiters
		for (;; readch()) {
            if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b')
                continue;
            else if (ch == '\n')
                line++; // count lines
            else
                break;
        }

		// TODO: check for operators.
		// Change this to implement the correct tokens for the 
		// language. See the correct pattern at the file's head. 
        switch (ch) {
            case '&':
                if (readch('&'))
                    return Word.and;
                else
                    return new Token('&');
            case '|':
                if (readch('|'))
                    return Word.or;
                else
                    return new Token('|');
            case '=':
                if (readch('='))
                    return Word.eq;
                else
                    return new Token('=');
            case '<':
                if (readch('='))
                    return Word.le;
                else
                    return new Token('<');
            case '>':
                if (readch('='))
                    return Word.ge;
                else
                    return new Token('>');
        }

		// TODO: check for numbers. Implement integer_const, 
		// real_const and digit here.
		// Change this to implementation the correct tokens for
		// the language. See the correct pattern 
		// at the file's head. 
        if (Character.isDigit(ch)) {
            int value = 0;

            do {
                value = 10 * value + Character.digit(ch, 10);
                readch();
            } while (Character.isDigit(ch));

            return new Num(value);
        }
		

		// TODO: check for identifiers. Implement integer_const, 
		// real_const and digit here.
		// Change this to implementation the correct tokens for
		// the language. See the correct pattern 
		// at the file's head. 
        if (Character.isLetter(ch)) {
            StringBuffer sb = new StringBuffer();

            do {
                sb.append(ch);
                readch();
            } while (Character.isLetterOrDigit(ch));

            String s = sb.toString();
            Word w = (Word) words.get(s);

            if (w != null)
                return w;

            w = new Word(s, Tag.ID);
            words.put(s, w);
            return w;
        }

        Token t = new Token(ch);
        ch = ' ';
        return t;
    }

}
