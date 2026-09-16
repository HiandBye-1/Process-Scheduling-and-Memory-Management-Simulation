import java.util.*;

public class FCFS {
    public static void main(String[] args)throws Exception{
        //test
    Set<String> set = new HashSet<>();
    List<Processes> processes = new ArrayList<>();
    ProcessInput.readFile("input/processes1.txt", set, processes);

    run(processes);
    }
    public static void run(List<Processes> processes){
        Set<Processes> seen = new HashSet<>();
        double CPUbusy = 0;
        double TAT = 0;
        double WT = 0;
        double CPUutilize = 0;
        //to add for printing
        ArrayList<GanattStruct> ganatt = new ArrayList<>();
        ArrayList<Processes> modified = new ArrayList<>();
        int time = 0;

        boolean added = false;
        while(seen.size() != processes.size()){
            added = false;
            for(Processes p : processes){
                //add to queue if arrival time met
                if(p.arrivalTime <= time && !seen.contains(p)){
                    p.completionTime = time + p.originalBurstTime;
                    p.turnaroundTime = p.completionTime - p.arrivalTime;
                    p.waitingTime = p.turnaroundTime - p.originalBurstTime;
                    CPUbusy += p.burstTime;
                    TAT += p.turnaroundTime;
                    WT += p.waitingTime;
                    seen.add(p);
                    modified.add(p);
                    ganatt.add(new GanattStruct(p.pid, time, p.burstTime + time));
                    time += p.burstTime;
                    added = true;
                    //end for adding to ganatt


                }
            }
            //if queue is empty, move to next time
            if (added == false){
                time++;
            }

        }

        CPUutilize = (CPUbusy / time)*100;
        TAT /= processes.size();
        WT /= processes.size();

        printEverything(ganatt, modified, TAT, WT, CPUbusy, CPUutilize);
    }

    public static void printEverything(ArrayList<GanattStruct> ganatt, ArrayList<Processes> modified, double TAT, double WT, double CPUbusy, double CPUutilize){
        System.out.println("--- FCFS Scheduling ---\n");
        System.out.println("Ganatt Chart:");
        ganattChart.printGanatt(ganatt);
        
        System.out.printf("\n%-10s %-5s %-5s %-5s%n", "Process", "CT", "TAT", "WT");

        for(int i = 0; i < modified.size(); i++){
            System.out.printf("%-10s %-5d %-5d %-5d%n",
                                modified.get(i).pid,
                                modified.get(i).completionTime,
                                modified.get(i).turnaroundTime,
                                modified.get(i).waitingTime);
        }
        
        System.out.printf("\nAverage Turnaround Time: %.2f", TAT);
        System.out.printf("\nAverage Waiting Time: %.2f", WT);
        System.out.printf("\nCPU Busy Time: %.2f", CPUbusy);
        System.out.printf("\nCPU Utilization: %.2f%%", CPUutilize); 
        

    }
}
