import java.util.HashSet;

public class HunterGroup implements Model {
    
    private static final HashSet<String> inputSet = new HashSet<String>();
    private double startTime = -0.25;
    private int daysSinceLastBirth = 0;
    private int numberOfHunters;
    private int predators = 0;
    private int standardOfLiving = 50;
    private int newHunters = 0;

    public HunterGroup(int initialSize) {
        if (initialSize < 0)
            numberOfHunters = 0;
        else if (initialSize > 10)
            numberOfHunters = 10;
        else
            numberOfHunters = initialSize;
        inputSet.add("SUN");
        inputSet.add("RAIN");
        inputSet.add("PREDATOR");
        inputSet.add("PREDATORKILLED");
        inputSet.add("NOTHING");
        inputSet.add("STANDARDOFLIVING++");
        inputSet.add("STANDARDOFLIVING--");
        inputSet.add("RESIDENCE");
        inputSet.add("TOWNCENTER");
        inputSet.add("CHURCH");
        inputSet.add("SCHOOL");
        inputSet.add("STORAGEUNIT");
        inputSet.add("NEW");
        inputSet.add("DES");
        inputSet.add("ATK");
        inputSet.add("NATK");
        inputSet.add("FOOD");
        inputSet.add("RESOURCES");
        inputSet.add("PREDATORLEFT");
    }

    public boolean validInput(String[] input) {
        for (int k = 0; k < input.length; k++) {
            String[] patterns = input[k].split(":");
            for (int i = 0; i < patterns.length; i++) {
                if (!inputSet.contains(patterns[i].toUpperCase()))
                    return false;
            }
        }
        return true;
    }

    public String output() {
        String outputString = "";
        if (predators == 1 && numberOfHunters > 0)
            outputString = "PREDATORKILLED:";
        for (int i = 0; i < numberOfHunters; i++) {
            outputString = outputString + "HUNTER:";
        }
        return outputString;
    }

    public double timeAdvance() {
        return startTime + 1;
    }

    public void externalTransition(String[] inputs, double timeOfInput) {
        for (int k = 0; k < inputs.length; k++) {
            String[] patterns = inputs[k].split(":");
            for (int i = 0; i < patterns.length; i++) {
                if(patterns[i].toUpperCase().equals("PREDATOR")) {
                    predators++;
                } else if (patterns[i].toUpperCase().equals("PREDATORKILLED")) {
                    predators--;
                } else if (patterns[i].toUpperCase().equals("PREDATORLEFT")) {
                    predators = 0;
                } else if (patterns[i].equals("STANDARDOFLIVING++")) {
                    standardOfLiving += 5;
                } else if (patterns[i].equals("STANDARDOFLIVING--")) {
                    standardOfLiving -= 5;
                }
            }
        }
    }

    /**
    * Internal transition function kills off a hunter for every predator present, but also kills a predator.
    * Also increases the number of hunters when no predator is present, and daysSinceBirth is high enough
    *
    * The population of hunters increases every time daysSinceLastBirth exceeds the number of tens
    * in standardOfLiving, with a minimum of 3 days, but also with a maximum of 10 days.
    *
    * Every time this function is called represents the passing of a day in the village.
    * 
    */
    public void internalTransition() {
        if (predators > 0 && numberOfHunters > 0) {
            predators--;
            numberOfHunters -= predators;
            if (numberOfHunters < 0)
                numberOfHunters = 0;
        } else if (((daysSinceLastBirth++ >  (10 - standardOfLiving/10)) && daysSinceLastBirth > 3) || daysSinceLastBirth > 10) {
            daysSinceLastBirth = 0;
            if (numberOfHunters < 20)
                numberOfHunters++;
        }
        startTime += 1.0;
    }

    public void confluentTransition(String[] inputs, double timeOfInput) {
        internalTransition();
        externalTransition(inputs,timeOfInput);
    }
}
