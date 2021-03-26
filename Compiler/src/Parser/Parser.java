package Parser;

import Lexer.Token;
import Lexer.Lexer;
import Lexer.Tag;

import java.io.IOException;



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
        program(); eat(-1);
    }

    private void advance() throws IOException{
        this.tok = getToken();
    }

    private Token getToken() throws IOException{
        Token token;
        boolean isEOF;
        
        token = lexer.scan();
        isEOF = lexer.getIsEOF();

        if(!isEOF) {

            if(token != null)
            {
                return token;
            }
            else
            {
                System.out.println("Error at token processing");
                System.exit(1);
            }
        } else if(this.tok.tag != Tag.STOP) {
            error(tok);
        }

        return null;
    }

    private void eat(int t) throws IOException{

        if(t == -1) {
            System.out.println("Parser terminou a sua execução.");
        }

        if(tok.tag == t)
            advance();
        else
            error(tok);
    }

    private void error(Token t) {
        System.out.println("Unexpected token " + t.toString() + " At line: " + Lexer.line);
        System.exit(1);
    }


    /*
     * Block of grammar assertion
     */

    private void program() throws IOException {
        switch(tok.tag) {
            case Tag.INIT:
                eat(Tag.INIT); program2(); 
            default:
                error(tok);
        }
    }

    private void program2() throws IOException {
        switch (tok.tag) {
            case Tag.BEGIN:
                eat(Tag.BEGIN); stmtList(); eat(Tag.STOP); break;
            case Tag.ID:
                declList(); eat(Tag.BEGIN); stmtList();  eat(Tag.STOP); break;
            default:
                error(tok);
        }
    }

    private void declList() throws IOException {
        switch (tok.tag) {
            case Tag.ID:
                decl(); eat(Tag.END_COMMAND);  declList2(); break;
            default:
                error(tok);
        }
    }

    private void declList2() throws IOException {
        switch (tok.tag) {
            case Tag.ID:
                declList(); break;
            case Tag.BEGIN: break;
            default:
                error(tok);
        }
    }

    private void decl() throws IOException {
        switch(tok.tag) {
            case Tag.ID:
                identList(); eat(Tag.IS); type(); break;  
            default:
                error(tok);
        }
    }

    private void identList() throws IOException {
        switch(tok.tag) {
            case Tag.ID:
                eat(Tag.ID); identList2(); break; 
            default:
                error(tok);
        }
    }

    private void identList2() throws IOException {
        switch(tok.tag) {
            case Tag.COMMA:
                eat(Tag.COMMA); eat(Tag.ID); identList2(); break;  
            case Tag.IS: break;  
            default:
                error(tok);
        }

    }

    private void type() throws IOException {
        switch(tok.tag) {
            case Tag.INTEGER:
                eat(Tag.INTEGER); break;  
            case Tag.STRING:
                eat(Tag.STRING); break;  
            case Tag.REAL:
                eat(Tag.REAL); break;  
            default:
                error(tok);
        }
    }

    private void stmtList() throws IOException {
        switch (tok.tag) {
            case Tag.ID:
                stmt(); eat(Tag.END_COMMAND); stmtList2(); break;
            case Tag.IF:
                stmt(); eat(Tag.END_COMMAND); stmtList2(); break;
            case Tag.DO:
                stmt(); eat(Tag.END_COMMAND); stmtList2(); break;
            case Tag.READ:
                stmt(); eat(Tag.END_COMMAND); stmtList2(); break;
            case Tag.WRITE:
                stmt(); eat(Tag.END_COMMAND); stmtList2(); break;
            default:
                error(tok);
        }
    }

    private void stmtList2() throws IOException {
        switch (tok.tag) {
            case Tag.STOP:
                break;
            case Tag.ID:
                stmtList(); break;
            case Tag.IF:
                stmtList(); break;
            case Tag.END:
                break;
            case Tag.DO:
                stmtList(); break;
            case Tag.WHILE:
                break;
            case Tag.READ:
                stmtList(); break;
            case Tag.WRITE:
                stmtList(); break;
            default:
                error(tok);
        }
    }

    private void stmt() throws IOException {
        switch(tok.tag) {
            case Tag.WRITE:
                writeStmt(); break;  
            case Tag.READ:
                readStmt(); break;  
            case Tag.DO:
                doStmt(); break;  
            case Tag.IF:
                ifStmt(); break;
            case Tag.ID:
                assignStmt(); break;
            default:
                error(tok);
        }

    }

    private void assignStmt() throws IOException {
        switch(tok.tag) {
            case Tag.ID:
                eat(Tag.ID); eat(Tag.ASSIGN); simpleExpr(); break;
            default:
                error(tok);
        }
    }

    private void ifStmt() throws IOException {
        switch(tok.tag) {
            case Tag.IF:
                eat(Tag.IF); 
                eat(Tag.OPEN_PARENTHESIS);
                condition();
                eat(Tag.CLOSE_PARENTHESIS);
                eat(Tag.BEGIN);
                stmtList(); 
                eat(Tag.END);
                ifStmt2(); break;  
            default:
                error(tok);
        }
    }

    private void ifStmt2() throws IOException {
        switch(tok.tag) {
            case Tag.ELSE:
                eat(Tag.ELSE);
                eat(Tag.BEGIN);
                stmtList();
                eat(Tag.END);
                break;  
            case Tag.END_COMMAND:
                break;
            default:
                error(tok);
        }
    }

    private void condition() throws IOException {
        switch(tok.tag) {
            case Tag.CONSTANT:
                expression(); break;  
            case Tag.MINUS:
                expression(); break;  
            case Tag.NOT:
                expression(); break;  
            case Tag.OPEN_PARENTHESIS:
                expression(); break;
            case Tag.ID:
                expression(); break;
            default:
                error(tok);
        }
    }

    private void doStmt() throws IOException {
        switch(tok.tag) {
            case Tag.DO:
                eat(Tag.DO); 
                stmtList();
                doSuffix();
                break;
            default:
                error(tok);
        }
    }

    private void doSuffix() throws IOException {
        switch(tok.tag) {
            case Tag.WHILE:
                eat(Tag.WHILE); 
                eat(Tag.OPEN_PARENTHESIS);
                condition();
                eat(Tag.CLOSE_PARENTHESIS);
                break;
            default:
                error(tok);
        }
    }

    private void readStmt() throws IOException {

        switch(tok.tag) {
            case Tag.READ:
                eat(Tag.READ); 
                eat(Tag.OPEN_PARENTHESIS);
                eat(Tag.ID); 
                eat(Tag.CLOSE_PARENTHESIS);
                break;
            default:
                error(tok);
        }
        
    }

    private void writeStmt() throws IOException {
        switch(tok.tag) {
            case Tag.WRITE:
                eat(Tag.WRITE); 
                eat(Tag.OPEN_PARENTHESIS);
                writable(); 
                eat(Tag.CLOSE_PARENTHESIS); 
                break;
            default:
                error(tok);
        }
    }

    private void writable() throws IOException {
        switch(tok.tag) {
            case Tag.CONSTANT:
                simpleExpr(); break;  
            case Tag.MINUS:
                simpleExpr(); break;  
            case Tag.NOT:
                simpleExpr(); break;  
            case Tag.OPEN_PARENTHESIS:
                simpleExpr(); break;
            case Tag.ID:
                simpleExpr(); break;
            default:
                error(tok);
        }
    }

    private void expression() throws IOException {
        switch(tok.tag) {
            case Tag.CONSTANT:
                simpleExpr(); expression2(); break;  
            case Tag.MINUS:
                simpleExpr(); expression2(); break;  
            case Tag.NOT:
                simpleExpr(); expression2(); break;  
            case Tag.OPEN_PARENTHESIS:
                simpleExpr(); expression2(); break;
            case Tag.ID:
                simpleExpr(); expression2(); break;
            default:
                error(tok);
        }
    }

    private void expression2() throws IOException {
        switch(tok.tag) {
            case Tag.NOT_EQUAL:
                relop(); simpleExpr(); break;  
            case Tag.LESS_EQUAL:
                relop(); simpleExpr(); break;
            case Tag.LESS:
                relop(); simpleExpr(); break;
            case Tag.GREATER_EQUAL:
                relop(); simpleExpr(); break;
            case Tag.GREATER:
                relop(); simpleExpr(); break;
            case Tag.EQUALS:
                relop(); simpleExpr(); break;
            case Tag.CLOSE_PARENTHESIS:
                break;
            default:
                error(tok);
        }
    }

    private void simpleExpr() throws IOException {
        switch(tok.tag) {
            case Tag.CONSTANT:
                term(); simpleExpr2(); break;
            case Tag.MINUS:
                term(); simpleExpr2(); break;
            case Tag.NOT:
                term(); simpleExpr2(); break;
            case Tag.OPEN_PARENTHESIS:
                term(); simpleExpr2(); break;
            case Tag.ID:
                term(); simpleExpr2(); break;
            default:
                error(tok);
        }
    }

    private void simpleExpr2() throws IOException {
        switch(tok.tag) {
            case Tag.OR:
                addop(); term(); simpleExpr2(); break;
            case Tag.SUM:
                addop(); term(); simpleExpr2(); break;
            case Tag.MINUS:
                addop(); term(); simpleExpr2(); break;
            case Tag.NOT_EQUAL:
                break;  
            case Tag.LESS_EQUAL:
                break; 
            case Tag.LESS:
                break;
            case Tag.GREATER_EQUAL:
                break;
            case Tag.GREATER:
                break;
            case Tag.EQUALS:
                break;
            case Tag.CLOSE_PARENTHESIS:
                break;
            case Tag.END_COMMAND:
                break;
            default:
                error(tok);
        }
    }

    private void term() throws IOException {
        switch(tok.tag) {
            case Tag.CONSTANT:
                factorA(); term2(); break;
            case Tag.MINUS:
                factorA(); term2(); break;
            case Tag.NOT:
                factorA(); term2(); break; 
            case Tag.OPEN_PARENTHESIS:
                factorA(); term2(); break;
            case Tag.ID:
                factorA(); term2(); break;
            default:
                error(tok);
        }
    }

    private void term2() throws IOException {
        switch(tok.tag) {
            case Tag.AND:
                mulop(); factorA(); term2(); break;
            case Tag.TIMES:
                mulop(); factorA(); term2(); break;
            case Tag.DIVIDE:
                mulop(); factorA(); term2(); break;
            case Tag.OR:
                break;
            case Tag.SUM:
                break;  
            case Tag.MINUS:
                break;         
            case Tag.NOT_EQUAL:
                break;  
            case Tag.LESS_EQUAL:
                break; 
            case Tag.LESS:
                break;
            case Tag.GREATER_EQUAL:
                break;
            case Tag.GREATER:
                break;
            case Tag.EQUALS:
                break;
            case Tag.CLOSE_PARENTHESIS:
                break;
            case Tag.END_COMMAND:
                break;
            default:
                error(tok);
        }
    }

    private void factorA() throws IOException {
        switch(tok.tag) {
            case Tag.CONSTANT:
                factor(); break;
            case Tag.MINUS:
                eat(Tag.MINUS); factor(); break;
            case Tag.NOT:
                eat(Tag.NOT); factor(); break;
            case Tag.OPEN_PARENTHESIS:
                factor(); break;
            case Tag.ID:
                factor(); break;
            default:
                error(tok);
        }
    }

    private void factor() throws IOException {
        switch(tok.tag) {
            case Tag.CONSTANT:
                eat(Tag.CONSTANT); break;
            case Tag.OPEN_PARENTHESIS:
                eat(Tag.OPEN_PARENTHESIS); expression(); eat(Tag.CLOSE_PARENTHESIS); break;
            case Tag.ID:
                eat(Tag.ID); break;
            default:
                error(tok);
        }
    }

    private void relop() throws IOException {
        switch(tok.tag) {
            case Tag.NOT_EQUAL:
                eat(Tag.NOT_EQUAL); break;
            case Tag.LESS_EQUAL:
                eat(Tag.LESS_EQUAL); break;
            case Tag.LESS:
                eat(Tag.LESS); break;
            case Tag.GREATER_EQUAL:
                eat(Tag.GREATER_EQUAL); break;
            case Tag.GREATER:
                eat(Tag.GREATER); break;
            case Tag.EQUALS:
                eat(Tag.EQUALS); break;
            default:
                error(tok);
        }        
    }

    private void addop() throws IOException {
        switch(tok.tag) {
            case Tag.OR:
                eat(Tag.OR); break;
            case Tag.SUM:
                eat(Tag.SUM); break;
            case Tag.MINUS:
                eat(Tag.MINUS); break;
            default:
                error(tok);
        }
    }

    private void mulop() throws IOException {
        switch(tok.tag) {
            case Tag.TIMES:
                eat(Tag.TIMES); break;
            case Tag.DIVIDE:
                eat(Tag.DIVIDE); break;
            case Tag.AND:
                eat(Tag.AND); break;
            default:
                error(tok);
        }
    }
}
