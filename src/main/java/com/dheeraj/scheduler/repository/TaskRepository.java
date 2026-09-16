package com.dheeraj.scheduler.repository;

import com.dheeraj.scheduler.model.Task;

import java.util.*;

public class TaskRepository {
    private final Queue<Task> taskQueue = new PriorityQueue<>(
            Comparator.comparing(Task::getPriority)
    );

    public void addTask(Task task){
        taskQueue.add(task);
    }
    public Task viewTask(UUID id){
        return taskQueue.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst().orElse(null);
    }
    public Optional<Task> returnTask(UUID id){
        return taskQueue.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }
    public List<Task> viewAllTask(){
        return taskQueue.stream().toList();
    }

    public boolean isPresent(UUID id){
        return taskQueue.stream().anyMatch(task -> task.getId().equals(id));
    }

    public Boolean deleteTask(UUID id){
       return taskQueue.removeIf(task -> task.getId().equals(id));
    }
}
