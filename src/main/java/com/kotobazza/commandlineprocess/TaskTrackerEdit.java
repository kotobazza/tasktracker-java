package com.kotobazza.commandlineprocess;


import com.kotobazza.Task;
import com.kotobazza.TaskState;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;


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
        Map<String, Task> tasks = loadTasksFromPath(filePath);

        if(!tasks.containsKey(id)){
            System.out.println("No found index in tasks file: "+id);
            return 127;
        }

        Task task = tasks.get(id);
        task.setDescription(newDescription);
        tasks.put(task.getId(), task);
        if(!repo.saveTasks(tasks.values(), filePath)){
            System.out.println("Not saved tasks into location");
            return 127;
        }
        return 0;




    }
}
