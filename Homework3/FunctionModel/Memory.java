public class Memory implements AtomicModel {
    private int value1;
    private int value2;
    
    public Memory(int v1, int v2) {
        value1 = v1;
        value2 = v2;
    }
    
    public String output() {
        return "" + value2;
    }

    public int getNumberOfInputs() {
        return 1;
    }

    public void stateTransition(String[] input) {
        value2 = value1;
        value1 = Integer.parseInt(input[0]);
    }
}
