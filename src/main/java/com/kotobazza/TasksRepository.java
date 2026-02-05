package com.kotobazza;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
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
        String xdgConfigHome = System.getenv("XDG_CONFIG_HOME");
        Path baseDir;

        if (xdgConfigHome != null && !xdgConfigHome.isBlank()) {
            baseDir = Paths.get(xdgConfigHome);
        } else {
            baseDir = Paths.get(System.getProperty("user.home"), ".local", "share");
        }

        defaultAppLocation = baseDir.resolve("com.kotobazza.tasktracker");

    }

    //TODO: unblocking needed

    public boolean saveTasks(Iterable<Task> tasks){
        try{
            tasksMapper.writeValue(new File(defaultAppLocation.resolve("tasks.json").toUri()), tasks);
            return true;
        } catch (IOException e){
            return false;
        }
    }



    //TODO: unblocking needed

    public Optional<List<Task>> loadTasksFromDefaultUserLocation() {
        return loadTasksFromLocation(defaultAppLocation.resolve("tasks.json"));
    }

    //TODO: unblocking needed

    public Optional<List<Task>> loadTasksFromLocation(Path location){
        try{
            List<Task> tasks = tasksMapper.readValue(
                    new File(location.toUri()),
                    new TypeReference<List<Task>>() {}
            );
            return Optional.of(tasks);
        } catch (IOException e){
            return Optional.empty();
        }
    }


}
