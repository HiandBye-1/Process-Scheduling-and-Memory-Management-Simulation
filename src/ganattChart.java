import java.util.ArrayList;

public class ganattChart {
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
