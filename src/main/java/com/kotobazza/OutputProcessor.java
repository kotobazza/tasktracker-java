package com.kotobazza;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class OutputProcessor {


    public String convertTaskStateIntoSymbolicForm(TaskState state){
        return switch (state) {
            case TaskState.TO_DO -> "!";
            case TaskState.IN_PROGRESS -> "~";
            case TaskState.DONE -> "✔\uFE0F";
        };
    }

    public int outputWidth = 80;



    public static final DateTimeFormatter TASK_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public String formatDateTime(LocalDateTime time){

        if(time == null){
            return "";
        }

        return time.format(TASK_DATE_FORMATTER);
    }

    public int descriptionSectionSize = outputWidth - 19 - 2 - 19*2 -1;

    private String divider = " ".repeat(outputWidth);
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

    public void printDivider(){
        System.out.println(divider);
    }

    public void printGreeting(){
        System.out.println("\n[Simple Task Tracker]");
    }

    public void printTasksTable(Map<String, Task> tasks){
        if(!tasks.isEmpty()){
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


    public void listTasks(Map<String, Task> tasks){
        if(!tasks.isEmpty()){
            for(Task task : tasks.values()){
                if(task.getState() == TaskState.DONE)
                    continue;
                String bld = "[" +
                        task.getId() +
                        "] " +
                        "<" +
                        convertTaskStateIntoSymbolicForm(task.getState()) +
                        "> " +
                        task.getDescription();
                System.out.println(bld);
            }
        } else {
            System.out.println("No tasks found");
        }
    }
}
