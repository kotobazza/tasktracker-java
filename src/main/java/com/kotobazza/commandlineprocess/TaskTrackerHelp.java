package com.kotobazza.commandlineprocess;


import picocli.CommandLine;

@CommandLine.Command(name = "help")
public class TaskTrackerHelp extends TaskTrackerCommandSuperclass{
    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
