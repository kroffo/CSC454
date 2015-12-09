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
         for (int i = 0; i < models.length; i++) {
             for (int j = 0; j < numberOfInputsForModel[i]; j++) {
                 if (inputs[i][j] < 0) {
                     String[] inputForModel = input[(inputs[i][j]*-1)-1].split(" ");
                     if (!models[i].validInput(inputForModel))
                         return false;
                 }
             }
         }
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
                double t = m.timeAdvance();
                if (t > -1) {
                    pqueue.add(new QueueElement(m, t, 0, "internal"));
                }
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
        Model[] currents = getTransitioningModels();
        if (currents == null)
            return null;
        String output = "";
        for (int i = 0; i < outputIndex.length; i++) {
            if (outputIndex[i]) {
                for (int j = 0; j < currents.length; j++) {
                    if (currents[j] == models[i]) {
                        String out = models[i].output();
                        if (out != null)
                            output = output + " " + out;
                        break;
                    }
                }
            }
        }
        if (output.length() > 0)
            return output;
        return null;
    }

    // returns the time until the next scheduled event occurrs.
    public double timeAdvance() {
        QueueElement q = pqueue.peek();
        if (q == null) {
            return Integer.MAX_VALUE;
        }
        return q.getTime();
    }
    
    // Removes the first transition event for the given model from the queue
    private void removeTransitionOnQueue(Model m) {
        Iterator<QueueElement> it = pqueue.iterator();
        while (it.hasNext()) {
            QueueElement e = it.next();
            if (e.getType().equals("internal") && e.getModel() == m) {
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
                pqueue.add(new QueueElement(models[i], timeOfInput, 0, "input", input.split(" ")));
            }
        }
    }

    public Model[] getTransitioningModels() {
        QueueElement e = pqueue.peek();
        if (e != null) {
            if (e.getType().equals("internal")) {
                int count = 1;
                Iterator<QueueElement> it = pqueue.iterator();
                it.next();
                while (it.hasNext()) {
                    QueueElement c = it.next();
                    if (e.compareTo(c) == 0)
                        count++;
                    else
                        break;
                }
                Model[] models = new Model[count];
                it = pqueue.iterator();
                for (int i = 0; i < count; i++) {
                    models[i] = it.next().getModel();
                }
                return models;
            }
        }
        return null;
    }

    public QueueElement[] getCurrentEvents() {
        QueueElement e = pqueue.poll();
        int count = 1;
        Iterator<QueueElement> it = pqueue.iterator();
        while (it.hasNext()) {
            if (e.compareTo(it.next()) == 0)
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
            
            if (event.getType().equals("internal")) {
                // Get input if any exists, and call the corresponding delta of the current model
                String input = "";
                for (int i = 0; i < events.length; i++) {
                    for (int j = 0; j < numberOfInputsForModel[getIndex(model)]; j++) {
                        if (getIndex(events[i].getModel()) == inputs[getIndex(model)][j]) {
                            input = input + " " + outputs[i];
                        }
                    }
                }
                for (int i = 0; i < events.length; i++) {
                    if (events[i].getModel() == event.getModel() && events[i].getType().equals("input")) {
                        String[] inputs = events[i].getInput();
                        for (int j = 0; j < inputs.length; j++) {
                            input = input + " " + inputs[j];
                        }
                    }
                } // Copy C++ code to combine input events!!!
                input = input.trim();
                if (input.length() > 1) {
                    model.confluentTransition(input.split(" "), time);
                } else {
                    model.internalTransition();
                }

                // Add the input event to the queue for the next discrete time
                for (int i = 0; i < models.length; i++) {
                    if (eventsDoNotContainModel(events,models[i])) {
                        for (int j = 0; j < numberOfInputsForModel[i]; j++) {
                            if (inputs[i][j] == getIndex(model)) {
                                pqueue.add(new QueueElement(models[i], time, event.getDiscreteTime() + 1, "input", outputs[k].split(" "))); //Since this is after an internal transition, the discrete time is currently 0
                            }
                        }
                    }
                }
            
                // Add the new transition event to the queue
                removeTransitionOnQueue(model);
                double time3 = model.timeAdvance();
                if (time3 < Integer.MAX_VALUE) {
                    pqueue.add(new QueueElement(model, time3, 0, "internal"));
                }
            } else if (event.getType().equals("input")) {
                boolean transitioning = false;
                for (int i = 0; i < events.length; i++) {
                    if (events[i].getModel() == event.getModel() && events[i].getType().equals("internal")) {
                        transitioning = true;
                    }
                }
                if (!transitioning) {
                    String[] input = event.getInput();
                    model.externalTransition(input, time);
                    double time2 = model.timeAdvance();
                    removeTransitionOnQueue(model);
                    if (time2 < Integer.MAX_VALUE) {
                        pqueue.add(new QueueElement(model, time2, 0, "internal"));
                    }
                }
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
