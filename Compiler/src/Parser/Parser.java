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

    /*
     * Block of basic functions of the Parser 
     */
    public void parse() throws IOException{
        program(); eat(null);
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


    /*
     * Block of grammar assertion
     */

    private void program(){

    }

    private void program2(){
        
    }

    private void declList(){
        
    }

    private void declList2(){
        
    }

    private void decl(){
        
    }

    private void identList(){
        
    }

    private void identList2(){
        
    }

    private void type(){
        
    }

    private void stmtList(){
        
    }

    private void stmt(){
        
    }

    private void assignStmt(){
        
    }

    private void ifStmt(){
        
 
    }

    private void ifStmt2(){
        
    }

    private void condition(){
        
    }

    private void doStmt(){
        
    }

    private void doSuffix(){
        
    }

    private void readStmt(){
        
    }

    private void writeStmt(){
        
    }

    private void writable(){
        
    }

    private void expression(){
        
    }

    private void expression2(){
        
    }

    private void simpleExpr(){
        
    }

    private void simpleExpr2(){
        
    }

    private void term(){
        
    }

    private void term2(){
        
    }

    private void factorA(){
        
    }

    private void factor(){
        
    }

    private void relop(){
        
    }

    private void addop(){
        
    }

    private void mulop(){
        
    }
}
