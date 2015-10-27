import java.util.Scanner;

public class MyFramework {
    public void run() {
        AtomicModel v = new VendingMachine();
        Scanner sc = new Scanner(System.in);
        System.out.println("To help make it easier for the grader to see what's going on you may enter the string \"state\" to see the state of the vending machine.\n Were this to not be just for an assignment, but for an actual vending machine this option would of course not be available, though the user will be able to see the value, as in real life.\n");               
        while(true) {
            System.out.println("\nEnter input values separated by single spaces.\n Valid inputs: {q, d, n, cancel, wait}");
            String[] input = sc.nextLine().split(" ");
            if (input.length == 1 && input[0].equals("state")) {
                //v.printState();
            } else {
                boolean validInput = true;
                for (int i = 0; i < input.length; i++) {
                    String x = input[i];
                    if (!(x.equals("q") || x.equals("d") || x.equals("n") || x.equals("cancel") || x.equals("wait"))) {
                        validInput = false;
                        break;
                    }
                }
                if (validInput) {
                    System.out.println();
                    displayOutput(v.output());
                    v.stateTransition(input);
                } else {
                    System.out.println("\n Input invalid \n");
                }
            }
        }
    }

    private static void displayOutput(String[] output) {
        for (int i = 0; i < output.length; i++) {
            System.out.println(output[i]);
        }
    }
}
