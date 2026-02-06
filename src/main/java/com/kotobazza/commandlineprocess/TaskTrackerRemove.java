package com.kotobazza.commandlineprocess;

import picocli.CommandLine;
import java.nio.file.Path;


@CommandLine.Command(name = "remove", description = "Remove a task from list")
public class TaskTrackerRemove extends TaskTrackerCommandSuperclass{

    @CommandLine.ParentCommand
    private TaskTracker tracker;

    @CommandLine.Parameters(index="0", description="Task identifier")
    private String id;


    @Override
    public Integer call() throws Exception {


        if(!getService().removeTask(tracker.filePath, id)){
            System.out.println("Not removed this task from tasks list: "+id);
            return 127;
        }

        //TODO: double acceptance???
        System.out.println("Accepted.");
        return 0;

    }
}
