import java.util.Scanner;

public class Framework {
    private AtomicModel model;
    private int count = 0;
    public Framework(AtomicModel m) {
        model = m;
    }

    public void tick(String[] input) {
        //System.out.println("Tick " + ++count + ": ");
        System.out.println(model.output());
        model.stateTransition(input);
    }

    public void run(Scanner sc) {

        // System.out.println("Enter a string for the initial state.");
        // String state = sc.nextLine();
        // while(!model.initializeState(state)) {
        //     System.out.println("Initialization failed. Try again:");
        //     state = sc.nextLine();
        // }
        System.out.println("Enter input for your model:");
        String[] input = sc.nextLine().split(" ");
        while (true) {
            if (input.length == model.getNumberOfInputs()) {
                tick(input);            
            } else {
                System.out.println("-- Input invalid --");
            }
            System.out.println("Enter input for your model:");
            input = sc.nextLine().split(" ");
        }
    }

    public void run(String[][] inputList) {

        // System.out.println("Enter a string for the initial state.");
        // String state = sc.nextLine();
        // while(!model.initializeState(state)) {
        //     System.out.println("Initialization failed. Try again:");
        //     state = sc.nextLine();
        // }
        //System.out.println("Enter input for your model:");
        for (int i = 0; i < inputList.length; i++) {
            if (inputList[i].length == model.getNumberOfInputs()) {
                String[] input = inputList[i];
                tick(input);
            }
        }
    }
}
