package com.kotobazza;

import java.nio.file.Path;
import java.util.Map;


public class TasksService {
    private static final TasksRepository repo = new TasksRepository();

    public void addNewTask(Path filePath, String description){
        Map<String, Task> tasks = repo.loadTasksFromLocation(filePath);
        Task newTask = new Task(description);

        tasks.put(newTask.getId(), newTask);

        repo.saveTasks(tasks.values(), filePath);
    }

    public boolean editTaskDescription(Path filePath, String id, String newDescription){
        Map<String, Task> tasks = repo.loadTasksFromLocation(filePath);

        if(!tasks.containsKey(id)){
            return false;
        }

        Task task = tasks.get(id);
        task.setDescription(newDescription);
        tasks.put(task.getId(), task);
        repo.saveTasks(tasks.values(), filePath);
        return true;
    }

    public Map<String, Task> getTasks(Path filePath){
        return repo.loadTasksFromLocation(filePath);
    }


    public boolean markTaskWithState(Path filePath, String id, TaskState state){
        Map<String, Task> tasks = repo.loadTasksFromLocation(filePath);

        if(!tasks.containsKey(id)){
            return false;
        }

        Task task = tasks.get(id);

        task.setState(state);
        tasks.put(task.getId(), task);
        return true;
    }

    public boolean removeTask(Path filePath, String id){
        return repo.loadTasksFromLocation(filePath).remove(id) == null;
    }


    public Path getEffectivePathForTaskOnLocation(Path location){
        return repo.defineEffectivePathToSaveTasks(location);
    }



}
