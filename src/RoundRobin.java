import java.util.*;

public class RoundRobin {
    //pid, arrivalTime, burstTime
    public static void run(List<Processes> processes, int quantum) {
        int time = 0;
        ArrayList<GanattStruct> ready = new ArrayList<>();
        List<Processes> completed = new ArrayList<>();
        Queue<Processes> queue = new ArrayDeque<>();
        Set<Processes> seen= new HashSet<>();
        double averageTAT = 0;
        double averageWT = 0;
        double CPUbusy = 0;
        double CPUutilize = 0;
        
        while(completed.size() != processes.size()){
            for(Processes p : processes){
                //add to queue if arrival time met
                if(p.arrivalTime <= time &&  !seen.contains(p)){//
                    queue.add(p);
                    seen.add(p);
                }
            }

            //if queue is empty, move to next time
            if(queue.isEmpty()){
                time++;
                continue;
            }

            //if not empty
            Processes p = queue.poll();
            if(p.burstTime <= quantum){//if complete
                completed.add(p);
                ganattChart.addGanatt(ready, p.pid, time, time+p.burstTime);
                time += p.burstTime;
                CPUbusy+=p.burstTime; 
                p.completionTime = time;
                p.turnaroundTime = p.completionTime - p.arrivalTime;
                p.waitingTime = p.turnaroundTime - p.originalBurstTime;
                p.burstTime = 0;
                averageTAT += p.turnaroundTime;
                averageWT += p.waitingTime;
            }else{//if not complete
                p.burstTime -= quantum;
                ganattChart.addGanatt(ready, p.pid, time, time+quantum);
                time += quantum;
                CPUbusy+=quantum;
                //check if there are other proces coming
                for(Processes k: processes){//if new process coming
                    if(k.arrivalTime <= time && !seen.contains(k)){
                        queue.add(k);
                        seen.add(k);
                    }
                }
                queue.add(p);
            }

            
            
        }//while

        averageTAT /= processes.size();
        averageWT /= processes.size();
        CPUutilize = (CPUbusy/time)*100;

        System.out.println("--- Round Robin Scheduling ---\n");
        System.out.println("Quantum: " + quantum);
        System.out.println("\nGrantt Chart:");
        ganattChart.printGanatt(ready);
        System.out.printf("\n%-10s %-5s %-5s %-5s%n", "Process", "CT", "TAT", "WT");
        for(Processes p : completed){
            System.out.printf("%-10s %-5d %-5d %-5d%n", p.pid, p.completionTime, p.turnaroundTime, p.waitingTime);
        }
        System.out.printf("\nAverage Turnaround Time: %.2f", averageTAT);
        System.out.printf("\nAverage Waiting Time: %.2f", averageWT);
        System.out.printf("\nCPU Busy Time: %.2f", CPUbusy);
        System.out.printf("\nCPU Utilization: %.2f%%", CPUutilize);
        
    }



}

