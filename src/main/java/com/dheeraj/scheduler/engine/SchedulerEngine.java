package com.dheeraj.scheduler.engine;

import com.dheeraj.scheduler.model.Task;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SchedulerEngine {
    private final ScheduledExecutorService executor;

    public SchedulerEngine(int poolSize){
        if(poolSize <=0 ){
            throw new IllegalArgumentException("Pool size must be greater than zero");
        }
        this.executor  = Executors.newScheduledThreadPool(poolSize);
    }
    public void schedule(Task task){
        Objects.requireNonNull(task,"Task cannot be null");
        Instant scheduledAt = task.getScheduledAt();
        long delayMillis = Duration.between(Instant.now(),scheduledAt).toMillis();
        delayMillis = Math.max(0,delayMillis);
        executor.schedule(()->execute(task),delayMillis, TimeUnit.MILLISECONDS);
    }
    public void execute(Task task){
        try {
            task.start();
        }catch(IllegalArgumentException e){
            return;
        }
        try{
            System.out.println("\nExecuting Task: "+task.getName());
            task.getAction().run();
            task.complete();
            System.out.println("Task Completed: "+task.getName());
        } catch (Exception e) {
            task.fail();
            System.out.println("Task Failed: "+task.getName());
            e.printStackTrace();
        }
    }
    public void cancel(Task task){
        try{
            task.cancel();
        }catch(IllegalArgumentException e){
            return;
        }
        try{
            System.out.println("\n Canceled Task: "+task.getName());
            task.getAction().
        }
    }
    public void shutdown(){
        executor.shutdown();
        System.out.println("Scheduler engine shutting down... ");
    }
}
