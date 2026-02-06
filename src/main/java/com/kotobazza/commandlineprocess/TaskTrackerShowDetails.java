package com.kotobazza.commandlineprocess;

import picocli.CommandLine;

import java.nio.file.Path;

@CommandLine.Command(name = "show", description = "Show info about user environment")
public class TaskTrackerShowDetails extends TaskTrackerCommandSuperclass {
    @CommandLine.Option(names = {"-f", "--file"},
            description = "Task storage file (json)")
    private Path filePath;

    @Override
    public Integer call() throws Exception {
        Path definedPath = service.getEffectivePathForTaskOnLocation(filePath);

        printGreeting();
        printDivider(defaultOutputWidth);
        System.out.println("Path used to save tasks: " + definedPath.toAbsolutePath().toUri().toASCIIString());
        return 0;
    }
}
