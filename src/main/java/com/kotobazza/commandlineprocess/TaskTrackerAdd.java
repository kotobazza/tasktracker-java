package com.kotobazza.commandlineprocess;

import picocli.CommandLine;
import java.nio.file.Path;

@CommandLine.Command(name = "add", description = "Add a new task")
public class TaskTrackerAdd extends TaskTrackerCommandSuperclass { ;
    @CommandLine.Option(names = {"-f", "--file"},
            description = "Task storage file (json)")
    private Path filePath;

    @CommandLine.Parameters(index="0", description="Task description")
    private String description;


    @Override
    public Integer call() throws Exception {

        getService().addNewTask(filePath, description);

        System.out.println("Accepted.");
        return 0;




    }
}
