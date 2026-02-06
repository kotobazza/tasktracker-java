package com.kotobazza.commandlineprocess;

import com.kotobazza.tasks.Task;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.Map;

@CommandLine.Command(name = "table", description = "Show whole info about tasks")
public class TaskTrackerTable extends TaskTrackerCommandSuperclass{

    @CommandLine.ParentCommand
    private TaskTracker tracker;

    @Override
    public Integer call() throws Exception {
        Map<String, Task> tasks = getService().getTasks(tracker.filePath);

        printGreeting();

        if(tracker.outputWidth == null){
            printDivider(defaultOutputWidth);
            printTasksTable(tasks, defaultOutputWidth);
            printDivider(defaultOutputWidth);
        } else{
            printDivider(tracker.outputWidth);
            printTasksTable(tasks, tracker.outputWidth);
            printDivider(tracker.outputWidth);
        }
        return 0;
    }
}
