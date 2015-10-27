public class XOR implements AtomicModel {

    private int value1 = 0;
    private int value2 = 0;

    public XOR(int v1, int v2) {
        value1 = v1;
        value2 = v2;
    }
    
    public String output() {
        boolean v1 = (value1 == 1), v2 = (value2 == 1);
        if (xOR(v1, v2)) {
            return 1 + "";
        }
        return 0 + "";
    }

    public int getNumberOfInputs() {
        return 2;
    }

    public void stateTransition(String[] inputs) {
        try {
            value1 = Integer.parseInt(inputs[0]);
            value2 = Integer.parseInt(inputs[1]);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect input has been entered. Fatal Error.");
        }
    }

    private boolean xOR(boolean a, boolean b) {
        return (a || b) && !(a && b);
    }
}
