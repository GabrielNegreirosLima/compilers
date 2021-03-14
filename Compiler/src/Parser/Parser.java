package Parser;

import Lexer.Token;

import java.io.IOException;

import Lexer.Lexer;


/**
 *
 * @author negreiros
 */
public class Parser {
    private Token tok;
    private Lexer lexer;

    public Parser(Lexer l) throws IOException{
        this.lexer = l;
        advance();
    }

    public void parse() throws IOException{

        while(tok!=null){
            System.out.println(tok.toString());
            advance();
        }
    }

    private void advance() throws IOException{
        this.tok = getToken();
    }

    private Token getToken() throws IOException{
        Token token;
        boolean isEOF;
    
        token = lexer.scan();
        isEOF = lexer.getIsEOF();

        if(!isEOF){ 

            if(token != null)
            {
                return token;
            }
            else
            {
                System.out.println("Error at token processing");
                System.exit(1);
            }
        }

        return null;
    }

    private void eat(Token t) throws IOException{
        if(tok.tag == t.tag)
            advance();
        
        error(t);
    }

    private void error(Token t){
        System.out.println("Unexpected token " + t.toString());
    }
}
