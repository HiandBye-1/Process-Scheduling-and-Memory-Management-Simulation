import java.util.ArrayList;


public class BestFit {

    public static void run(ArrayList<Integer> storage, ArrayList<Integer> request) {
        System.out.println("--- Best Fit Memory Management ---");
        System.out.println("Requests   |  Storage" );

        
        for(int current = 0; current < request.size(); current++){//for every request
            int best = -1;
            for(int processing = 0; processing < storage.size(); processing++){//for every block in storage
                if (storage.get(processing) >= request.get(current) && best == -1){//if the block can be allocated
                    best = processing;
                }else if(storage.get(processing) >= request.get(current) && storage.get(processing) < storage.get(best)){//if this block is better
                    best = processing;
                }else if(processing == storage.size()-1 && storage.get(processing) < request.get(current) && best == -1 ){//if the block cannot be allocated
                    System.out.println(request.get(current) + "        |  UNALLOCATED");
                }
            }
            if(best != -1){//if the block can be allocated
                storage.set(best, storage.get(best) - request.get(current));//allocate the block
                System.out.println(request.get(current) + "        |  Block " + (best));
            }
            
        }

        System.out.println("\nRemaining Memory Blocks: ");
        for(int i = 0 ; i < storage.size(); i++){//for every block in storage
            System.out.println("Block " + i + ": " + storage.get(i));
        }

    }
    
}
