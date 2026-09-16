import java.util.*;
import java.io.*;
public class MemoryInput {
    //first line = memory-block sizes
    //second line = process memory requests
    public static void main(String[] args) throws Exception{
        //test
        readFile("input/memory1.txt", new ArrayList<Integer>(), new ArrayList<Integer>());
    }
    public static boolean readFile(String filename, ArrayList<Integer> request, ArrayList<Integer> storage) throws Exception{
        Scanner in = new Scanner(new File(filename));


        //check if file is empty
        if (!in.hasNextLine()) {
            in.close();
            System.out.println("Error: Empty file.");
            return false;
        }

        //watch for empty Lines
        int line = 1;
        int count = 0;

        while(in.hasNextLine()){
            String input = in.nextLine();
            //if an empty line
            if (input.trim().isEmpty()) {
                continue;
            
            }else {//if not empty
                if(count == 0){
                    if(validateStorage(input.split("\\s+"), storage, line)){
                        count++;
                    }else{
                        in.close();
                        return false;
                    } 
                }else if(count == 1){
                    if(validateRequest(input.split("\\s+"), request, line)){
                        count++;
                    }else{
                        in.close();
                        return false;
                    }
                }else{
                    System.out.println("Error: invalid input on line " + line + ".");
                    in.close();
                    return false;
                }
            }
            line++;
            
        }//end while

        //if only 1 line exist
        if (count <= 1) {
            in.close();
            System.out.println("Error: only 1 line exits, invalid input");
            return false;
        }


        in.close();
        return true;
    }

    public static boolean validateStorage(String[] current, ArrayList<Integer> storage, int line){
        for(int i = 0; i < current.length; i++){
            try{
                int value = Integer.parseInt(current[i]);
                if(value <= 0){
                    storage.clear();
                    System.out.println("Error: non-positive block size for storing at position " + i + ".");
                    return false;
                }
                storage.add(value);
            }catch(Exception e){
                System.out.println("Error: incorrect type input on line " + line + ".");
                storage.clear();
                return false;
            }
        }
        return true;
    }

    public static boolean validateRequest(String[] current, ArrayList<Integer> request, int line){
        for(int i = 0; i < current.length; i++){
            try{
                int value = Integer.parseInt(current[i]);
                if(value <= 0){
                    request.clear();
                    System.out.println("Error: non-positive memory request at position " + i + ".");
                    return false;
                }
                request.add(value);
            }catch(Exception e){
                request.clear();
                System.out.println("Error: incorrect type input on line "+ line + ".");
                return false;
            }
        }
        return true;
    }

}
