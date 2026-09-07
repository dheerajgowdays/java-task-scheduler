package com.dheeraj.scheduler.model;

import com.dheeraj.scheduler.enums.TaskPriority;
import com.dheeraj.scheduler.enums.TaskStatus;


import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Task {
    private final UUID id;
    private String name;
    private String description;
    private final Runnable action;
    private TaskPriority priority;
    private  TaskStatus status;
    private final Instant createdAt;
    private Instant scheduledAt;

    public Task(UUID id,String name,String description,Runnable action,TaskPriority priority,Instant scheduledAt){
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.description = description;
        this.action = Objects.requireNonNull(action);
        this.priority = Objects.requireNonNull(priority);
        this.status = TaskStatus.CREATED;
        this.createdAt = Instant.now();
        schedule(scheduledAt);
    }

    public UUID getId() {
        return id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public TaskPriority getPriority(){
        return priority;
    }
    public void setPriority(TaskPriority priority){
        this.priority = priority;
    }
    public TaskStatus getStatus(){
        return status;
    }
    public Instant getCreatedAt(){
        return createdAt;
    }
    public Instant getScheduledAt(){
        return scheduledAt;
    }
    public Runnable getAction() {
        return action;
    }
    public void schedule(Instant scheduledAt){
        if(status != TaskStatus.CREATED){
            throw new IllegalStateException("Invalid state transition. Task cannot be scheduled for state "+this.status);
        }
        if(scheduledAt == null || scheduledAt.isBefore(Instant.now()) || scheduledAt.equals(Instant.now())){
            throw new IllegalArgumentException("The Schedule time must be not null and in the future");
        }
        this.scheduledAt = scheduledAt;
        this.status = TaskStatus.SCHEDULED;
    }
    public void start(){
        if(status != TaskStatus.SCHEDULED){
            throw new IllegalStateException("Invalid state transition. Task cannot be started form state "+this.status);
        }
        this.status = TaskStatus.RUNNING;
    }
    public void complete(){
        if(status != TaskStatus.RUNNING){
            throw new IllegalStateException("Invalid state transition. Task cannot be completed form state "+this.status);
        }
        this.status = TaskStatus.COMPLETED;
    }
    public void fail(){
        if(status != TaskStatus.RUNNING){
            throw new IllegalStateException("Invalid state transition. Task cannot be failed form state "+this.status);
        }
        this.status = TaskStatus.FAILED;
    }
    public void cancel(){
        if(status != TaskStatus.CREATED && status != TaskStatus.SCHEDULED){
            throw new IllegalStateException("Invalid state transition. Task cannot be scheduled for state "+this.status);
        }
        this.status = TaskStatus.CANCELLED;
    }
}
