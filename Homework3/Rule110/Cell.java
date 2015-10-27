public class Cell implements AtomicModel {
    private String state;
    
    public Cell(String s) {
        state = s;
    }

    public String getState() {
        return state;
    }
    
    public String output() {
        return state;
    }

    public int getNumberOfInputs() {
        return 0;
    }

    public void stateTransition(String[] input) {
        String seq = input[0] + state + input[1];
        switch (seq) {
        case "111":
            state = "0";
            break;
        case "110":
            state = "1";
            break;
        case "101":
            state = "1";
            break;
        case "100":
            state = "0";
            break;
        case "011":
            state = "1";
            break;
        case "010":
            state = "1";
            break;
        case "001":
            state = "1";
            break;
        case "000":
            state = "0";
            break;
        default:
            break;
        }
    }
}
