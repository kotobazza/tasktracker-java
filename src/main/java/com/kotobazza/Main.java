package com.kotobazza;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        TasksRepository repo = new TasksRepository();
        OutputProcessor output = new OutputProcessor();

        Map<String, Task> tasks;
        Optional<List<Task>> loadedTasksFromDefault = repo.loadTasksFromDefaultUserLocation();
        if(loadedTasksFromDefault.isEmpty()){
            System.out.println("[!] No found tasks in default location, creating empty storage");
            tasks = new HashMap<>();
        } else {
            tasks = loadedTasksFromDefault
                    .get()
                    .stream()
                    .collect(Collectors.toMap(
                            Task::getId,
                            item -> item
                    ));
        }


        output.printGreeting();

        if(args.length == 0){
            output.printDivider();
            output.listTasks(tasks);
            output.printDivider();
        } else {
            switch(args[0]){
                case "table" -> {
                    output.printDivider();
                    output.printTasksTable(tasks);
                    output.printDivider();
                }
                case "list" -> {
                    output.printDivider();
                    output.listTasks(tasks);
                    output.printDivider();
                }
                case "add" -> {
                    if(args.length == 1){
                        System.out.println("[!] Give a description for a task!");
                        return;
                    }
                    Task newTask = new Task(args[1]);
                    tasks.put(newTask.getId(), newTask);
                    repo.saveTasks(tasks.values());
                }

                case "mark" -> {
                    if(args.length != 3){
                        System.out.println("[!] Give only identifier and mark for a task!");
                        return;
                    }
                    if(!tasks.containsKey(args[1])){
                        System.out.println("[!] Didn't find a task with id: " + args[1] + ". Aborted.");
                        return;
                    }

                    try{
                        TaskState newState = TaskState.valueOf(args[2].toUpperCase(Locale.ROOT));
                        Task task = tasks.get(args[1]);
                        task.setState(newState);
                        tasks.put(task.getId(), task);
                        if(!repo.saveTasks(tasks.values())){
                            System.out.println("[!] Tasks weren't saved!");
                        }
                    } catch (IllegalArgumentException e){
                        System.out.println("[!] Didn't find available task state:  " + args[2] + ". Aborted.");

                    }
                }

                case "remove" -> {
                    for(int i = 1; i<args.length; i++){
                        if(tasks.containsKey(args[i])){
                            tasks.remove(args[i]);
                            System.out.println(args[i]);
                        }
                    }
                }

                default -> {
                    System.out.println("No found subcommand!");
                    output.printHelp();
                }
            }

        }
    }
}