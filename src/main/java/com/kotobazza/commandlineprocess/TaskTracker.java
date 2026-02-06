package com.kotobazza.commandlineprocess;

import picocli.CommandLine;

import java.nio.file.Path;

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
                TaskTrackerEdit.class,
                TaskTrackerShowDetails.class
        }
)
public class TaskTracker extends TaskTrackerCommandSuperclass {
    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;

    @CommandLine.Option(names = {"-f", "--file"},
            description = "Task storage file (json)",
            scope= CommandLine.ScopeType.INHERIT)
    Path filePath;

    @CommandLine.Option(names = {"-w", "--width"},
            description = "Output table width",
            scope= CommandLine.ScopeType.INHERIT)
    Integer outputWidth;

    @Override
    public Integer call() {
        spec.commandLine().usage(System.out);
        return 0;
    }

    public void run() {
        System.out.println("Use a subcommand (add, list, ...)");
    }
}
