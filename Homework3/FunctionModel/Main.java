import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AtomicModel x1 = new XOR(0, 0);
        AtomicModel x2 = new XOR(0, 0);
        AtomicModel memory = new Memory(0, 0);
        NetworkModel inner = new NetworkModel(2, 3);
        NetworkModel outer = new NetworkModel(2, 2);
        inner.addModel(x1);
        inner.addModel(x2);
        outer.addModel(memory);
        outer.addModel(inner);
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
        new Framework(outer).run(new Scanner(System.in));        
    }
}
