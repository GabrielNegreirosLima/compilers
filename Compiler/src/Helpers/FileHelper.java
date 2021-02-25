package Helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class FileHelper {
    public static String getPathDivisor() {
        String divisor = "";
        String OS = System.getProperty("os.name", "unknown").toLowerCase(Locale.ROOT);

        if(OS.contains("win")){
            divisor = "\\";
        }
        else{
            divisor = "/";
        }

        return divisor;
    }

    public static String getFullPath() {
        File directory = new File("");
        String divisor = getPathDivisor();
        
        String path =  divisor + "src" + divisor + "Compiler" + divisor + "Tests" + divisor;    
        System.out.println(directory.getAbsolutePath() + path);
        
        return directory.getAbsolutePath() + path;
    }

    public static ArrayList<String> getAllFiles() {
        String fullPath = getFullPath();

        File[] listFiles = new File(fullPath).listFiles();   
        ArrayList<String> files = new ArrayList<String>();
        
        for (File file : listFiles) {           
            if (file.isFile()) {
                files.add(fullPath + file.getName());                
            }
        }

        return files;
    }

}
