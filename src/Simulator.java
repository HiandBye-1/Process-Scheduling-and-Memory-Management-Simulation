import java.util.*;
import java.io.*;

public class Simulator {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int choice = 0;


        clearScreen();
        System.out.println("--- CPU Scheduling Simulator ---");
        System.out.println("1. Round Robin");
        System.out.println("2. FCFS");
        System.out.println("3. Exit");


        try{
            System.out.print("Enter choice: ");
            choice = in.nextInt();
        }catch(Exception e){
            System.out.println("Error: Please enter a number between 1 and 3.");
            return;
        }


        Set<String> set = new HashSet<>();
        List<Processes> processes = new ArrayList<>();
        int quantum = 0;
        switch(choice){
            //ROUND ROBIN
            case 1:
                System.out.print("Enter Quantum: ");
                try{//check quantum number
                    quantum = in.nextInt();
                }catch(Exception e){
                    System.out.println("Error: Please enter a number for Quantum.");
                    return;
                }

                if(quantum <= 0){
                    System.out.println("Error: Quantum must be greater than 0.");
                    return;
                }

                    
                System.out.print("Enter file name: ");
                String filename = in.next();
                
                try{//check file valid
                    if(ProcessInput.readFile("input/"+filename, set, processes)){//if file is valid
                        RoundRobin.run(processes, quantum);
                    }else{
                        return;
                    }
                }catch(Exception e){
                    System.out.println("Error: Invalid file.");
                    return;
                }
                    break;
                    
            //FCFS
            case 2:
                /// FCFS
                break;
            case 3:
                System.out.println("Goodbye!");
                break;
        }



        in.close();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


}