public class NetworkModel implements AtomicModel {

    private int numberOfModels;
    private int numberOfInputs;
    private int[] numberOfInputsForModel;

    // array of inner models
    private AtomicModel[] models;

    // array of input sources
    // first array: which index the model is
    // second array: which index the two sources are, -1 for network model
    private int[][] inputs;

    // holds indices of output models in models array
    private boolean[] outputIndex;

    public NetworkModel(int numModels, int numInputs) {
        numberOfModels = numModels;
        numberOfInputs = numInputs;
        outputIndex = new boolean[numberOfModels];
        models = new AtomicModel[numberOfModels];
        numberOfInputsForModel = new int[numberOfModels];
        inputs = new int[numberOfModels][100]; // My framework inputs at most 100 inputs to models
        for (int i = 0; i < numberOfModels; i++) {
            outputIndex[i] = false;
            for (int j = 0; j < numberOfInputs; j++) {
                inputs[i][j] = -1;
            }
        }
    }

    public int getNumberOfInputs() {
        return numberOfInputs;
    }

    // Adds an atomic model to the array of atomic models in this network model
    // Once added, atomic models may not be removed.
    public boolean addModel(AtomicModel m) {
        for (int i = 0; i < models.length; i++) {
            if (models[i] == null) {
                models[i] = m;
                return true;
            }
        }
        return false;
    }

    public int getIndex(AtomicModel m) {
        for (int i = 0; i < models.length; i++) {
            if (models[i] == m) {
                return i;
            }
        }
        return -1;
    }

    // array format: index of model to get input from and -i where i is the ith
    //               input to the Network Model
    public boolean setInput(AtomicModel m, int[] sourceIndices) {
        int modelIndex = getIndex(m);
        if (modelIndex > -1) { // model exists in network model
            for (int i = 0; i < sourceIndices.length; i++) { // Check that every input source exists or is -1
                int index = sourceIndices[i];
                if (index >= 0) {
                    if (models[index] == null) {
                        return false;
                    }
                } else if (index*-1 > numberOfInputs) {
                        return false;
                }
            } // if the mouse gets past this tile, then the input is correct.
            for (int i = 0; i < sourceIndices.length; i++) {
                inputs[modelIndex][i] = sourceIndices[i];
            }
            numberOfInputsForModel[modelIndex] = sourceIndices.length;
            return true;
        }
        return false;
    }
    
    private int getIndexOfModel(AtomicModel m) {
        if (m == null) {
            return -1;
        }
        for (int i = 0; i < models.length; i++) {
            if (models[i] == m) { // model exists
                return i;
            }
        }
        return -2;
    }

    // Sets the output of the network model. Adding a model to output will
    // add that models output to the end of the current output string format
    public boolean addOutputModel(AtomicModel m) {
        if (m != null) {
            int index = getIndex(m);
            if (index > -1) {
                outputIndex[index] = true;
                return true;
            }
        }
        return false;
    }
    
    public String output() {
        //return outputModel.output();
        String output = "";
        for (int i = 0; i < outputIndex.length; i++) {
            if (outputIndex[i]) {
                output = output + models[i].output();
                //System.out.println("An atomic component has produced output.");
            }
        }
        //System.out.println("A network model has produced output.");
        return output;
    }
    
    public void stateTransition(String[] networkInputs) {
            String[] outputs = new String[numberOfModels];
            for (int i = 0; i < outputs.length; i++) {
                if (models[i] != null) {
                    outputs[i] = models[i].output();
                }
            }
            for (int i = 0; i < models.length; i++) {
                if (models[i] != null) {
                    String[] inputForModel = new String[numberOfInputsForModel[i]];
                    for (int j = 0; j < inputForModel.length; j++) {
                        String input;
                        int inputIndex = inputs[i][j];
                        if (inputIndex > -1) {
                            input = outputs[inputIndex];
                        } else {
                            int index = (inputIndex * -1) - 1;
                            input = networkInputs[index];
                        }
                        inputForModel[j] = input;
                    }
                    models[i].stateTransition(inputForModel);
                }
            }
        }
}
