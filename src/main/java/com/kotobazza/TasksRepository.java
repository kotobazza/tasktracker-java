package com.kotobazza;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class TasksRepository {
    private final Path defaultAppLocation;

    private final ObjectMapper tasksMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .enable(SerializationFeature.INDENT_OUTPUT);;

    public TasksRepository(){
        String xdgConfigHome = System.getenv("XDG_DATA_HOME");
        Path baseDir;

        if (xdgConfigHome != null && !xdgConfigHome.isBlank()) {
            baseDir = Paths.get(xdgConfigHome);
        } else {
            baseDir = Paths.get(System.getProperty("user.home"), ".local", "share");
        }

        defaultAppLocation = baseDir.resolve("com.kotobazza.tasktracker");

        try{
            if(!Files.exists(defaultAppLocation))
                Files.createDirectories(defaultAppLocation);
        } catch (IOException ignored){}
    }


    private void createFileIfNotExists(Path path) throws IOException {
        if(!Files.exists(path))
            Files.createFile(path);
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

    public boolean saveTasks(Iterable<Task> tasks, Path saveInto){
        try{
            Path tasksJson = defineEffectivePathToSaveTasks(saveInto);

            createFileIfNotExists(tasksJson);

            tasksMapper.writeValue(new File(tasksJson.toUri()), tasks);

            return true;
        } catch (IOException e){
            return false;
        }
    }

    public Optional<List<Task>> loadTasksFromDefaultUserLocation() {
        return loadTasksFromLocation(defaultAppLocation.resolve("tasks.json"));
    }

    public Optional<List<Task>> loadTasksFromLocation(Path location){
        try{
            Path tasksJson = defineEffectivePathToSaveTasks(location);

            createFileIfNotExists(tasksJson);

            List<Task> tasks = tasksMapper.readValue(
                    new File(tasksJson.toUri()),
                    new TypeReference<>() {}
            );
            return Optional.of(tasks);
        } catch (IOException e){
            return Optional.empty();
        }
    }


}
