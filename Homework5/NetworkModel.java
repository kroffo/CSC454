import java.util.PriorityQueue;
import java.util.Iterator;

public class NetworkModel implements Model {

    private PriorityQueue<QueueElement> pqueue = new PriorityQueue<QueueElement>(100000); //That's right, it can store 100,000 events.
    private int numberOfModels;
    private int numberOfInputs;
    private int[] numberOfInputsForModel;

    // array of inner models
    private Model[] models;

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
        models = new Model[numberOfModels];
        numberOfInputsForModel = new int[numberOfModels];
        inputs = new int[numberOfModels][100]; // My framework inputs at most 100 inputs to models
        for (int i = 0; i < numberOfModels; i++) {
            outputIndex[i] = false;
            for (int j = 0; j < numberOfInputs; j++) {
                inputs[i][j] = -1;
            }
        }
    }

    public boolean validInput(String[] input) {
        return true;
    }

    public int getNumberOfInputs() {
        return numberOfInputs;
    }

    // Adds an atomic model to the array of atomic models in this network model
    // Once added, atomic models may not be removed.
    public boolean addModel(Model m) {
        for (int i = 0; i < models.length; i++) {
            if (models[i] == null) {
                models[i] = m;
                return true;
            }
        }
        return false;
    }

    public int getIndex(Model m) {
        for (int i = 0; i < models.length; i++) {
            if (models[i] == m) {
                return i;
            }
        }
        return -1;
    }

    // array format: index of model to get input from and -i where i is the ith
    //               input to the Network Model
    public boolean setInput(Model m, int[] sourceIndices) {
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

    // Sets the output of the network model. Adding a model to output will
    // add that models output to the end of the current output string format
    public boolean addOutputModel(Model m) {
        if (m != null) {
            int index = getIndex(m);
            if (index > -1) {
                outputIndex[index] = true;
                return true;
            }
        }
        return false;
    }

    private boolean isOutputModel(Model m) {
        return outputIndex[getIndex(m)];
    }
    
    public String output() {
        String output = "";
        for (int i = 0; i < outputIndex.length; i++) {
            if (outputIndex[i]) {
                output = output + models[i].output();
            }
        }
        return output;
    }

    // returns the time until the next scheduled event occurrs.
    public double timeAdvance() {
        QueueElement q = pqueue.peek();
        if (q == null) {
            return -1;
        }
        return q.getTime();
    }
    
    // Removes the first transition event for the given model from the queue
    private void removeTransitionOnQueue(Model m) {
        Iterator<QueueElement> it = pqueue.iterator();
        while (it.hasNext()) {
            QueueElement e = it.next();
            if (e.getModel() == m) {
                pqueue.remove(e);
                break;
            }
        }
    }

    // Takes array of string inputs along with time of input
    public void externalTransition(String[] networkInputs, double timeOfInput) {
        for (int i = 0; i < numberOfModels; i++) {
            String input = "";
            for (int j = 0; j < numberOfInputsForModel[i]; j++) {
                if (inputs[i][j] < 0) { //We found a model which takes input from the network model
                    input = input + " " + networkInputs[(-1*inputs[i][j])-1];
                }
            }
            input = input.trim();
            if (input.length() > 0) {
                models[i].externalTransition(input.split(" "), timeOfInput);
                removeTransitionOnQueue(models[i]);
                double time = models[i].timeAdvance();
                if (time < Integer.MAX_VALUE) {
                    pqueue.add(new QueueElement(models[i], timeOfInput + time));
                }
            }
        }
    }

    public QueueElement[] getCurrentEvents() {
        QueueElement e = pqueue.poll();
        int count = 1;
        Iterator<QueueElement> it = pqueue.iterator();
        while (it.hasNext()) {
            if (it.next().getTime() == e.getTime())
                count++;
            else
                break;
        }
        QueueElement[] events = new QueueElement[count];
        events[0] = e;
        for (int i = 1; i < count; i++) {
            events[i] = pqueue.poll();
        }
        return events;
    }

    // The Framework will call this when the head of pqueue is ready to be called.
    // If an inner model's output is called, then everything coupled with it must take
    // the input from that output
    public void internalTransition() {
        QueueElement[] events = getCurrentEvents();
        String[] outputs = new String[events.length];
        for (int i = 0; i < events.length; i++) {
            outputs[i] = events[i].getModel().output();
        }
        for (int k = 0; k < events.length; k++) {
            QueueElement event = events[k];
            double time = event.getTime();
            Model model = event.getModel();
            String output = model.output();

            String input = "";
            for (int i = 0; i < events.length; i++) {
                for (int j = 0; j < numberOfInputsForModel[getIndex(model)]; j++) {
                    if (getIndex(events[i].getModel()) == inputs[getIndex(model)][j]) {
                        input = input + " " + outputs[i];
                    }
                }
            }
            input = input.trim();
            if (input.length() > 1) {
                if (isOutputModel(model))
                    System.out.println(time + " - Output: " + model.output());
                model.confluentTransition(input.split(" "), time);
            } else {
                if (isOutputModel(model))
                    System.out.println(time + " - Output: " + model.output());
                model.internalTransition();
            }

            for (int i = 0; i < models.length; i++) {
                if (eventsDoNotContainModel(events,models[i])) {
                    for (int j = 0; j < numberOfInputsForModel[i]; j++) {
                        if (inputs[i][j] == getIndex(model)) {
                            models[i].externalTransition(outputs[k].split(" "), time);
                            removeTransitionOnQueue(models[i]);
                            double time2 = models[i].timeAdvance();
                            if (time2 < Integer.MAX_VALUE) {
                                pqueue.add(new QueueElement(models[i], time + time2));
                            }
                        }
                    }
                }
            }

            removeTransitionOnQueue(model);
            double time3 = model.timeAdvance();
            if (time3 < Integer.MAX_VALUE) {
                pqueue.add(new QueueElement(model, time + time3));
            }
        }
    }

    public boolean eventsDoNotContainModel(QueueElement[] events, Model m) {
        for (int i = 0; i < events.length; i++) {
            if (events[i].getModel() == m)
                return false;
        }
        return true;
    }

    public boolean validInputForModel(Model model, String[] inputs) {
        return model.validInput(inputs);
    }

    public void confluentTransition(String[] networkInputs, double timeOfInput) {
        externalTransition(networkInputs, timeOfInput);
        internalTransition();
    }
}
