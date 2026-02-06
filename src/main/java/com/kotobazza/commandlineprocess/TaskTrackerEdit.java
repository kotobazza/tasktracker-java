package com.kotobazza.commandlineprocess;


import com.kotobazza.tasks.TaskNotFoundException;
import picocli.CommandLine;
import java.nio.file.Path;


@CommandLine.Command(name = "edit", description = "Edit task description")
public class TaskTrackerEdit extends TaskTrackerCommandSuperclass{

    @CommandLine.ParentCommand
    private TaskTracker tracker;

    @CommandLine.Parameters(index="0", description="Task identifier")
    private String id;

    @CommandLine.Parameters(index="1", description="New task description")
    private String newDescription;

    @Override
    public Integer call() throws Exception {
        if(!getService().editTaskDescription(tracker.filePath, id, newDescription)){
            throw new TaskNotFoundException("Didn't find a task in tasks list with this id: "+id);
        }
        System.out.println("Accepted.");
        return 0;




    }
}
