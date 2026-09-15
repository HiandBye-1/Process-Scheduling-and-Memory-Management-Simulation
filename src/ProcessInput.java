import java.util.*;
import java.io.*;

public class ProcessInput {
    //print nothing if no error

    public static boolean readFile(String filename, Set<String> set, List<Processes> processes) throws Exception {
        Scanner in = new Scanner(new File(filename));

        int line = 1;
        String input = "";

        if (!in.hasNextLine()) {
            in.close();
            System.out.println("Error: Empty file.");
            return false;
        }

        boolean found = false;

        while (in.hasNextLine()) {
            input = in.nextLine();

            if (input.trim().isEmpty()) {
                line++;
            } else {
                found = true;
                break;
            }
        }

        if (!found) {
            in.close();
            System.out.println("Error: Empty file.");
            return false;
        }

        String[] current = input.split("\\s+");

        //check for header in first line
        if (checkHeader(current)) {
            if (in.hasNextLine()) {
                line++;
            } else {//if empty
                in.close();
                System.out.println("Error: No processes found.");
                return false;
            }
        } else {
            in.close();
            in = new Scanner(new File(filename));

            for (int i = 1; i < line; i++) {
                in.nextLine();
            }
        }

        boolean foundProcesses = false;
        while (in.hasNextLine()) {
            input = in.nextLine();
            //skip empty lines
            if (input.trim().isEmpty()) {
                line++;
                if(!in.hasNextLine() && !foundProcesses) {
                    System.out.println("Error: No processes found.");
                    in.close();
                    return false;
                }
                continue;
            }


            current = input.split("\\s+");
            
            if (!validateProcess(current, line, set, processes)) {
                in.close();
                processes.clear();
                return false;
            }
            foundProcesses = true;

            line++;
        }

        in.close();
        return true;
    }

    public static boolean checkHeader(String[] current) {
        if (current.length != 4) {
            return false;
        }

        return current[0].equalsIgnoreCase("pid")
                && current[1].equalsIgnoreCase("arrival_time")
                && current[2].equalsIgnoreCase("burst_time")
                && current[3].equalsIgnoreCase("priority");
    }

    public static boolean validateProcess(String[] current, int line, Set<String> set, List<Processes> processes) {
        if (!checkIndex(current.length, line)) {
            return false;
        }

        if (!checkPID(current[0], line, set)) {
            return false;
        }

        try {
            int arrivalTime = Integer.parseInt(current[1]);

            if (arrivalTime < 0) {
                System.out.println("Error: negative arrival time on line " + line + ".");
                return false;
            }
        } catch (Exception e) {
            printError();
            return false;
        }

        try {
            int burstTime = Integer.parseInt(current[2]);

            if (burstTime <= 0) {
                System.out.println("Error: non-positive burst time on line " + line + ".");
                return false;
            }
        } catch (Exception e) {
            printError();
            return false;
        }

        try {
            int priority = Integer.parseInt(current[3]);
        } catch (Exception e) {
            printError();
            return false;
        }

        Processes p = new Processes(current[0], Integer.parseInt(current[1]), 
                                    Integer.parseInt(current[2]), Integer.parseInt(current[3]), 
                                    Integer.parseInt(current[2]), 0, 0, 0);
        processes.add(p);
        return true;
    }


    public static boolean checkPID(String pid, int line, Set<String> set) {

        if (pid.length() < 2|| !(pid.charAt(0) == 'P') ) {
            printError();
            return false;
        }

        try {
            int value = Integer.parseInt(pid.substring(1));

            if (set.contains(pid)) {
                System.out.println("Error: Duplicate PID found on line " + line + ".");
                return false;
            }

            set.add(pid);

        } catch (Exception e) {
            printError();
            return false;
        }
        
        return true;
    }


    public static boolean checkIndex(int index, int line) {
        if (index <= 3 ) {
            System.out.println("Error: File is missing field on line " + line + ".");
            return false;
        }else if(index > 4) {
            System.out.println("Error: File has too many fields on line " + line + ".");
            return false;
        }

        return true;
    }

    public static void printError() {
        System.out.println("Error: incorrect type input");
    }
}