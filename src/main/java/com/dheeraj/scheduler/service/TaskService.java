package com.dheeraj.scheduler.service;

import com.dheeraj.scheduler.model.Task;
import com.dheeraj.scheduler.repository.TaskRepository;


import java.util.Scanner;

public class TaskService {
    TaskRepository taskRepository;
    Scanner sc;

    public TaskService(TaskRepository taskRepository, Scanner sc) {
        this.taskRepository = taskRepository;
        this.sc = sc;
    }
    public void createTask(Task task){
        taskRepository.addTask(task);
    }
}

