package com.kotobazza.tasks;

import java.nio.file.Path;
import java.util.Map;


public class TasksService {

    public TasksRepository getRepo(){
        return new TasksRepository();
    }

    public void addNewTask(Path filePath, String description){
        Map<String, Task> tasks = getRepo().loadTasksFromLocation(filePath);
        Task newTask = new Task(description);

        tasks.put(newTask.getId(), newTask);

        getRepo().saveTasks(tasks.values(), filePath);
    }

    public boolean editTaskDescription(Path filePath, String id, String newDescription){
        Map<String, Task> tasks = getRepo().loadTasksFromLocation(filePath);

        if(!tasks.containsKey(id)){
            return false;
        }

        Task task = tasks.get(id);
        task.setDescription(newDescription);
        tasks.put(task.getId(), task);
        getRepo().saveTasks(tasks.values(), filePath);
        return true;
    }

    public Map<String, Task> getTasks(Path filePath){
        return getRepo().loadTasksFromLocation(filePath);
    }


    public boolean markTaskWithState(Path filePath, String id, TaskState state){
        Map<String, Task> tasks = getRepo().loadTasksFromLocation(filePath);

        if(!tasks.containsKey(id)){
            return false;
        }

        Task task = tasks.get(id);

        task.setState(state);
        tasks.put(task.getId(), task);
        getRepo().saveTasks(tasks.values(), filePath);
        return true;
    }

    public boolean removeTask(Path filePath, String id){
        Map<String, Task> tasks = getRepo().loadTasksFromLocation(filePath);
        boolean existed = tasks.containsKey(id);
        if (existed) {
            tasks.remove(id);
            getRepo().saveTasks(tasks.values(), filePath);
        }
        return existed;
    }


    public Path getEffectivePathForTaskOnLocation(Path location){
        return getRepo().defineEffectivePathToSaveTasks(location);
    }



}
