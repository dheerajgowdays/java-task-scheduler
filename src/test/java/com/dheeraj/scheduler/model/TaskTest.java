package com.dheeraj.scheduler.model;

import com.dheeraj.scheduler.enums.TaskPriority;
import com.dheeraj.scheduler.enums.TaskStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskTest {
    private Task  createTask(){
        return new Task(UUID.randomUUID(),"task1","task",()->System.out.println("task"), TaskPriority.HIGH);
    }
    @Test
    void shouldCreateTask(){
        Task task = createTask();
        assertEquals(TaskStatus.CREATED,task.getStatus());
        task.schedule(Instant.now().plusSeconds(10000));
        assertEquals(TaskStatus.SCHEDULED,task.getStatus());
        assertEquals("task1",task.getName());
    }
    @Test
    void shouldCreateStartComplete(){
        Task task = createTask();
        task.schedule(Instant.now().plusSeconds(10000));
        assertEquals(TaskStatus.SCHEDULED,task.getStatus());
        task.start();
        assertEquals(TaskStatus.RUNNING,task.getStatus());
        task.complete();
        assertEquals(TaskStatus.COMPLETED,task.getStatus());
    }
    @Test
    void shouldCreateCancel(){
        Task task = createTask();
        assertEquals(TaskStatus.CREATED,task.getStatus());
        task.cancel();
        assertEquals(TaskStatus.CANCELLED,task.getStatus());
    }
    @Test
    void shouldCompleteStart(){
        Task task = createTask();
        task.schedule(Instant.now().plusSeconds(10000));
        task.start();
        task.complete();
        assertThrows(IllegalStateException.class, task::start);
    }
    @Test
    void shouldSchedulePast(){
        Task task =createTask();
        assertThrows(IllegalArgumentException.class,()->task.schedule(Instant.parse("2026-06-08T14:30:00Z")));
    }
    @Test
    void shouldScheduleNull(){
        Task task =createTask();
        assertThrows(IllegalArgumentException.class,()->task.schedule(null));
    }
}
