package com.dheeraj.scheduler.ui;

import com.dheeraj.scheduler.service.SchedulingService;

import java.time.Instant;
import java.util.Scanner;
import java.util.UUID;

public class SchedulingUi {
    Scanner sc;
    SchedulingService schedulingService;
    public SchedulingUi(Scanner sc,SchedulingService schedulingService){
        this.sc = sc;
        this.schedulingService = schedulingService;
    }
    public UUID readId(){
        while(true){
            System.out.println("Enter Task Id: ");
            try {
                return UUID.fromString(sc.nextLine().trim());
            }catch(IllegalArgumentException e){
                System.out.println("\n--------------------------------------------------------------");
                System.out.println("Id Cannot be Empty. Please Enter a Valid Id".indent(10));
                System.out.println("\n--------------------------------------------------------------");
            }
        }
    }
    public Instant readScheduleAt(){
        while(true){
            System.out.println("Enter the Schedule Time: ");
            try{
                return Instant.parse(sc.nextLine().trim());
            }catch(IllegalArgumentException e){
                System.out.println("\n--------------------------------------------------------------");
                System.out.println("Id Cannot be Empty. Please Enter a Valid Trim".indent(10));
                System.out.println("\n--------------------------------------------------------------");

            }
        }
    }
    public void schedule(){
        UUID id = readId();
        Instant scheduleAt = readScheduleAt();
        schedulingService.scheduleTaskAt(id,scheduleAt);
    }
}
