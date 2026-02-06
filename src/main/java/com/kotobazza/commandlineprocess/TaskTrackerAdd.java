package com.kotobazza.commandlineprocess;


import com.kotobazza.Task;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.Map;

@CommandLine.Command(name = "add", description = "Add a new task")
public class TaskTrackerAdd extends TaskTrackerCommandSuperclass { ;
    @CommandLine.Option(names = {"-f", "--file"},
            description = "Task storage file (json)")
    private Path filePath;

    @CommandLine.Parameters(index="0", description="Task description")
    private String description;


    @Override
    public Integer call() throws Exception {

        if(filePath==null){
            System.out.println("got null filepath");
        }

        Map<String, Task> tasks = loadTasksFromPath(filePath);
        Task newTask = new Task(description);

        tasks.put(newTask.getId(), newTask);

        printGreeting();

        if(repo.saveTasks(tasks.values())){
            System.out.println("Accepted.");
            return 0;
        } else {
            System.err.println("Not accepted");
            return 127;
        }
    }
}
