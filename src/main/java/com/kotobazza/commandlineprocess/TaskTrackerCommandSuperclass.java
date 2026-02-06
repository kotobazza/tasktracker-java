package com.kotobazza.commandlineprocess;

import com.kotobazza.Task;
import com.kotobazza.TaskState;
import com.kotobazza.TasksRepository;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public abstract class TaskTrackerCommandSuperclass implements Callable<Integer> {
    static TasksRepository repo = new TasksRepository();

    public static Map<String, Task> loadTasksFromPath(Path path){

        if(path == null){
            Optional<List<Task>> loadedTasksFromDefault = repo.loadTasksFromDefaultUserLocation();
            return loadedTasksFromDefault.map(tasks -> tasks
                    .stream()
                    .collect(Collectors.toMap(
                            Task::getId,
                            item -> item
                    ))).orElseGet(HashMap::new);
        } else {
            Optional<List<Task>> loadedTasksFromDefault = repo.loadTasksFromLocation(path);
            return loadedTasksFromDefault.map(tasks -> tasks
                    .stream()
                    .collect(Collectors.toMap(
                            Task::getId,
                            item -> item
                    ))).orElseGet(HashMap::new);
        }
    }

    private static String convertTaskStateIntoSymbolicForm(TaskState state){
        return switch (state) {
            case TaskState.TO_DO -> "!";
            case TaskState.IN_PROGRESS -> "~";
            case TaskState.DONE -> "✔\uFE0F";
        };
    }

    protected int defaultOutputWidth = 80;



    public static final DateTimeFormatter TASK_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public String formatDateTime(LocalDateTime time){

        if(time == null){
            return "";
        }

        return time.format(TASK_DATE_FORMATTER);
    }

    private String help = """
            Usage: tasktracker
           \s
            \t\t(to show created tasks and help)
           \s
            \tor tasktracker add <"description">
           \s
            \t\t(to add new task)
           \s
            \tor tasktracker mark <id> <state> \n
            \t\t (to mark task by one of states) \n
           \s
           \s""";

    public void printHelp(){
        System.out.println(help);
    }

    public void printDivider(Integer width){
        System.out.println(" ".repeat(width));
    }

    public void printGreeting(){
        System.out.println("\n[Simple Task Tracker]");
    }

    public void printTasksTable(Map<String, Task> tasks, Integer width){
        if(!tasks.isEmpty()){
            int descriptionSectionSize = width - 19 - 2 - 19*2 -1;
            for(Task task : tasks.values()){
                String emptiness = task.getDescription().length() >= descriptionSectionSize ?
                        "..." : " ".repeat(descriptionSectionSize - task.getDescription().length());

                String fitDescription = task.getDescription().length() >=descriptionSectionSize ?
                        task.getDescription().substring(0, descriptionSectionSize-3) + emptiness :
                        task.getDescription() + emptiness;

                String bld = "[" +
                        task.getId() +
                        "] " +
                        "<" +
                        convertTaskStateIntoSymbolicForm(task.getState()) +
                        ">  " +
                        fitDescription +
                        " "+
                        formatDateTime(task.getCreatedAt()) +
                        "|" +
                        formatDateTime(task.getUpdatedAt());
                System.out.println(bld);
            }
        } else {
            System.out.println("No tasks found");
        }
    }


    public static void listTasks(Map<String, Task> tasks, Integer width){
        if(!tasks.isEmpty()){
            int descriptionSectionSize = width - 19 - 2 - 19*2 -1;
            for(Task task : tasks.values()){
                if(task.getState() == TaskState.DONE)
                    continue;

                String emptiness = task.getDescription().length() >= descriptionSectionSize ?
                        "..." : " ".repeat(descriptionSectionSize - task.getDescription().length());

                String fitDescription = task.getDescription().length() >=descriptionSectionSize ?
                        task.getDescription().substring(0, descriptionSectionSize-3) + emptiness :
                        task.getDescription() + emptiness;

                String bld = "[" +
                        task.getId() +
                        "] " +
                        "<" +
                        convertTaskStateIntoSymbolicForm(task.getState()) +
                        "> " +
                        fitDescription;
                System.out.println(bld);
            }
        } else {
            System.out.println("No tasks found");
        }
    }
}
