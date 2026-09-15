public class Processes{
    public String pid;
    public int arrivalTime;
    public int burstTime;
    public int priority;
    public int originalBurstTime;
    public int completionTime;
    public int turnaroundTime;
    public int waitingTime;
    public Processes(String pid, int arrivalTime, int burstTime, int priority, 
                     int originalBurstTime, int completionTime, int turnaroundTime,
                     int waitingTime){
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.originalBurstTime = originalBurstTime;
        this.completionTime = completionTime;
        this.turnaroundTime = turnaroundTime;
        this.waitingTime = waitingTime;
    }

}