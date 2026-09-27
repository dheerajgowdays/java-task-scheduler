package com.dheeraj.scheduler.engine;

import com.dheeraj.scheduler.model.Task;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskExecutor {
    private final ExecutorService executor;
    public TaskExecutor(int poolSize){
        if(poolSize <= 0){
            throw new IllegalArgumentException("Pool size must be grater than Zero");
        }
        this.executor = Executors.newFixedThreadPool(poolSize);
    }
    public void submit(Task task){
        Objects.requireNonNull(task,"Task Cannot be null");
        executor.submit(()->execute(task));
    }
    public void execute(Task task) {
        try {
            task.start();
        } catch (IllegalStateException e) {
            System.out.println(
                    "Task Could Not Start: "
                            + task.getName()
            );
            return;
        }
        try {
            System.out.println(
                    "\nExecuting Task: "
                            + task.getName()
                            + " | Thread: "
                            + Thread.currentThread().getName()
            );
            task.getAction().run();
            task.complete();
            System.out.println(
                    "Task Completed: "
                            + task.getName()
            );

        } catch (Exception e) {

            task.fail();
            System.out.println(
                    "Task Failed: "
                            + task.getName()
            );
            e.printStackTrace();
        }
    }
    public void shutdown(){
        executor.shutdown();
        System.out.println("Task executor shutting down ...");
    }
}
