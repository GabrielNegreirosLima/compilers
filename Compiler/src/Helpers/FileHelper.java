package Helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class FileHelper {
    public static ArrayList<String> getFiles() {
        File directory = new File("");
        String path = "/Compiler/src/compiler/Tests";
        String OS = System.getProperty("os.name", "unknown").toLowerCase(Locale.ROOT);
        if(OS.contains("win"))
            path = path.replace('/', '\\');
        String fullPath = directory.getAbsolutePath() + path;
        File[] listFiles = new File(fullPath).listFiles();
        
        ArrayList<String> files = new ArrayList<String>();

        for (File file : listFiles) {
            if (file.isFile()) {
                if(OS.contains("win"))
                    files.add(fullPath + "\\" + file.getName());
                else
                    files.add(fullPath + "/" + file.getName());
            }
        }

        return files;
    }

}
