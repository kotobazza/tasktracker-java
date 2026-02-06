package com.kotobazza.commandlineprocess;


import com.kotobazza.tasks.Task;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.Map;

@CommandLine.Command(name = "remove", description = "Remove a task from list")
public class TaskTrackerRemove extends TaskTrackerCommandSuperclass{
    @CommandLine.Parameters(index="0", description="Task identifier")
    private String id;

    @CommandLine.Option(names = {"-f", "--file"},
            description = "Task storage file (json)")
    private Path filePath;


    @Override
    public Integer call() throws Exception {


        if(!service.removeTask(filePath, id)){
            System.out.println("Not removed this task from tasks list: "+id);
            return 127;
        }

        //TODO: double acceptance???
        System.out.println("Accepted.");
        return 0;

    }
}
