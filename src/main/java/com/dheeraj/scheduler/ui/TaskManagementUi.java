package com.dheeraj.scheduler.ui;

import com.dheeraj.scheduler.enums.TaskPriority;
import com.dheeraj.scheduler.model.Task;
import com.dheeraj.scheduler.service.TaskService;

import java.time.Instant;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

public class TaskManagementUi {
    Scanner sc;
    TaskService taskService;
    public TaskManagementUi(Scanner sc,TaskService taskService) {
        this.sc = sc;
        this.taskService = taskService;
    }
    public void createTask(Runnable action){
        try{
            System.out.print("Enter Task Name: ");
            String name = sc.nextLine();
            try{
                System.out.print("Enter Task Description: ");
                String description = sc.nextLine();
                try{
                    System.out.print("Enter Task Priority: ");
                    TaskPriority taskPriority = TaskPriority.valueOf(sc.nextLine().trim().toUpperCase());
                    try{
                        System.out.print("Enter Scheduled Time: ");
                        String time = sc.nextLine();
                        Instant instant = Instant.parse(time);

                        Task task = new Task(UUID.randomUUID(),name,description,action,taskPriority,instant);
                        taskService.createTask(task);
                        System.out.println("\n-----------------------------------------");
                        System.out.println("           New Task Created ");
                        System.out.println("-----------------------------------------");
                    }catch (IllegalArgumentException e){
                        System.out.println("\n--------------------------------------------------------------");
                        System.out.println("       Invalid Input! Please Enter Valid Scheduled Time Only");
                        System.out.println("--------------------------------------------------------------");
                    }
                }catch (IllegalArgumentException e){
                    System.out.println("\n--------------------------------------------------------------");
                    System.out.println("       Invalid Input! Please Enter Valid Priority Type Only");
                    System.out.println("--------------------------------------------------------------");
                }
            }catch (InputMismatchException e){
                System.out.println("\n------------------------------------------------------------------");
                System.out.println("       Invalid Input! Please Enter String Description Only");
                System.out.println("------------------------------------------------------------------");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("\n--------------------------------------------------------------");
            System.out.println("       Invalid Input! Please Enter String Only");
            System.out.println("--------------------------------------------------------------");
        }
    }

}

