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
        String path = divisor + "src" + divisor + "compiler" + divisor + "Tests";
        
        System.out.println(path);

        String fullPath = directory.getAbsolutePath() + path;
        
        System.out.println(fullPath);
        
        File[] listFiles = new File(fullPath).listFiles();
        
        System.out.println(listFiles);
             
        ArrayList<String> files = new ArrayList<String>();
        
        for (File file : listFiles) {
            System.out.println("passei auqi 3");
            if (file.isFile()) {
                files.add(fullPath + divisor + file.getName());
                System.out.println("passei auqi");
            }
        }

        return files;
    }

}
