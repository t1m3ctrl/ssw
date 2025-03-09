package org.sibsutis.filefilter.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.*;
import java.util.stream.Collectors;

public class DataService {
    private static final Pattern INTEGER_PATTERN = Pattern.compile("^-?\\d+$");
    private static final Pattern FLOAT_PATTERN = Pattern.compile("^-?\\d*\\.\\d+$");

    private final Path outputPath;
    private final String prefix;
    private final boolean append;
    private final int fullStats;
    private final List<Path> inputFiles;

    final List<Integer> integers = new ArrayList<>();
    final List<Double> floats = new ArrayList<>();
    private final List<String> strings = new ArrayList<>();

    public DataService(List<Path> inputFiles, Path outputPath, String prefix, boolean append, int fullStats) {
        this.inputFiles = inputFiles;
        this.outputPath = outputPath;
        this.prefix = prefix;
        this.append = append;
        this.fullStats = fullStats;
    }

    public void start() {
        for (Path inputFile : inputFiles) {
            try (BufferedReader br = Files.newBufferedReader(inputFile)) {
                String line;
                while ((line = br.readLine()) != null) {
                    classify(line.trim());
                }
            } catch (IOException e) {
                System.err.println("Error in reading file: " + e.getMessage());
            }
        }
        writeResults();
        if (fullStats != 0) {
            printStats();
        }
    }

    void classify(String line) {
        if (INTEGER_PATTERN.matcher(line).matches()) {
            integers.add(Integer.parseInt(line));
        } else if (FLOAT_PATTERN.matcher(line).matches()) {
            floats.add(Double.parseDouble(line));
        } else {
            strings.add(line);
        }
    }

    private void writeResults() {
        try {
            if (!integers.isEmpty()) writeToFile("integers.txt",
                    integers.stream()
                            .map(String::valueOf).collect(Collectors.toList()));
            if (!floats.isEmpty()) writeToFile("floats.txt",
                    floats.stream()
                            .map(String::valueOf).collect(Collectors.toList()));
            if (!strings.isEmpty()) writeToFile("strings.txt", strings);
        } catch (IOException e) {
            System.err.println("Error in writing results " + e.getMessage());
        }
    }

    private void writeToFile(String filename, List<String> lines) throws IOException {
        Path path = outputPath.resolve(prefix + filename);
        Files.write(path, lines, append ? StandardOpenOption.APPEND : StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    }

    private void printStats() {
        System.out.println("Stats:");
        printIntegerStats();
        printFloatStats();
        printStringStats();
    }

    private void printIntegerStats() {
        if (integers.isEmpty()) return;
        System.out.println("INTEGER: " + integers.size());
        if (fullStats == 2) {
            int min = Collections.min(integers);
            int max = Collections.max(integers);
            int sum = integers.stream().mapToInt(Integer::intValue).sum();
            double avg = integers.stream().mapToInt(Integer::intValue).average().orElse(0);
            System.out.println("  min: " + min);
            System.out.println("  max: " + max);
            System.out.println("  sum: " + sum);
            System.out.println("  avg: " + avg);
        }
    }

    private void printFloatStats() {
        if (floats.isEmpty()) return;
        System.out.println("FLOAT: " + floats.size());
        if (fullStats == 2) {
            double min = Collections.min(floats);
            double max = Collections.max(floats);
            double sum = floats.stream().mapToDouble(Double::doubleValue).sum();
            double avg = floats.stream().mapToDouble(Double::doubleValue).average().orElse(0);
            System.out.println("  min: " + min);
            System.out.println("  max: " + max);
            System.out.println("  sum: " + sum);
            System.out.println("  avg: " + avg);
        }
    }

    private void printStringStats() {
        if (strings.isEmpty()) return;
        System.out.println("STRING: " + strings.size());
        if (fullStats == 2) {
            int minLen = strings.stream().mapToInt(String::length).min().orElse(0);
            int maxLen = strings.stream().mapToInt(String::length).max().orElse(0);
            System.out.println("  shortest string length: " + minLen);
            System.out.println("  longest string length: " + maxLen);
        }
    }
}
