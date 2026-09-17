package com.dheeraj.scheduler.service;

import com.dheeraj.scheduler.engine.SchedulerEngine;
import com.dheeraj.scheduler.repository.TaskRepository;
import com.dheeraj.scheduler.model.Task;
import java.time.Instant;
import java.util.UUID;

public class SchedulingService {
    private final TaskRepository taskRepository;
    private final SchedulerEngine schedulerEngine;
    public SchedulingService(TaskRepository taskRepository,SchedulerEngine schedulerEngine){
        this.taskRepository = taskRepository;
        this.schedulerEngine = schedulerEngine;
    }
    public void scheduleTaskAt(UUID id, Instant scheduleAt){
        Task task= taskRepository.viewTask(id);
        if(task == null){
            throw new IllegalArgumentException("Task does not exist: "+id);
        }
        task.schedule(scheduleAt);
        schedulerEngine.schedule(task);
        System.out.println("Task Scheduled Successfully!");
    }
}
