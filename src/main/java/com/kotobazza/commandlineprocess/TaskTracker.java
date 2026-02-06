package com.kotobazza.commandlineprocess;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "tasktracker",
        version = "TaskTracker 1.0",
        description = "Simple CLI task manager",
        subcommands = {
                TaskTrackerAdd.class,
                TaskTrackerList.class,
                TaskTrackerMark.class,
                TaskTrackerRemove.class,
                TaskTrackerHelp.class,
                TaskTrackerTable.class,
        }
)
public class TaskTracker extends TaskTrackerCommandSuperclass {
    @Override
    public Integer call() throws Exception {
        return 0;
    }

    public void run() {
        System.out.println("Use a subcommand (add, list, ...)");
    }
}
