package com.kotobazza.commandlineprocess;


import picocli.CommandLine;
import java.nio.file.Path;


@CommandLine.Command(name = "edit", description = "Edit task description")
public class TaskTrackerEdit extends TaskTrackerCommandSuperclass{

    @CommandLine.Parameters(index="0", description="Task identifier")
    private String id;

    @CommandLine.Parameters(index="1", description="New task description")
    private String newDescription;

    @CommandLine.Option(names = {"-f", "--file"},
            description = "Task storage file (json)")
    private Path filePath;

    @Override
    public Integer call() throws Exception {
        if(!getService().editTaskDescription(filePath, id, newDescription)){
            System.out.println("Not accepted.");
            return 127;
        }
        System.out.println("Accepted.");
        return 0;




    }
}
