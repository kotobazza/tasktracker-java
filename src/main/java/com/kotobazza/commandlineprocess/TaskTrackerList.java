package com.kotobazza.commandlineprocess;

import com.kotobazza.Task;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.Map;

@CommandLine.Command(name = "list", description = "List active tasks")
public class TaskTrackerList extends TaskTrackerCommandSuperclass{
    @CommandLine.Option(names = {"-f", "--file"},
            description = "Task storage file (json)")
    private Path filePath;

    @CommandLine.Option(names = {"-w", "--width"},
            description = "Output table width")
    private Integer outputWidth;

    @Override
    public Integer call() throws Exception {
        Map<String, Task> tasks = service.getTasks(filePath);

        printGreeting();

        if(outputWidth == null){
            printDivider(defaultOutputWidth);
            listTasks(tasks, defaultOutputWidth);
            printDivider(defaultOutputWidth);
        } else{
            printDivider(outputWidth);
            listTasks(tasks, outputWidth);
            printDivider(outputWidth);

        }
        return 0;
    }
}
