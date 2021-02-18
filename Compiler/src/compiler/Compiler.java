/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.util.ArrayList;
import java.util.Iterator;

import Helpers.FileHelper;
import lexer.Lexer;
import lexer.Token;

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
            ArrayList<String> testFiles = FileHelper.getFiles();
            Iterator<String> i = testFiles.iterator();
            System.out.println(testFiles);

            while (i.hasNext()) 
            {
                lexer = new Lexer(i.next().toString());
                token = lexer.scan();

                while(!token.toString().equals("end"))
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
                        
                }
            }

        } 
        
        catch (Exception e) 
        {
            System.out.println("Tests files not found.");
        }        
    }    
}
