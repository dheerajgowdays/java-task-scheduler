package com.dheeraj.scheduler.model;

import com.dheeraj.scheduler.enums.TaskPriority;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class TaskTest {
    @Test
    void shouldCreateTask(){
        Task task = new Task(UUID.randomUUID(),"task1","task",()->System.out.println("task"), TaskPriority.HIGH,026-09-08T14:30:00Z);

    }
}
