package Compiler;

import java.io.FileNotFoundException;

import Lexer.Lexer;
import Lexer.Token;

public class Compiler 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {        
        try 
        {
            Lexer lexer;
            Token token;
            String fileName = args[0];

            lexer = new Lexer(fileName);
            token = lexer.scan();

            System.out.println("\n\nArquivo " + fileName);
            boolean isEOF = lexer.getIsEOF();

            while(true)
            {
                
                if(token != null)
                {
                    System.out.println(token.toString());
                }
                else
                {
                    System.exit(1);
                }

                token = lexer.scan();
                
                isEOF =  lexer.getIsEOF();
                if(isEOF){
                    break;
                }
            }

            System.out.println(lexer.getSymbolTable());
            lexer.clearLines();

        }
        catch(NullPointerException e)
        {
            
        }
        catch(FileNotFoundException e)  
        {
            System.out.println("Tests file not found.");
        }
        catch (Exception e) 
        {
            System.out.println("Error at" + e);
        }
    }    
}
