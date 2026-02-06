package com.kotobazza.commandlineprocess;

import com.kotobazza.tasks.TaskState;
import picocli.CommandLine;
import java.nio.file.Path;
import java.util.Locale;


@CommandLine.Command(name = "mark", description = "Mark task state")
public class TaskTrackerMark extends TaskTrackerCommandSuperclass{

    @CommandLine.ParentCommand
    private TaskTracker tracker;

    @CommandLine.Parameters(index="0", description="Task identifier")
    private String id;

    @CommandLine.Parameters(index="1", description="Task state")
    private String state;

    @Override
    public Integer call() throws Exception {
        try{
            TaskState newState = TaskState.valueOf(state.toUpperCase(Locale.ROOT));

            if(!getService().markTaskWithState(tracker.filePath, id, newState)){
                System.out.println("Not marked this task from tasks list: "+id);
                return 127;
            }

            System.out.println("Accepted.");
            return 0;

        } catch (IllegalArgumentException e){
            System.out.println("Got undefined state: "+state);
            return 127;
        }




    }
}
