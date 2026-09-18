
import java.util.*;

public class FIFO {

    public static void run(ArrayList<Integer> values){
        System.out.println("--- FIFO Page Replacement ---");
        final int max = PagingInput.size;//max size of storage

        int[] frames = new int[max];//array of frames of size max with all = 0


        int hit = 0;
        int fault = 0;
        int current = 0;
        int size = values.size();

        boolean have = false;
        System.out.println("Frames: "+ max);

        System.out.printf("\n%-10s %-5s %14s", "Page", "Frames", "Result");
        while(!values.isEmpty()){//until values is empty
            have = false;
            if (current == max) current = 0;//reset current to 0

            for(int i = 0 ; i < max; i++){
                if(frames[i] == values.get(0)){//hit
                    printFrames(frames, "Hit", values.get(0));
                    values.remove(0);
                    hit++;
                    have = true;
                    break;
                }else if(frames[i] == 0){//empty frame mean add
                    frames[i] = values.get(0);
                    printFrames(frames, "Fault", values.get(0));
                    values.remove(0);
                    fault++;
                    have = true;
                    break;
                }
            }
            if(!have){//if not hit in non empty
                frames[current] = values.get(0);
                printFrames(frames, "Fault", values.get(0));
                values.remove(0);
                fault++;
                current++;
            }
            

        }//while

        System.out.println("\n\nTotal Reference: " + size);
        System.out.println("Hits: " + hit);
        System.out.println("Faults: " + fault);
        System.out.printf("Hit Rate: %.2f" , (double)hit/size);
        System.out.printf("\nMiss Rate: %.2f\n" ,(double)fault/size);
    }//run  

    public static void printFrames(int[] frames, String result, int current){


        System.out.printf("\n%-10d %-5s %10s", current, Arrays.toString(frames), result);
    }
}
