package com.dheeraj.scheduler.model;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.dheeraj.scheduler.enums.TaskPriority;
import com.dheeraj.scheduler.enums.TaskStatus;

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
    @Test
    void shouldHandleConcurrentStartAndCancel() throws InterruptedException {

        // Create a task
        Task task = createTask();

        // Move task from CREATED → SCHEDULED
        task.schedule(Instant.now().plusSeconds(60));

        assertEquals(TaskStatus.SCHEDULED, task.getStatus());

        // Latch starts with count = 1.
        // Both threads will wait here until countDown() is called.
        CountDownLatch latch = new CountDownLatch(1);

        // These variables record whether each operation succeeded.
        AtomicBoolean startSucceeded = new AtomicBoolean(false);
        AtomicBoolean cancelSucceeded = new AtomicBoolean(false);

        // Thread 1 tries to start the task
        Thread startThread = new Thread(() -> {

            try {

                // Wait until the main test thread releases us
                latch.await();

                // Try:
                // SCHEDULED → RUNNING
                task.start();

                // If we reach here, start() succeeded
                startSucceeded.set(true);

            } catch (IllegalStateException e) {

                // Another thread may have changed the state first.
                System.out.println(
                        "Start failed: " + e.getMessage()
                );

            } catch (InterruptedException e) {

                // Restore the interrupted status
                Thread.currentThread().interrupt();
            }
        });


        // Thread 2 tries to cancel the task
        Thread cancelThread = new Thread(() -> {

            try {

                // Wait until the main test thread releases us
                latch.await();

                // Try:
                // SCHEDULED → CANCELLED
                task.cancel();

                // If we reach here, cancel() succeeded
                cancelSucceeded.set(true);

            } catch (IllegalStateException e) {

                // Another thread may have changed the state first.
                System.out.println(
                        "Cancel failed: " + e.getMessage()
                );

            } catch (InterruptedException e) {

                // Restore the interrupted status
                Thread.currentThread().interrupt();
            }
        });


        // Start both threads
        startThread.start();
        cancelThread.start();


        // Release both threads
        //
        // Count:
        //
        //     1
        //     ↓
        // countDown()
        //     ↓
        //     0
        //
        // Both waiting threads can now continue.
        latch.countDown();


        // Wait until startThread finishes
        startThread.join();

        // Wait until cancelThread finishes
        cancelThread.join();


        // Only one operation should succeed.
        assertEquals(
                1,
                (startSucceeded.get() ? 1 : 0)
                        + (cancelSucceeded.get() ? 1 : 0)
        );

        // The final state should correspond to the successful operation.
        if (startSucceeded.get()) {
            assertEquals(TaskStatus.RUNNING, task.getStatus());
        } else {
            assertEquals(TaskStatus.CANCELLED, task.getStatus());
        }
    }
}
