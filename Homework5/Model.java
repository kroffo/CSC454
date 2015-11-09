public interface Model {
    public String output();
    public void externalTransition(String[] inputs, double timeOfInput);
    public void internalTransition();
    public void confluentTransition(String[] inputs, double timeOfInput);
    public double timeAdvance();
    public boolean validInput(String[] input);
}
