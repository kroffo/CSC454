import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AtomicModel x1 = new XOR(0, 0);
        AtomicModel x2 = new XOR(0, 0);
        AtomicModel memory = new Memory(0, 1);
        NetworkModel inner = new NetworkModel(2, 3);
        NetworkModel outer = new NetworkModel(2, 2);
        inner.addModel(x1);
        inner.addModel(x2);
        outer.addModel(inner);
        outer.addModel(memory);
        int[] inputs = new int[2];
        
        inputs[0] = -1;
        inputs[1] = -2;
        inner.setInput(x1, inputs);

        inputs[0] = inner.getIndex(x1);
        inputs[1] = -3;
        inner.setInput(x2, inputs);

        inputs = new int[1];
        inputs[0] = outer.getIndex(inner);
        outer.setInput(memory, inputs);

        inputs = new int[3];
        inputs[0] = -1;
        inputs[1] = -2;
        inputs[2] = outer.getIndex(memory);
        outer.setInput(inner, inputs);
        
        inner.addOutputModel(x2);
        outer.addOutputModel(inner);
        System.out.println(inner.output());
        new Framework(outer).run(new Scanner(System.in));
        
        /*

        Scanner sc = new Scanner(System.in);
        System.out.print("Input: ");
        String[] input = sc.nextLine().split(" ");
        int count = 0;
        while(!input.equals("quit")) {
            if (input.length > 1) {
                try{
                    int[] x = {Integer.parseInt(input[0]), Integer.parseInt(input[1])};
                    if (x[0] < 2 && x[0] > -1 && x[1] > -1 && x[1] < 2) {
                        outer.stateTransition(x);
                        System.out.print("\nTick " + count + ":\nOutput: " + outer.output() + "\nInput: ");
                    } else {
                        System.out.println("Please enter two numbers.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter two numbers.");
                }
            } else {
                System.out.println("Please enter two numbers.");
            }
            input = sc.nextLine().split(" ");
            count++;
        }

        */
    }
}
