package com.kotobazza.commandlineprocess;

import com.kotobazza.tasks.TaskState;
import picocli.CommandLine;
import java.nio.file.Path;
import java.util.Locale;


@CommandLine.Command(name = "mark", description = "Mark task state")
public class TaskTrackerMark extends TaskTrackerCommandSuperclass{

    @CommandLine.Parameters(index="0", description="Task identifier")
    private String id;

    @CommandLine.Parameters(index="1", description="Task state")
    private String state;

    @CommandLine.Option(names = {"-f", "--file"},
            description = "Task storage file (json)")
    private Path filePath;

    @Override
    public Integer call() throws Exception {
        try{
            TaskState newState = TaskState.valueOf(state.toUpperCase(Locale.ROOT));

            if(!service.markTaskWithState(filePath, id, newState)){
                System.out.println("Not saved tasks into location");
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
