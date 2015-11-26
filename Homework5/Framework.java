import java.util.Scanner;
import java.io.*;

public class Framework {
    
    private Model model;

    public Framework(Model model) {
        this.model = model;
    }
    
    public void run(String[][] inputs, double[] times) {
         double untilTime = model.timeAdvance(), currentTime = 0;
         int uptick = 0;
         for (int i = 0; i < inputs.length; i++) {
             if (inputs.length == times.length) {
                String[] input = inputs[i];
                double waitTime = times[i];
                if (model.validInput(input)) {
                    if (waitTime > 0) {
                        while (waitTime > 0) {
                            //System.out.println(untilTime + " " + waitTime);
                            if (untilTime < waitTime && untilTime > 0)  {
                                try {
                                    Thread.currentThread().sleep((long)((untilTime - currentTime)*1000));
                                    currentTime = untilTime;
                                    //if (untilTime == 0) {
                                    //    uptick++;
                                    //} else {
                                    //    uptick = 0;
                                    //}
                                    //String currentTimeString = "(" + currentTime + "," + uptick + ") ";                 
                                    //System.out.println(currentTimeString + model.output());
                                    //System.out.println("Internal Transition");
                                    model.internalTransition();
                                    untilTime = model.timeAdvance();
                                } catch (InterruptedException e) {}
                            } else if (waitTime < untilTime || untilTime < 0) {
                                try {
                                    Thread.currentThread().sleep((long)((waitTime - currentTime)*1000));
                                    currentTime = waitTime;
                                    // uptick = 0;
                                    String outString = currentTime + " - Input: ";
                                    for (int j = 0; j < input.length; j++) {
                                        outString = outString + input[j] + " ";
                                    }
                                    System.out.println(outString);
                                    model.externalTransition(input, currentTime);
                                    waitTime = -1;
                                    if (untilTime < 0) {
                                        untilTime = model.timeAdvance();
                                    }
                                } catch (InterruptedException e) {}
                            } else { // They must be equal!
                                try {
                                    Thread.currentThread().sleep((long)((waitTime - currentTime)*1000));
                                    currentTime = waitTime;
                                    // uptick = 0;
                                    String outString = currentTime + " - Input: ";
                                    for (int j = 0; j < input.length; j++) {
                                        outString = outString + input[j] + " ";
                                    }
                                    System.out.println(outString);
                                    model.confluentTransition(input, currentTime);
                                    waitTime = -1;
                                    untilTime = model.timeAdvance();
                                } catch (InterruptedException e) {}
                            }
                        }
                    } else {
                        System.out.println("Input is invalid");
                    }
                } else {
                    System.out.println("Input is invalid");
                }
            } else {
                System.out.println("Input is invalid");
            }
         }
    }

    // public String[] getInput(double waitTime) { // After spending 6 hours on handling this via multithreading, I found this on google.
    //     BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    //     double startTime = System.currentTimeMillis(), elapsed = 0;
    //     System.out.println("Enter input:");
    //     try {
    //         while ((System.currentTimeMillis() - startTime) < waitTime * 1000
    //                && !in.ready()) {
    //         }
    //         if (in.ready()) {
    //             elapsed = System.currentTimeMillis() - startTime;
    //             String[] output = {in.readLine().trim(), elapsed/1000 + ""};
    //             return output;
    //         } else {
    //             String[] output = {"", "No input"};
    //             return output;
    //         }
    //     } catch (IOException e) {
    //         System.out.println(e);
    //     }
    //     return null;
    // }

    // public void run() {
    //     double startTime = System.currentTimeMillis(), currentTime = 0;
    //     while (true) {
    //         double until = model.timeAdvance();
    //         int uptick = 0;
    //         String[] inputTry = getInput(until);
    //         if (inputTry != null) {
    //                 if (inputTry[1].equals("No input")) {
    //                     if (until == 0) {
    //                         uptick++;                            
    //                     } else {
    //                         uptick = 0;
    //                         currentTime = System.currentTimeMillis() - startTime;
    //                     }                                        
    //                     String currentTimeString = "(" + currentTime/1000 + "," + uptick + ") ";
    //                     System.out.println(currentTimeString + model.output());
    //                     model.internalTransition();
    //                 } else if (model.validInput(inputTry[0])) {
    //                     if (Double.parseDouble(inputTry[1]) >= until) { // input was given a moment before time ran out, we count this as the same time.
    //                         currentTime = System.currentTimeMillis() - startTime;
    //                         System.out.println("(" + currentTime/1000 + "," + uptick + ") " + model.output());
    //                         model.confluentTransition(inputTry[0]);
    //                     } else {
    //                         model.externalTransition(inputTry[0]);
    //                     }
    //                 } else {
    //                     System.out.println("Input invalid -- try again");
    //                 }            
    //         } else {
    //             System.out.println("Fatal error."); // I don't know what would ever cause this to happen, but just in case I'll put it in here.
    //             System.exit(0);
    //         }
    //     }
    // }

    public boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}