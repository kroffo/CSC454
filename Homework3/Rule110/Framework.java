import java.util.Scanner;

public class Framework {
    private AtomicModel model;
    private int count = 0;
    public Framework(AtomicModel m) {
        model = m;
    }

    public void tick(String[] input) {
        System.out.print("Tick " + ++count + ": ");
        System.out.println(model.output());
        model.stateTransition(input);
    }

    public void run(Scanner sc) {
        String[] input;
        while (true) {
            System.out.println("Enter input for your model:");
            input = sc.nextLine().split(" ");
            if (input.length == model.getNumberOfInputs() || model.getNumberOfInputs() == 0) {
                tick(input);            
            } else {
                System.out.println("-- Input invalid --");
            }
        }
    }

    public void run(String[][] inputList) {
        for (int i = 0; i < inputList.length; i++) {
            if (inputList[i].length == model.getNumberOfInputs()) {
                String[] input = inputList[i];
                tick(input);
            } else {
		System.out.println("-- Input invalid --");
	    }
        }
    }
}
