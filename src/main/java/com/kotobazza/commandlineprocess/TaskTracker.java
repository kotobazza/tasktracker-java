package com.kotobazza.commandlineprocess;

import picocli.CommandLine;

@CommandLine.Command(
        name = "tasktracker",
        version = "TaskTracker 1.0",
        description = "Simple CLI task manager",
        subcommands = {
                TaskTrackerAdd.class,
                TaskTrackerList.class,
                TaskTrackerMark.class,
                TaskTrackerRemove.class,
                TaskTrackerTable.class,
                TaskTrackerEdit.class
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
