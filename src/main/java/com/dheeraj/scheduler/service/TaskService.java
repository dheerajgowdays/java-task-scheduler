package com.dheeraj.scheduler.service;

import com.dheeraj.scheduler.enums.TaskPriority;
import com.dheeraj.scheduler.model.Task;
import com.dheeraj.scheduler.repository.TaskRepository;


import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

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
    public boolean isPresent(UUID id){
        return taskRepository.isPresent(id);
    }
    public Task viewTask(UUID id){
        return taskRepository.viewTask(id);
    }
    public List<Task> viewAllTask(){
        return taskRepository.viewAllTask();
    }
    public boolean deleteTask(UUID id){
        return taskRepository.deleteTask(id);
    }
    public boolean updateTaskName(UUID id,String name){
        Optional <Task> target = taskRepository.returnTask(id);
        target.ifPresent(task -> task.setName(name));
        return target.isPresent();
    }

    public boolean updateTaskDescription(UUID id, String description){
        Optional <Task> target = taskRepository.returnTask(id);
        target.ifPresent(task -> task.setDescription(description));
        return target.isPresent();                       
    }
    public boolean updateTaskPriority(UUID id, TaskPriority taskPriority){
        Optional <Task> target = taskRepository.returnTask(id);
        target.ifPresent(task -> task.setPriority(taskPriority));
        return target.isPresent();
    }
    public void updateTaskScheduledAt(Instant instant){

    }
}

