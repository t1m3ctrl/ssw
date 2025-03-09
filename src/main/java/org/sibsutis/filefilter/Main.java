package org.sibsutis.filefilter;

import org.sibsutis.filefilter.services.DataService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Path> inputFiles = new ArrayList<>();
        Path outputPath = Paths.get(".");
        String prefix = "";
        boolean append = false;
        int fullStats = 0;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o": outputPath = Paths.get(args[++i]); break;
                case "-p": prefix = args[++i]; break;
                case "-a": append = true; break;
                case "-f": fullStats = 2; break;
                case "-s": fullStats = 1; break;
                default: inputFiles.add(Paths.get(args[i]));
            }
        }

        if (inputFiles.isEmpty()) {
            System.err.println("Error: no input files specified");
            return;
        }

        new DataService(inputFiles, outputPath, prefix, append, fullStats).start();
    }
}