package com.kotobazza;

import com.kotobazza.commandlineprocess.TaskTracker;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {

        CommandLine cmd = new CommandLine(new TaskTracker());
        cmd.setExecutionExceptionHandler((ex, cmdLine, parseResult) -> {
            System.err.println("[!] " + ex.getMessage());
            return 1;
        });
        System.exit(cmd.execute(args));
    }
}