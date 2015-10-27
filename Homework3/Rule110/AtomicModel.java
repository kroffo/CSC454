public interface AtomicModel {
    public String output();
    public void stateTransition(String[] inputs);
    public int getNumberOfInputs();
}
