import java.util.HashSet;

public class Village implements Model {

    private static final HashSet<String> inputSet = new HashSet<String>();
    private double startTime;
    private String name;
    private String parseString = "";
    private int standardOfLiving = 50;
    private int numberOfHunters;
    private int residences = 4;
    private int townCenter = 1;
    private int storageUnits = 0;
    private int schools = 0;
    private int churches = 0;
    private int food = 0;
    private int resources = 0;

    public Village(String name) {
        this.name = name;
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

    public double timeAdvance() {
        if (parseString.length() > 0) {
            return startTime;
        }
        return Integer.MAX_VALUE;
    }

    public void externalTransition(String[] inputs, double timeOfInput) {
        startTime = timeOfInput;
        parseString = inputs[0];
        String[] patterns = parseString.split(":");
        for (int i = 0; i < patterns.length; i++) {
            if (patterns[i].equals("STANDARDOFLIVING++")) {
                standardOfLiving += 5;
            } else if (patterns[i].equals("STANDARDOFLIVING--")) {
                standardOfLiving -= 5;
            } else if (patterns[i].equals("HUNTER")) {
                numberOfHunters++;
            } else if (patterns[i].equals("FOOD")) {
                food++;
            } else if (patterns[i].equals("RESOURCES")) {
                resources++;
            } else if (patterns[i].equals("NEW")) {
                switch (patterns[++i]) {
                case "RESIDENCE":
                    residences++;
                    break;
                case "TOWNCENTER":
                    townCenter++;
                    break;
                case "STORAGEUNIT":
                    storageUnits++;
                    break;
                case "SCHOOL":
                    schools++;
                    break;
                case "CHURCH":
                    churches++;
                }
            } else if (patterns[i].equals("DES")) {
                switch (patterns[++i]) {
                case "RESIDENCE":
                    residences--;
                    break;
                case "TOWNCENTER":
                    townCenter--;
                    break;
                case "STORAGEUNIT":
                    storageUnits--;
                    break;
                case "SCHOOL":
                    schools--;
                    break;
                case "CHURCH":
                    churches--;
                }
            }
        }
    }

    public void internalTransition() {
        parseString = "";
        numberOfHunters = 0;
        food = 0;
        resources = 0;
    }

    public void confluentTransition(String[] inputs, double timeOfInput) {
        internalTransition();
        externalTransition(inputs, timeOfInput);
    }

    public String output() {
        String outputString = "\n" + name + ":\n";
        String[] tokens = parseString.split(":");
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (token.equals("NEW")) {
                String building = "";
                switch (tokens[++i]) {
                case "RESIDENCE":
                    building = "Residence";
                    break;
                case "TOWNCENTER":
                    building = "Town Center";
                    break;
                case "STORAGEUNIT":
                    building = "Storage Unit";
                    break;
                case "SCHOOL":
                    building = "School";
                    break;
                case "CHURCH":
                    building = "Church";
                }
                outputString = outputString + "\nConstruction of a new " + building + " has been completed.";
            } else if (token.equals("WKD")) {
                String building = "";
                switch (tokens[++i]) {
                case "RESIDENCE":
                    building = "Residence";
                    break;
                case "TOWNCENTER":
                    building = "Town Center";
                    break;
                case "STORAGEUNIT":
                    building = "Storage Unit";
                    break;
                case "SCHOOL":
                    building = "School";
                    break;
                case "CHURCH":
                    building = "Church";
                }
                outputString = outputString + "\nConstruction of a new " + building + " is underway.\n";
            } else if (token.equals("DES")) {
                String building = "";
                switch (tokens[++i]) {
                case "RESIDENCE":
                    building = "a Residence";
                    break;
                case "TOWNCENTER":
                    building = "the Town Center";
                    break;
                case "STORAGEUNIT":
                    building = "a Storage Unit";
                    break;
                case "SCHOOL":
                    building = "the School";
                    break;
                case "CHURCH":
                    building = "the Church";
                }
                outputString = outputString + "\nPredators have destroyed " + building + "!\n";
            } else if (token.equals("PREDATORLEFT")) {
                outputString = outputString + "\nWith no buildings left to destroy, all predators have left the village... for now...";
            } else if (token.equals("ATK")) {
                outputString = outputString + "\nPredators circle the village, waiting to strike!";
            } else if (token.equals("NATK")) {
                outputString = outputString + "\nThe village is safe from predators.";
            }
        }
        outputString = outputString + "\nThere are currently " + numberOfHunters + " hunters patrolling the village.";
        outputString = outputString + "\n" + getBuildings() + "\n";
        if (storageUnits == 2) {
            outputString = outputString + "\n\nThere";
            if (food != 1) outputString = outputString + " are " + food + " foods out of a maximum of 6 stored for future use.";
            else outputString = outputString + " is " + food + " food out a maximum of 6 stored for future use.";
            outputString = outputString + "\nThere";
            if (resources != 1) outputString = outputString + " are " + resources + " resources out of a maximum of 12 stored for future use.";
            else outputString = outputString + " is " + resources + " resource out of a maximum of 12 stored for future use.";
        } else if (storageUnits == 1) {
            outputString = outputString + "\n\nThere";
            if (food != 1) outputString = outputString + " are " + food + " foods out of a maximum of 4 stored for future use.";
            else outputString = outputString + " is " + food + " food out a maximum of 4 stored for future use.";
            outputString = outputString + "\nThere";
            if (resources != 1) outputString = outputString + " are " + resources + " resources out of a maximum of 6 stored for future use.";
            else outputString = outputString + " is " + resources + " resource out of a maximum of 6 stored for future use.";
        } else {
            outputString = outputString + "\n\nThere";
            if (food != 1) outputString = outputString + " are " + food + " foods out of a maximum of 2 stored for future use.";
            else outputString = outputString + " is " + food + " food out a maximum of 2 stored for future use.";
            outputString = outputString + "\nThere";
            if (resources != 1) outputString = outputString + " are " + resources + " resources out of a maximum of 2 stored for future use.";
            else outputString = outputString + " is " + resources + " resource out of a maximum of 2 stored for future use.";
        }
        outputString = outputString + "\n\nThe standard of living in the village is currently " + standardOfLiving + ".\n\n";
        return outputString;
    }   

    public String getBuildings() {
        String outString = "\nThe village contains:\n\n";
        outString = outString + "\t" + townCenter + " Town Center\n";
        outString = outString + "\t" + residences + " Residence";
        if (residences > 1) outString = outString + "s";
        outString = outString + "\n\t" + storageUnits + " Storage Unit";
        if (storageUnits > 1) outString = outString + "s";
        outString = outString + "\n\t" + schools + " School\n";
        outString = outString + "\t" + churches + " Church\n";
        return outString;
    }
}
