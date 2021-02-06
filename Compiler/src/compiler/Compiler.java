/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.util.Locale;

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
		String os = System.getProperty("os.name", "unknown").toLowerCase(Locale.ROOT);
        System.out.println(os);
        System.out.println("Hello Negreiros!");
    }
    
}
