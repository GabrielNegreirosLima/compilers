package Compiler;

import java.io.FileNotFoundException;

import Lexer.Lexer;
import Parser.Parser;

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
            Parser parser;
            String fileName = args[0];

            
            lexer = new Lexer(fileName);
            parser = new Parser(lexer);
            
            System.out.println("\n\nArquivo " + fileName);
            parser.parse();

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
