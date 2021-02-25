/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compiler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import Helpers.FileHelper;
import Lexer.Lexer;
import Lexer.Token;

/**
 *
 * @author yanvi
 */
public class Compiler 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        // TODO code application logic here
        try 
        {
            Lexer lexer;
            Token token;
            System.out.println("Compilador.\nPara compilar um arquivo, o mesmo deve estar presentes na pasta \"Tests\"");
            System.out.println("Digite o nome do arquivo para ser compilado:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String fileName = reader.readLine();

            lexer = new Lexer(FileHelper.getFullPath() + fileName);
            token = lexer.scan();

            System.out.println("\n\nArquivo " + fileName);
            boolean isEOF = lexer.getIsEOF();

            while(!token.toString().equals("<257, stop>"))
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
        catch (Exception e) 
        {
            System.out.println("Tests files not found.");
        }        
    }    
}
