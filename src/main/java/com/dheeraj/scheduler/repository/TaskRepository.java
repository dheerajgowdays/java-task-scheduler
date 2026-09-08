package com.dheeraj.scheduler.repository;

import com.dheeraj.scheduler.model.Task;

import java.util.*;

public class TaskRepository {
    private final Queue<Task> taskQueue = new PriorityQueue<>(
            Comparator.comparing(Task::getScheduledAt)
    );

    public void addTask(Task task){
        taskQueue.add(task);
    }
    public Task viewTask(UUID id){
        return taskQueue.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst().orElse(null);
    }
}
