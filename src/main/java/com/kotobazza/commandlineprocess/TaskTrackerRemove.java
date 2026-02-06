package com.kotobazza.commandlineprocess;


import picocli.CommandLine;

@CommandLine.Command(name = "remove", description = "Remove a task from list")
public class TaskTrackerRemove extends TaskTrackerCommandSuperclass{
    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
