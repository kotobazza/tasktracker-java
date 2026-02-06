package com.kotobazza.commandlineprocess;

import picocli.CommandLine;

import java.nio.file.Path;

@CommandLine.Command(name = "show", description = "Show info about user environment")
public class TaskTrackerShowDetails extends TaskTrackerCommandSuperclass {

    @CommandLine.ParentCommand
    private TaskTracker tracker;

    @Override
    public Integer call() throws Exception {
        Path definedPath = getService().getEffectivePathForTaskOnLocation(tracker.filePath);

        printGreeting();
        printDivider(defaultOutputWidth);
        System.out.println("Path used to save tasks: ");
        printPath(definedPath);
        return 0;
    }
}
