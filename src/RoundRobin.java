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
                addGanatt(ready, p.pid, time, time+p.burstTime);
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
                addGanatt(ready, p.pid, time, time+quantum);
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

        printGanatt(ready);

        
    }

    public static void printGanatt(ArrayList<GanattStruct> ganatt){
        //fill gaps
        if(ganatt.get(0).startTime !=0){
            addGanattat(ganatt, "IDLE", 0, ganatt.get(0).startTime,0);
        }

        for(int i = ganatt.size()-1; i > 0; i--){
            if(ganatt.get(i).startTime != ganatt.get(i-1).endTime){
                addGanattat(ganatt, "IDLE", ganatt.get(i-1).endTime, ganatt.get(i).startTime, i);
            }
        }//done gaps

        //print start

        int size = 0;
        int count = 0;
        while(size < ganatt.size()){
            count = 0;
            for(int i = size ; i < ganatt.size(); i++){
                if(count == 6){
                    System.out.println("|");
                    break;
                }
                System.out.printf("| %-6s",ganatt.get(i).pid);

                if(i == ganatt.size()-1){//end of line
                    System.out.println("|");
                }
                count++;

            }

            count = 0;
            for(int j = size;  j < ganatt.size(); j++){
                if(count == 6){
                    System.out.println(ganatt.get(j-1).endTime+"\n");
                    break;
                }
                System.out.printf("%-8d",ganatt.get(j).startTime);

                if(j == ganatt.size()-1){//end of line
                    System.out.println(ganatt.get(j).endTime);
                }
                count++;
            }

            size += count;


        }//while



    }
    public static void addGanatt(ArrayList<GanattStruct> ganatt, String pid, int startTime, int endTime){
        GanattStruct g = new GanattStruct(pid, startTime, endTime);
        ganatt.add(g);
    }

    public static void addGanattat(ArrayList<GanattStruct> ganatt, String pid, int startTime, int endTime, int index){
        GanattStruct g = new GanattStruct(pid, startTime, endTime);
        ganatt.add(index, g);
    }


}

