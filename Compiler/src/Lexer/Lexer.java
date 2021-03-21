package Lexer;

import java.io.*;
import java.util.*;


public class Lexer
{
    public static int line = 1;
    private char ch = ' ';
    private boolean isEOF = false;
    private FileReader file;

    private Hashtable<String, Word> words = new Hashtable<String, Word>();

    private void reserve(Word w) 
    {
        words.put(w.getLexeme(), w);
    }

    public Lexer(String fileName) throws FileNotFoundException 
    {
        this.isEOF = false;
        try 
        {
            file = new FileReader(fileName);
        }
        
        catch (FileNotFoundException e) 
        {            
            throw e;
        }

        reserve(new Word("if", Tag.IF));
        reserve(new Word("init", Tag.INIT));
        reserve(new Word("stop", Tag.STOP));
        reserve(new Word("read", Tag.READ));
        reserve(new Word("write", Tag.WRITE));
        reserve(new Word("type", Tag.TYPE));
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

    public String getSymbolTable(){
        return words.toString();
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
        int charLine = verifyDelimiters();
        if(charLine != 0){
            System.out.println("Comment opened at line " + charLine + 
                                " and not closed");
            return null;
        } 

        
        Token reservedOperator = verifyReservedOperators();

        if(reservedOperator != null)
        {
            return reservedOperator;
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

        Token constantToken = verifyConstant();
        
        if(constantToken != null)
        {
            return constantToken;
        }

        if(verifyCharacter()){
            Token t = new Token(ch);
            char current = ch;

            readch();
            if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b')
                return t;

            else if (ch == '\n'){
                line++; // count lines
                return t;
            }

            // Error handling
            System.out.println("Malformed character token: \"" + current + "\" at line " + line);
            return null;
        }

        // Error handling
        if(ch == '\n'){
            System.out.println("Malformed token: \"\\n\" at line " + line);
            return null;
        }

        if((int)ch == 65535){
            isEOF = true;
            return null;
        }   

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
            Word w = new Word(s, Tag.CONSTANT);
            words.put(s, w);
            return w;
        }
        return null;
    }

    private Token verifyConstant() throws IOException 
    {
        if(ch == '"')
        {
            return verifyLiteral();
        }
        else if(isNonZeroDigit(ch) || ch == '0')
        {
            StringBuffer sb = verifyIntegerConst();
            if(sb != null)
            {
                String s = sb.toString();
                Word w = new Word(s, Tag.INTEGER);
                words.put(s, w);
                return w;
            }
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
        // verify string const
        // else verifyIntegerConst
        sb = verifyIntegerConst();

        if(sb == null)
        {
            return null;
        }
        
        // integer_const        
        else if(ch != '.')
        {
            String s = sb.toString();           

            Word w = new Word(s, Tag.INTEGER);
            words.put(s, w);
            return w;
        }

        // real_const
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

    private int verifyDelimiters() throws IOException{

        for(;; readch()){
            if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b')
            {
                continue;
            }

            // Ignore comments
            else if (ch == '%'){
                int currentLine = line;
                do{
                    readch();
                    currentLine = line;
                    if((int)ch == 65535)
                        return currentLine;
                } while (ch != '%');
                
            }

            else if (ch == '\n')
                line++; // count lines

            else
                break;
        }

        return 0;
    }

    private Token verifyReservedOperators() throws IOException
    {
        switch (ch)
        {
            case ',':
                readch();
                return Word.comma;
            case '=':
                readch();
                return Word.equals;
            case '<':
                readch();
                if (ch == '=')
                {
                    readch();
                    return Word.lessEqual;
                }
                else if(ch == '>')
                {
                    readch();
                    return Word.notEqual;
                }
                return Word.less;
            case '>':
                if (readch('='))
                {
                    readch();
                    return Word.greaterEqual;
                }
                return Word.greater;
            case ':':
                if (readch('='))
                {
                    readch();
                    return Word.assign;
                }
                return new Token(':');
            case '+': 
                readch();
                return Word.sum;
            case '-':
                readch();
                return Word.minus;
            case '*':
                readch();
                return Word.times;
            case '/':
                readch();
                return Word.divide;
            case '(':
                readch();
                return Word.openParenthesis;
            case ')':
                readch();
                return Word.closeParenthesis;
            case ';':
                readch();
                return Word.endCommand;
            default: return null;
        }
    }

    public void clearLines(){
        Lexer.line = 1;
    }

    public boolean getIsEOF(){
        return isEOF;
    }
}
