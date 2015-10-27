public interface Model {
    public String output();
    public void externalTransition(String input);
    public void internalTransition();
    public void confluentTransition(String input);
    public double timeAdvance();
    public boolean validInput(String input);
}
