
import java.util.*;
import java.io.*;
public class PagingInput {
    public static int size;

    public static boolean readFile(String filename,  ArrayList<Integer> values) throws Exception{
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
                    if(validateSize(input.split("\\s+"), line)){
                        count++;
                    }else{
                        in.close();
                        return false;
                    } 
                }else if(count == 1){
                    if(validatePages(input.split("\\s+"), values, line)){
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
            System.out.println("Error: only 1 line exists, invalid input");
            return false;
        }

        in.close();
        return true;
    }

    public static boolean validateSize(String[] current, int line){
        if(current.length != 1){//check if only 1 input
            System.out.println("Error: invalid input on line " + line + ".");
            return false;
        }

        //if 1 ipnut 
        try{
            int value = Integer.parseInt(current[0]);
            if(value <= 0){
                System.out.println("Error: non-positive size detected.");
                return false;
            }else size = value;
            
        }catch(Exception e){
            System.out.println("Error: incorrect type input on line " + line + ".");
            return false;
        }
        
        return true;
    }

    public static boolean validatePages(String[] current, ArrayList<Integer> values, int line){
        for(int i = 0; i < current.length; i++){
            try{
                int value = Integer.parseInt(current[i]);
                if(value <= 0){
                    values.clear();
                    System.out.println("Error: non-positive page at position " + i + ".");
                    return false;
                }
                values.add(value);
            }catch(Exception e){
                values.clear();
                System.out.println("Error: incorrect type input on line "+ line + ".");
                return false;
            }
        }
        return true;
    }

}

