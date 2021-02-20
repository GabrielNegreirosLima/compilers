package Helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class FileHelper {
    public static ArrayList<String> getFiles() {
        File directory = new File("");
        String divisor = "";
        String OS = System.getProperty("os.name", "unknown").toLowerCase(Locale.ROOT);
        if(OS.contains("win")){
            divisor = "\\";
        }
        else{
            divisor = "/";
        }
        String path = divisor + "Compiler" + divisor + "src" + divisor + "compiler" + divisor + "Tests";    
        
        String fullPath = directory.getAbsolutePath() + path;       
        
        File[] listFiles = new File(fullPath).listFiles();   
        
        ArrayList<String> files = new ArrayList<String>();
        
        for (File file : listFiles) {           
            if (file.isFile()) {
                files.add(fullPath + divisor + file.getName());                
            }
        }

        return files;
    }

}
