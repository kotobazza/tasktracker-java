package com.kotobazza.commandlineprocess;

import com.kotobazza.tasks.TaskNotFoundException;
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
            throw new TaskNotFoundException("Didn't find a task in tasks list with this id: "+id);
        }

        //TODO: double acceptance???
        System.out.println("Accepted.");
        return 0;

    }
}
