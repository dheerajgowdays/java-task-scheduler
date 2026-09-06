    package com.dheeraj.scheduler;

    import com.dheeraj.scheduler.repository.TaskRepository;
    import com.dheeraj.scheduler.service.TaskService;
    import com.dheeraj.scheduler.ui.TaskManagementUi;

    import java.util.Scanner;
    public class Main {
        public static void main(String[] args){
            Scanner sc = new Scanner(System.in);
            TaskRepository taskRepository = new TaskRepository();
            TaskService taskService = new TaskService(taskRepository,sc);
            TaskManagementUi taskManagementUi = new TaskManagementUi(sc,taskService);

            while (true){
                System.out.println("\n============================================");
                System.out.println("           TASK SCHEDULER");
                System.out.println("============================================");
                System.out.println("\n TASK MANAGEMENT");
                System.out.println("--------------------");
                System.out.println("1.  Create Task");
                System.out.println("2.  View Task");
                System.out.println("3.  View All Task");
                System.out.println("4.  Update Task");
                System.out.println("5.  Delete Task");
                System.out.println("\n SCHEDULING");
                System.out.println("------------");
                System.out.println("6.  Schedule Task");
                System.out.println("7.  Schedule Task After Delay");
                System.out.println("8.  Schedule Reoccurring Task");
                System.out.println("\n EXECUTION");
                System.out.println("-------------");
                System.out.println("9.  Execute Task");
                System.out.println("10. Cancel Task");
                System.out.println("11. Retry Task");
                System.out.println("\n TASK CONFIGURATION");
                System.out.println("--------------------");
                System.out.println("12. Set Priority");
                System.out.println("13. Add Reminder");
                System.out.println("14. Remove Reminder");
                System.out.println("\n MONITORING");
                System.out.println("------------");
                System.out.println("15. Task History");
                System.out.println("16. Schedule Status");
                System.out.println("\n SYSTEM");
                System.out.println("-------");
                System.out.println("17. Shutdown Scheduler");
                System.out.println("18. Exit ");

                System.out.print("\n Enter Your Choice: ");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice){
                    case 1:
                        //Create Task
                        taskManagementUi.createTask(()-> System.out.println("Executing background job for this task!"));
                        break;
                    case 2:
                        //View Task
                        break;
                    case 3:
                        //View All Task
                        break;
                    case 4:
                        //Update Task
                        break;
                    case 5:
                        //Delete Task
                        break;
                    case 6:
                        //Schedule Task
                        break;
                    case 7:
                        //Schedule Task After Delay
                        break;
                    case 8:
                        //Schedule Reoccurring Task
                        break;
                    case 9:
                        //Execute Task
                        break;
                    case 10:
                        //Cancel Task
                        break;
                    case 11:
                        //Retry Task
                        break;
                    case 12:
                        //Set Priority
                        break;
                    case 13:
                        //Add Reminder
                        break;
                    case 14:
                        //Remove Reminder
                        break;
                    case 15:
                        //Task History
                        break;
                    case 16:
                        //Schedule Status
                        break;
                    case 17:
                        //Shutdown Scheduler
                        break;
                    case 18:
                        //Exit
                        System.out.println("  THANK YOU");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("\n=============================");
                        System.out.println("   Enter A Valid Choice !");
                        System.out.println("=============================\n");
                }
            }
        }
    }
