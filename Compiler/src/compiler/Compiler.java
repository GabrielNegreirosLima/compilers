/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.util.ArrayList;
import Helpers.FileHelper;
import lexer.Lexer;

/**
 *
 * @author yanvi
 */
public class Compiler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            ArrayList<String> testFiles = FileHelper.getFiles();
            System.out.println(testFiles);

            Lexer lexer = new Lexer(testFiles.get(0));
            System.out.println((int)'a');
            System.out.println((int)'z');
            System.out.println((int)'A');
            System.out.println((int)'Z');
        } catch (Exception e) {
            System.out.println("Tests files not found.");
        }

        System.out.println("Hello Negreiros!");
    }
    
}
