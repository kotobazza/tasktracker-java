package com.kotobazza.commandlineprocess;


import com.kotobazza.Task;
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
        Map<String, Task> tasks = repo.loadTasksFromLocation(filePath);

        if(!tasks.containsKey(id)){
            System.out.println("No found index in tasks file: "+id);
            return 127;
        }

        //TODO: double acceptance???

        tasks.remove(id);
        if(!repo.saveTasks(tasks.values(), filePath)){
            System.out.println("Not saved tasks into location");
            return 127;
        }
        return 0;

    }
}
