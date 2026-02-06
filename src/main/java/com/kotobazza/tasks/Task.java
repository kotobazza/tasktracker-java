package com.kotobazza.tasks;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Task {
    private final String id;
    private String description;
    private TaskState state;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Task(String description) {
        this.id = UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 12);
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.state = TaskState.TO_DO;
    }

    @JsonCreator
    public Task(
            @JsonProperty("id") String id,
            @JsonProperty("description") String description,
            @JsonProperty("state") TaskState state,
            @JsonProperty("createdAt") LocalDateTime createdAt,
            @JsonProperty("updatedAt") LocalDateTime updatedAt
    ) {
        this.id = id;
        this.description = description;
        this.state = state;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public TaskState getState() {
        return state;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getCreatedAt() { return createdAt; }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getUpdatedAt() { return updatedAt; }


    public void setDescription(String description) {
        this.description = description;
        updateTime(LocalDateTime.now());
    }

    public void setState(TaskState state) {
        this.state = state;
        updateTime(LocalDateTime.now());
    }

    private void updateTime(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", state=" + state +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
