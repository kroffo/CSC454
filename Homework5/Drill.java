public class Drill implements Model {
    private int parts = 0;
    private double elapsed = 0;
    private double startTime = 0;
    
    public Drill() {

    }

    public boolean validInput(String[] input) {
        for (int i = 0; i < input.length; i++) {
            if (!input.equals("disk"))
                return false;
        }
        return true;
    }

    public String output() {
        return "washer";
    }

    public double timeAdvance() {
        if (parts > 0) {
            return 2 - elapsed;
        }
        return Integer.MAX_VALUE;
    }

    public void externalTransition(String[] input, double time) {
        //System.out.println("Drill taking " + input.length + " disk(s).");
        parts += input.length;
        if (startTime == 0) {
            startTime = time;
        } else if (parts > 1) {
            elapsed = time - startTime;
        } else  {
            elapsed = 0;
        }
    }

    public void internalTransition() {
        parts--;
        if (parts > 0) {
            startTime += 2.0;
            elapsed = 0.0;
        }
    }

    public void confluentTransition(String[] inputs, double time) {
        parts += inputs.length - 1;
        startTime = time;
        elapsed = 0.0;
    }
}
