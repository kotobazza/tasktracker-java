package com.kotobazza.commandlineprocess;


import com.kotobazza.Task;
import com.kotobazza.TaskState;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;


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
        Map<String, Task> tasks = repo.loadTasksFromLocation(filePath);

        if(!tasks.containsKey(id)){
            System.out.println("No found index in tasks file: "+id);
            return 127;
        }

        try{
            TaskState newState = TaskState.valueOf(state.toUpperCase(Locale.ROOT));
            Task task = tasks.get(id);

            task.setState(newState);
            tasks.put(task.getId(), task);
            if(!repo.saveTasks(tasks.values(), filePath)){
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
