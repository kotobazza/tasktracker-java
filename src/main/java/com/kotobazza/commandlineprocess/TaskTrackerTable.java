package com.kotobazza.commandlineprocess;

import com.kotobazza.Task;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.Map;

@CommandLine.Command(name = "table", description = "Show whole info about tasks")
public class TaskTrackerTable extends TaskTrackerCommandSuperclass{
    @CommandLine.Option(names = {"-f", "--file"},
            description = "Task storage file (json)")
    private Path filePath;

    @CommandLine.Option(names = {"-w", "--width"},
            description = "Output table width")
    private Integer outputWidth;

    @Override
    public Integer call() throws Exception {
        Map<String, Task> tasks = loadTasksFromPath(filePath);

        printGreeting();

        if(outputWidth == null){
            printDivider(defaultOutputWidth);
            printTasksTable(tasks, defaultOutputWidth);
            printDivider(defaultOutputWidth);
        } else{
            printDivider(outputWidth);
            printTasksTable(tasks, outputWidth);
            printDivider(outputWidth);
        }
        return 0;
    }
}
