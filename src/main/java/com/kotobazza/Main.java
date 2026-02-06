package com.kotobazza;

import com.kotobazza.commandlineprocess.TaskTracker;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new TaskTracker()).execute(args);
        System.exit(exitCode);
    }
}