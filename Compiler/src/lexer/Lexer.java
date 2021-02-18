package lexer;

import java.io.*;
import java.util.*;

/**
 * Token formation pattern:
 * [DONE][Bruno] constant → integer_const | literal
 * [DONE][Yan] integer_const → nonzero{digit}| “0”
 * [DONE][Yan] real_const → interger_const "." digit+
 * [DONE][Bruno] literal → "“" caractere*"”" 
 * [DONE][Negreiros] identifier → letter{letter | digit| "_"}
 * [DONE][Character.isLetter(ch)] letter → [A-Za-z]
 * [DONE][Yan] digit → [0-9] = Character.isDigit(ch)
 * [DONE][Yan] nonzero→ [1-9] = isNonZeroDigit(char ch)
 * [Bruno] caractere → um  dos  256  caracteres  do  conjunto  ASCII,  
 * exceto as aspas e quebra de linha
 */

public class Lexer
{
    public static int line = 1;
    private char ch = ' ';
    private FileReader file;

    private Hashtable<String, Word> words = new Hashtable<String, Word>();

    private void reserve(Word w) 
    {
        words.put(w.getLexeme(), w);
    }

    public Lexer(String fileName) throws FileNotFoundException 
    {
        try 
        {
            file = new FileReader(fileName);
        }
        
        catch (FileNotFoundException e) 
        {
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

    private void readch() throws IOException 
    {
        ch = (char) file.read();
		ch = Character.toLowerCase(ch);
    }

    private boolean readch(char c) throws IOException 
    {
        readch();

        if (ch != c)
            return false;

        ch = ' ';
        return true;
    }

    public Token scan() throws IOException 
    {

        // Ignore delimiters
		for (;; readch()) 
        {
            if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b')
                {
                    continue;
                }

            // Ignore comments
			else if (ch == '%')
				while (ch != '%') 
					readch();

            else if (ch == '\n')
                line++; // count lines

            else
                break;
        }


        //integer_const or real_const
        Token constToken = verifyIsConst();
        
        if(constToken != null)
        {
            return constToken;
        } 		

        // Identifiers
        Token idToken = verifyIdentifierAndReserved();

        if(idToken != null)
        {
            return idToken;
        }           

        Token constantToken = verifyconstant();
        
        if(constantToken != null)
        {
            return constantToken;
        }

        if(verifyCharacter()){
            Token t = new Token(ch);
            readch();
            return t;
        }

        // Error handling
        System.out.println("Malformed token: \"" + ch + "\" at line " + line);
        return null;
    }

    private Token verifyIdentifierAndReserved() throws IOException
    {
        if (Character.isLetter(ch)) 
        {
            StringBuffer sb = new StringBuffer();

            do 
            {
                sb.append(ch);
                readch();
            } 
            while (Character.isLetterOrDigit(ch) || ch == '_');

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

    private boolean verifyCharacter() throws IOException 
    {
        int asciiValue = (int) ch;
        return ((asciiValue >= 0 && asciiValue <= 255) && (ch != '"' && ch != '\n'));
    }

    private String getLiteral() throws IOException 
    {
        StringBuffer sb = new StringBuffer();
        
        if(ch != '"') 
        {
            //TODO: Throw exception
            return null;
        }

        readch();

        while(verifyCharacter()) 
        {
            sb.append(ch);
            readch();
        }
   
        if(ch != '"') 
        {
            return null;
        }
        readch();

        return sb.toString();
    }

    private Token verifyLiteral() throws IOException 
    {
        String s = getLiteral();
        
        if(s != null) 
        {
            Word w = new Word(s, Tag.STRING);
            words.put(s, w);
            return w;
        }
        return null;
    }

    private Token verifyconstant() throws IOException 
    {
        // String integer = getIntegerConst();
        // if(integer != null) {
        //     Word w = new Word(integer, Tag.CONSTANT);
        //     words.put(integer, w);
        //     return w;
        // }
        if(ch == '"')
        {
            return verifyLiteral();
        }

        return null;
    }

    private boolean isNonZeroDigit(char ch)
    {
        int asciiValue = (int) ch;
        return (asciiValue >= 49 && asciiValue <= 57);
    }

    private StringBuffer verifyIntegerConst() throws IOException
    {
        StringBuffer sb = new StringBuffer();

        if(isNonZeroDigit(ch))
        {
            do
            {
                sb.append(ch);
                readch();
            }
            while (Character.isDigit(ch));

            return sb;
        }
        else if(ch == '0')
        {
            sb.append(ch);
            readch();

            return sb;
        }

        return null;
    } 
    
    //integer_const or real_const
    private Token verifyIsConst() throws IOException
    {
        StringBuffer sb = new StringBuffer();

        sb = verifyIntegerConst();

        if(sb == null)
        {
            return null;
        }
        
        else if(ch != '.')
        {
            String s = sb.toString();           

            Word w = new Word(s, Tag.INTEGER);
            words.put(s, w);
            return w;
        }

        else
        {
            sb.append(ch);
            readch();
            sb.append(verifyIntegerConst());
            readch();

            String s = sb.toString();           

            Word w = new Word(s, Tag.REAL);
            words.put(s, w);
            return w;

            //return real
        }
    }
}
