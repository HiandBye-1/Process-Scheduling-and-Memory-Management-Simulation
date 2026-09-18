import java.util.*;


public class Simulator {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int choice = 0;




        boolean running = true;
        while(running){//main loop

            clearScreen();
            System.out.println("--- CPU Scheduling Simulator ---");
            System.out.println("1. Round Robin");
            System.out.println("2. FCFS(CPU Scheduling)");
            System.out.println("3. Best Fit Memory Management");
            System.out.println("4. FIFO(Page Replacement");
            System.out.println("5. Exit");
        
            try{// if not a number
                System.out.print("Enter choice: ");
                choice = in.nextInt();
            }catch(Exception e){
                System.out.println("Error: Please enter a number between 1 and 4.");
                return;
            }


            Set<String> set = new HashSet<>();
            List<Processes> processes = new ArrayList<>();
            ArrayList<Integer> storage = new ArrayList<>();
            ArrayList<Integer> request = new ArrayList<>();
            int quantum = 0;

            String filename = "";
            
            if(choice < 5 && choice > 0){//if choice is valid
                System.out.print("Enter file name: ");
                filename = in.next();
            }


            switch(choice){
                //ROUND ROBIN
                case 1:
                    System.out.print("Enter Quantum: ");
                    try{//check quantum number
                        quantum = in.nextInt();
                    }catch(Exception e){
                        System.out.println("Error: Please enter a number for Quantum.");
                        continue;
                    }

                    if(quantum <= 0){
                        System.out.println("Error: Quantum must be greater than 0.");
                        return;
                    }

                    
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
                    try{//check file valid
                        if(ProcessInput.readFile("input/"+filename, set, processes)){//if file is valid
                            FCFS.run(processes);
                        }else{
                            return;
                    }
                    }catch(Exception e){
                        System.out.println("Error: Invalid file.");
                        return;
                    }
                    break;

                //Memory allocation
                case 3:
                    try{
                        if(MemoryInput.readFile("input/"+filename, request, storage)){//if file is valid
                            //BestFit.run(request, storage);
                        }else{
                            return;
                        }
                    }catch(Exception e){
                        System.out.println("Error: Invalid file.");
                        return;
                    }
                    break;

                case 4:
                    //FIFO
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Error: Please enter a number between 1 and 4.");
                    break;
            }
        }



        in.close();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


}