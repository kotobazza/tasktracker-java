package com.kotobazza.tasks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TasksRepository {
    private final Path defaultAppLocation;

    private final ObjectMapper tasksMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .enable(SerializationFeature.INDENT_OUTPUT);;

    public TasksRepository(){
        String xdgDataHome = System.getenv("XDG_DATA_HOME");
        Path baseDir;

        if (xdgDataHome != null && !xdgDataHome.isBlank()) {
            baseDir = Paths.get(xdgDataHome);
        } else {
            baseDir = Paths.get(System.getProperty("user.home"), ".local", "share");
        }

        defaultAppLocation = baseDir.resolve("com.kotobazza.tasktracker");
    }


    private void createFileIfNotExists(Path path) throws IOException {
        if(!Files.exists(path)){
            Files.createDirectories(path.getParent());
            Files.createFile(path);
            Files.writeString(path, "[]", Charset.defaultCharset());
        }

    }

    public Path defineEffectivePathToSaveTasks(Path location){
        if(location == null){
            return defaultAppLocation.resolve("tasks.json");
        } else if(location.toFile().isDirectory()){
            return location.resolve("tasks.json");
        } else{
            return location;
        }
    }

    public void saveTasks(Iterable<Task> tasks, Path saveInto) throws TasksFilePrepareException, TasksSaveException {
        Path tasksJson = defineEffectivePathToSaveTasks(saveInto);

        try{
            createFileIfNotExists(tasksJson);
        } catch (Exception e) {
            throw new TasksFilePrepareException("Didn't prepare tasks file due to IO error: " + e.getMessage());
        }

        try {
            tasksMapper.writeValue(new File(tasksJson.toUri()), tasks);
        } catch (Exception e) {
            throw new TasksSaveException("Didn't save tasks into file due to task mapper error: " + e.getMessage());
        }
    }

    public Map<String, Task> loadTasksFromLocation(Path location) throws TasksFilePrepareException, TasksLoadException{
        Path tasksJson = defineEffectivePathToSaveTasks(location);
        try{
            createFileIfNotExists(tasksJson);
        } catch (Exception e) {
            throw new TasksFilePrepareException("Didn't prepare tasks file due to IO error: " + e.getMessage());
        }


        try{
            List<Task> loadedTasksFromDefault = tasksMapper.readValue(
                    new File(tasksJson.toUri()),
                    new TypeReference<>() {}
            );

            return loadedTasksFromDefault.stream()
                    .collect(Collectors.toMap(
                            Task::getId,
                            item -> item
                    ));
        } catch (Exception e){
            throw new TasksLoadException("Didn't load tasks from file due to task mapper error: " + e.getMessage());
        }
    }


}
