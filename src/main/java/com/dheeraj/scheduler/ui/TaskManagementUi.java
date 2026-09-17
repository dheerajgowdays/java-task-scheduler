package com.dheeraj.scheduler.ui;

import com.dheeraj.scheduler.enums.TaskPriority;
import com.dheeraj.scheduler.model.Task;
import com.dheeraj.scheduler.service.TaskService;

import java.time.Instant;
import java.util.Scanner;
import java.util.UUID;

public class TaskManagementUi {
    Scanner sc;
    TaskService taskService;
    public TaskManagementUi(Scanner sc,TaskService taskService) {
        this.sc = sc;
        this.taskService = taskService;
    }
    public void print(Task task){
        System.out.println("-------------------------------------------");
        System.out.println("Task Name        :"+task.getName());
        System.out.println("Task Description :"+task.getDescription());
        System.out.println("Task Status      :"+task.getStatus());
        System.out.println("Task Priority    :"+task.getPriority());
        System.out.println("Task CreatedAt   :"+task.getCreatedAt());
        System.out.println("Task ScheduledAt :"+task.getScheduledAt());
        System.out.println("-------------------------------------------");

    }
    public void createTask(Runnable action) {
        System.out.print("Enter Task Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Task Description: ");
        String description = sc.nextLine();
        try {
            System.out.print("Enter Task Priority: ");
            TaskPriority taskPriority = TaskPriority.valueOf(sc.nextLine().trim().toUpperCase());
            UUID id = UUID.randomUUID();
            Task task = new Task(id, name, description, action, taskPriority);
            taskService.createTask(task);
            System.out.println("\n----------------------------------------------------------------------------");
            System.out.println("           New Task Created With Id: "+id);
            System.out.println("------------------------------------------------------------------------------");
        } catch (IllegalArgumentException e) {
            System.out.println("\n--------------------------------------------------------------");
            System.out.println("       Invalid Input! Please Enter Valid Priority Type Only");
            System.out.println("--------------------------------------------------------------");
        }
    }

    public void viewTask(){
        try{
            System.out.print("Enter the Id: ");
            String i = sc.next();
            UUID uuid = UUID.fromString(i);
            print(taskService.viewTask(uuid));
        }catch (IllegalArgumentException e){
            System.out.println("\n--------------------------------------------------------------");
            System.out.println("          Invalid Input! Please Enter Valid UUID id");
            System.out.println("--------------------------------------------------------------");
        }
    }
    public void viewAllTask(){
        taskService.viewAllTask().forEach(this::print);
    }

    public void updateTask(){
        System.out.println("Enter Task Id to Update: ");
        String id = sc.next();
        UUID uuid = UUID.fromString(id);
        if(taskService.isPresent(uuid)){
        label:
        while(true){
            System.out.println("1. Update Task Name");
            System.out.println("2. Update Task Description");
            System.out.println("3. Update Task Priority");
            System.out.println("4. Update Task ScheduledAt");
            System.out.println("5. Exit");
            System.out.print("\n Enter Your Choice");
            int choice = sc.nextInt();
            switch(choice){
                case 1:
                    System.out.print("Enter Task Name: ");
                    String name = sc.nextLine();
                    taskService.updateTaskName(uuid,name);
                    break;
                case 2:
                    System.out.print("Enter Task Description: ");
                    String description  = sc.nextLine();
                    taskService.updateTaskDescription(uuid,description);
                    break;
                case 3:
                    try{
                    System.out.print("Enter Task Priority: ");
                    TaskPriority priority = TaskPriority.valueOf(sc.nextLine());
                    taskService.updateTaskPriority(uuid,priority);
                    }catch (IllegalArgumentException e){
                         System.out.println("\n--------------------------------------------------------------");
                         System.out.println("          Invalid Input! Please Enter Valid Priority");
                         System.out.println("--------------------------------------------------------------");
                    }        
                    break;
                case 4:
                    System.out.print("Enter Task ScheduledAt: ");
                    String time = sc.nextLine();
                    try{
                    Instant instant = Instant.parse(time);
                    taskService.updateTaskScheduledAt(instant);
                    }catch (IllegalArgumentException e){
                             System.out.println("\n--------------------------------------------------------------");
                             System.out.println("          Invalid Input! Please Enter Valid Scheduled Time");
                             System.out.println("--------------------------------------------------------------");
                    }
                    break;
                case 5:
                    break label;
                default:
                    System.out.println("-----------------------------");
                    System.out.println("   Enter a Valid Choice !");  
                    System.out.println("-----------------------------");          
            }
        }
        }else{
            System.out.println("\n--------------------------------------------------------------");
            System.out.println("          Invalid Input! Please Enter Valid UUID id");
            System.out.println("--------------------------------------------------------------");
        }
    }
    public void deleteTask(){
        try{
            System.out.print("Enter the Id: ");
            String id = sc.next();
            UUID uuid = UUID.fromString(id);
            if(taskService.deleteTask(uuid)) {
                System.out.println("--------------------------------------------------------------");
                System.out.println("         The Task Deleted With Id: " + uuid);
                System.out.println("--------------------------------------------------------------");
            }else {
                System.out.println("--------------------------------------------------------------");
                System.out.println("        The Task Does Not Exist With Id: " + uuid);
                System.out.println("--------------------------------------------------------------");
            }
        }catch (IllegalArgumentException e){
            System.out.println("\n--------------------------------------------------------------");
            System.out.println("          Invalid Input! Please Enter Valid UUID id");
            System.out.println("--------------------------------------------------------------");
        }
    }


}

