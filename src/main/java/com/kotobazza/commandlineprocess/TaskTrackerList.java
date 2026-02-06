package com.kotobazza.commandlineprocess;

import com.kotobazza.tasks.Task;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.Map;

@CommandLine.Command(name = "list", description = "List active tasks")
public class TaskTrackerList extends TaskTrackerCommandSuperclass{
    @CommandLine.ParentCommand
    private TaskTracker tracker;

    @Override
    public Integer call() throws Exception {
        Map<String, Task> tasks = getService().getTasks(tracker.filePath);

        printGreeting();

        if(tracker.outputWidth == null){
            printDivider(defaultOutputWidth);
            listTasks(tasks, defaultOutputWidth);
            printDivider(defaultOutputWidth);
        } else{
            printDivider(tracker.outputWidth);
            listTasks(tasks, tracker.outputWidth);
            printDivider(tracker.outputWidth);

        }
        return 0;
    }
}
