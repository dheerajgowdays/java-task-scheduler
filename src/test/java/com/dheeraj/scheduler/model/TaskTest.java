package com.dheeraj.scheduler.model;

import com.dheeraj.scheduler.enums.TaskPriority;
import com.dheeraj.scheduler.enums.TaskStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    @Test
    void shouldCreateTask(){
        Task task = new Task(UUID.randomUUID(),"task1","task",()->System.out.println("task"), TaskPriority.HIGH);
        t
        assertEquals(TaskStatus.CREATED,task.getStatus());
        assertEquals("task1",task.getName());
    }
}
