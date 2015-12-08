import java.util.Scanner;
import java.io.*;

public class Framework {
    
    private Model model;
    private double currentTime;
    private double untilTime;

    public Framework(Model model) {
        this.model = model;
        currentTime = 0;
        untilTime = model.timeAdvance();
    }
    
    public void run(String[][] inputs, double[] times) {
        untilTime = model.timeAdvance();
         for (int i = 0; i < inputs.length; i++) {
             if (inputs.length == times.length) {
                String[] input = inputs[i];
                double waitTime = times[i];
                if (model.validInput(input)) {
                    if (waitTime >= currentTime) {
                        while (waitTime >= currentTime) {
                            //System.out.println(untilTime + " " + waitTime);
                            if (untilTime < waitTime && untilTime > 0)  {
                                //try {
                                //Thread.currentThread().sleep((long)((untilTime - currentTime)*1000));
                                    currentTime = untilTime;
                                    String output = model.output();
                                    if (output != null) 
                                        System.out.println(currentTime + " - Output: " + output);
                                    model.internalTransition();
                                    untilTime = model.timeAdvance();
                                    //} catch (InterruptedException e) {}
                            } else if (waitTime < untilTime || untilTime < 0) {
                                //try {
                                //Thread.currentThread().sleep((long)((waitTime - currentTime)*1000));
                                    currentTime = waitTime;
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
                                    //} catch (InterruptedException e) {}
                            } else { // They must be equal!
                                //try {
                                //Thread.currentThread().sleep((long)((waitTime - currentTime)*1000));
                                    currentTime = waitTime;
                                    String outString = currentTime + "";
                                    String output = model.output();
                                    if (output != null) 
                                        outString = outString + " - Output: " + output;
                                    outString = outString + " - Input: ";
                                    for (int j = 0; j < input.length; j++) {
                                        outString = outString + input[j] + " ";
                                    }
                                    System.out.println(outString);
                                    model.confluentTransition(input, currentTime);
                                    waitTime = -1;
                                    untilTime = model.timeAdvance();
                                    //} catch (InterruptedException e) {}
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

    public String[] getInput(double waitTime) { // After spending 6 hours on handling this via multithreading, I found this on google.
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        double startTime = System.currentTimeMillis(), elapsed = 0;
        System.out.println("Enter input:");
        try {
            if (waitTime > 0) {
                while ((System.currentTimeMillis() - startTime) < waitTime * 1000 && !in.ready()) {}
            } else {
                while (!in.ready()) {}
            }
            if (in.ready()) {
                elapsed = System.currentTimeMillis() - startTime;
                String[] output = {in.readLine().trim(), elapsed/1000 + ""};
                return output;
            } else {
                String[] output = {"", "No input"};
                return output;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    public void run() {
        double startTime = System.currentTimeMillis(), currentTime = 0;
        while (true) {
            double until = model.timeAdvance();
            String[] inputTry = getInput(until);
            if (inputTry != null) {
                String[] input = inputTry[0].split(",");
                for (int i = 0; i < input.length; i++) {
                    input[i] = input[i].trim();
                }
                if (inputTry[1].equals("No input")) {
                    if (until != 0)
                        currentTime = System.currentTimeMillis() - startTime;
                    String currentTimeString = currentTime/1000 + " - ";
                    String output = model.output();
                    if (output != null) {
                        if (output.length() > 0)
                            System.out.println(currentTimeString + model.output());
                    }
                    model.internalTransition();
                } else if (model.validInput(input)) {
                    if (Double.parseDouble(inputTry[1]) >= until) { // input was given a moment before time ran out, we count this as the same time.
                        currentTime = System.currentTimeMillis() - startTime;
                        String currentTimeString = currentTime/1000 + " - ";
                        String output = model.output();
                        if (output.length() > 0)
                            System.out.println(currentTimeString + model.output());
                        model.confluentTransition(input, currentTime);
                    } else {
                        model.externalTransition(input, currentTime);
                    }
                } else {
                    System.out.println("Input invalid -- try again");
                }            
            } else {
                System.out.println("Fatal error."); // I don't know what would ever cause this to happen, but just in case I'll put it in here.
                System.exit(0);
            }
        }
    }

    public boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
