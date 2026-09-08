package com.dheeraj.scheduler.service;

import com.dheeraj.scheduler.repository.TaskRepository;
import com.dheeraj.scheduler.model.Task;
import java.time.Instant;
import java.util.UUID;

public class SchedulingService {
    TaskRepository taskRepository;
    public SchedulingService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }
    public void scheduleTaskAt(UUID id, Instant scheduleAt){
        Task task= taskRepository.viewTask(id);

    }
}
