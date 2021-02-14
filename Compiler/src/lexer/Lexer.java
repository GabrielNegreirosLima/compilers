package lexer;

import java.io.*;
import java.util.*;

/**
 * Token formation pattern:
 * [DONE][Bruno] constant → integer_const | literal
 * [Yan] integer_const → nonzero{digit}| “0”
 * [Yan] real_const → interger_const "." digit+
 * [DONE][Bruno] literal → "“" caractere*"”" 
 * [DONE][Negreiros] identifier → letter{letter | digit| "_"}
 * [Character.isLetter(ch)] letter → [A-Za-z]
 * [Yan] digit → [0-9]
 * [Yan] nonzero→ [1-9]
 * [DONE][Bruno] caractere → um  dos  256  caracteres  do  conjunto  ASCII,  
 * exceto as aspas e quebra de linha
 */

public class Lexer {
    public static int line = 1;
    private char ch = ' ';
    private FileReader file;

    private Hashtable<String, Word> words = new Hashtable<String, Word>();

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

        reserve(new Word("if", Tag.IF));
        reserve(new Word("init", Tag.INIT));
        reserve(new Word("stop", Tag.STOP));
        reserve(new Word("read", Tag.READ));
        reserve(new Word("write", Tag.WRITE));
        reserve(new Word("is", Tag.IS));
        reserve(new Word("integer", Tag.INTEGER));
        reserve(new Word("string", Tag.STRING));
        reserve(new Word("real", Tag.REAL));
        reserve(new Word("else", Tag.ELSE));
        reserve(new Word("begin", Tag.BEGIN));
        reserve(new Word("end", Tag.END));
        reserve(new Word("do", Tag.DO));
        reserve(new Word("while", Tag.WHILE));
        reserve(new Word("not", Tag.NOT));
        reserve(new Word("or", Tag.OR));
        reserve(new Word("and", Tag.AND));
    }

    private void readch() throws IOException {
        ch = (char) file.read();
		ch = Character.toLowerCase(ch);
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
            // Ignore comments
			else if (ch == '%')
				while (ch != '%') 
					readch();
            else if (ch == '\n')
                line++; // count lines
            else
                break;
        }

		// TODO: check for operators.
		// Change this to implement the correct tokens for the 
		// language. See the correct pattern at the file's head. 
        // switch (ch) {
        //     case '&':
        //         if (readch('&'))
        //             return Word.AND;
        //         else
        //             return new Token('&');
        //     case '|':
        //         if (readch('|'))
        //             return Word.or;
        //         else
        //             return new Token('|');
        //     case '=':
        //         if (readch('='))
        //             return Word.eq;
        //         else
        //             return new Token('=');
        //     case '<':
        //         if (readch('='))
        //             return Word.le;
        //         else
        //             return new Token('<');
        //     case '>':
        //         if (readch('='))
        //             return Word.ge;
        //         else
        //             return new Token('>');
        // }

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
		

        // Identifiers
        Token idToken = verifyIdentifierAndReserved();
        if(idToken != null)
            return idToken;

        // Error handling
        System.out.println("Malformed token: \"" + ch + "\" at line" + line);
        return null;
    }

    private Token verifyIdentifierAndReserved() throws IOException{
        if (Character.isLetter(ch)) {
            StringBuffer sb = new StringBuffer();

            do {
                sb.append(ch);
                readch();
            } while (Character.isLetterOrDigit(ch) || ch == '_');

            String s = sb.toString();
            Word w = (Word) words.get(s);

            if (w != null)
                return w;

            w = new Word(s, Tag.ID);
            words.put(s, w);
            return w;
        }

        return null;
    }

    private boolean verifyCharacter() throws IOException {
        int asciiValue = (int) ch;
        return ((asciiValue >= 0 && asciiValue <= 255) && (ch != '"' && ch != '\n'));
    }

    private String getLiteral() throws IOException {
        StringBuffer sb = new StringBuffer();
        
        if(ch != '"') {
            //TODO: Throw exception
            System.out.println("Expected character: " + ch + "to be: " + '"' );
            return null;
        }

        readch();

        while(verifyCharacter()) {
            sb.append(ch);
            readch();
        }

        if(ch != '"') {
            //TODO: Throw exception
            System.out.println("Expected character: " + ch + "to be: " + '"' );
            return null;
        }

        return sb.toString();
    }

    private Token verifyLiteral() throws IOException {
        String s = getLiteral();
        Word w = new Word(s, Tag.STRING);
        words.put(s, w);
        return w;
    }

    private Token verifyconstant() throws IOException {
        // String integer = getIntegerConst();
        // if(integer != null) {
        //     Word w = new Word(integer, Tag.CONSTANT);
        //     words.put(integer, w);
        //     return w;
        // }

        String literal = getLiteral();
        if(literal != null) {
            Word w = new Word(literal, Tag.CONSTANT);
            words.put(literal, w);
            return w;
        }
        return null;
    }

}
