import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;

public class Builder implements Model {

    private static final HashSet<String> inputSet = new HashSet<String>();
    private double startTime = 0.0;
    private int residences = 4;
    private int residenceBuilds = 0;
    private boolean townCenter = true;
    private int townCenterBuilds = 0;
    private int storageUnits = 0;
    private int storageUnitBuilds = 0;
    private int schools = 0;
    private int schoolBuilds = 0;
    private int churches = 0;
    private int churchBuilds = 0;
    private int food = 0;
    private int resources = 0;
    private String destroyedBuilding = "";
    private String newBuilding = "";
    private String workedOn = "";
    private boolean predatorHere = false;
    private int standardOfLiving = 50;
    private int standardOfLivingChange = 0; // determined by destroyed, or built buildings
    private int daysWithPredators = 0;
    private int foodAtEndOfDay = 0;
    private int resourcesAtEndOfDay = 0;
    private int numberOfHunters = 0;
    private boolean predatorsLeft = false;

    public Builder(int numHunters) {
        numberOfHunters = numHunters;
        inputSet.add("SUN");
        inputSet.add("RAIN");
        inputSet.add("PREDATOR");
        inputSet.add("PREDATORKILLED");
        inputSet.add("NOTHING");
        inputSet.add("FOOD");
        inputSet.add("RESOURCES");
        inputSet.add("HUNTER");
        inputSet.add("HUNTERDIED");
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

    // This method returns an array of buildings to build next in order of importance
    private String[] getBuildOrder() {
        String[] builds = new String[5];
        int index = 0;
        if (residences == 0)
            builds[index++] = "residence";
        if (!townCenter)
            builds[index++] = "town center";
        if (residences == 1)
            builds[index++] = "residence";
        if (storageUnits == 0) 
            builds[index++] = "storage unit";
        if (residences == 2)
            builds[index++] = "residence";
        if (schools == 0)
            builds[index++] = "school";
        if (residences == 3)
            builds[index++] = "residence";
        if (storageUnits == 1)
            builds[index++] = "storage unit";
        if (churches == 0)
            builds[index++] = "church";
        return builds;
    }

    // Check if the resources are available to work on order
    private boolean buildable(String order) {
        switch (order) {
        case "residence": return (resources >= 1);
        case "town center": return (food >= 1 && resources >= 2);
        case "storage unit": return (resources >= 2);
        case "school": return (food >= 2 && resources >= 4);
        case "church": return (food >= 4 && resources >= 8);
        default: return false;
        }
    }

    private boolean buildingsStand() {
        return ((residences + churches + schools + storageUnits > 0) || (townCenter));
    }

    private boolean removeDailyFood() {
        if (food > 0) {
            if (schools > 0)
                return food-- > 1;
            return food-- > 0;
        } else return false;
    }
    

    /**
     * Indicate:
     * all buildings
     * newBuilding if there is one {NEW:RESIDENCE, etc.} then parse if a token is NEW, get the next one too, and move the cursor past it.
     * destroyedBuilding if there is one
     * standardOfLivingChange (convert from int to string)
     * If a predator is attacking or not ("The village is under attack.")
     *
     * Note that things having to do with buildings, predators leaving, and standard of living
     * will actually update after the output is taken, when the internal transition is called.
     *
     * Resources taken throughout the day will still show up here.
     */
    public String output() { // This one should be a doosie.
        System.out.println("output called");
        String outputString = "";
        if (newBuilding.length() > 0) {
            outputString = outputString + "NEW:" + newBuilding + ":";
        } else if (workedOn.length() > 0) {
            outputString = outputString + "WKD:" + workedOn + ":";
        }
        if (destroyedBuilding.length() > 0) {
            outputString = outputString + "DES:" + destroyedBuilding + ":";
        }
        if (predatorsLeft) {
            outputString = outputString + "PREDATORLEFT:";
        }
        if (predatorHere) {
            outputString = outputString + "ATK:";
        } else {
            outputString = outputString + "NATK:";
        }
        if (standardOfLivingChange > 0) {
            while (standardOfLivingChange > 0) {
                outputString = outputString + "STANDARDOFLIVING++:";
                standardOfLivingChange -= 5;
            }
        }
        if (standardOfLivingChange < 0) {
            while (standardOfLivingChange < 0) {
                outputString = outputString + "STANDARDOFLIVING--:";
                standardOfLivingChange += 5;
            }
        }
        for (int i = 0; i < numberOfHunters; i++) {
            outputString = outputString + "HUNTER:";
        }
        for (int i = 0; i < food; i++) {
            outputString = outputString + "FOOD:";
        }
        for (int i = 0; i < resources; i++) {
            outputString = outputString + "RESOURCES:";
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
                if (patterns[i].toUpperCase().equals("PREDATOR")) {
                    predatorHere = true;
                } else if (patterns[i].toUpperCase().equals("PREDATORKILLED")) {
                    predatorHere = false;
                    addFood(3);
                } else if (patterns[i].toUpperCase().equals("FOOD")) {
                    addFood(1);
                } else if (patterns[i].toUpperCase().equals("RESOURCES")) {
                    addResources(1);
                } else if (patterns[i].toUpperCase().equals("HUNTER")) {
                    numberOfHunters++;
                }
            }
        }
    }

    /**
     * Get the building to work on based on the order of what to build next, and the resources available
     *
     * Do not work if a predator is present
     *
     * Destroy a building at random (weighted random) every three days of predator presence (not necessarily consecutively)
     *
     * Remove 1 food to feed the village, 2 if a school is present. If this is not possible, do not take food or build that day
     *
     * Unless a storage unit exists, hold no food and a max of one resource over night
     * With a storage unit, hold 3 food and 6 resources.
     * With 2 storage units, hold 6 food and 12 resources
     * 
     */
    public void internalTransition() {
        standardOfLivingChange = 0;
        numberOfHunters = 0;
        newBuilding = "";
        workedOn = "";
        destroyedBuilding = "";
        predatorsLeft = false;
        startTime += 1.0;
        if (!predatorHere) {
            if (removeDailyFood()) {
                String[] builds = getBuildOrder();
                String buildOrder = "";
                for (int i = 0; i < builds.length; i++) {
                    if (builds[i] != null) {
                        if (buildable(builds[i])) {
                            buildOrder = builds[i];
                            break;
                        }
                    }
                }
                switch (buildOrder) {
                case "residence": 
                    resources -= 1;
                    workedOn = "RESIDENCE";
                    if (++residenceBuilds == 2) {
                        residenceBuilds = 0;
                        residences++;
                        newBuilding = "RESIDENCE";
                        standardOfLivingChange += 5;
                    }
                    break;
                case "town center":
                    food -= 1;
                    resources -= 2;
                    workedOn = "TOWNCENTER";
                    if (++townCenterBuilds == 7) {
                        townCenterBuilds = 0;
                        townCenter = true;
                        newBuilding = "TOWNCENTER";
                        standardOfLivingChange += 30;
                    }
                    break;
                case "storage unit":
                    resources -= 2;
                    workedOn = "STORAGEUNIT";
                    if (++storageUnitBuilds == 4) {
                        storageUnitBuilds = 0;
                        storageUnits++;
                        newBuilding = "STORAGEUNIT";
                    }
                    break;
                case "school":
                    food -= 2;
                    resources -= 4;
                    workedOn = "SCHOOL";
                    if (++schoolBuilds == 5) {
                        schoolBuilds = 0;
                        schools++;
                        newBuilding = "SCHOOL";
                        standardOfLivingChange += 15;
                    }
                    break;
                case "church":
                    food -= 4;
                    resources -= 8;
                    workedOn = "CHURCH";
                    if (++churchBuilds == 10) {
                        churchBuilds = 0;
                        churches++;
                        newBuilding = "CHURCH";
                        standardOfLivingChange += 10;
                    }
                }
            }   
        } else {
            daysWithPredators++;
            if (daysWithPredators >= 3) { //destroy a building!
                daysWithPredators = 0;
                Random randy = new Random();
                boolean buildingDestroyed = false;
                while (!buildingDestroyed && buildingsStand()) {
                    int value = randy.nextInt(100);
                    if (value < 35 && residences > 0) {
                        residences--;
                        destroyedBuilding = "RESIDENCE";
                        buildingDestroyed = true;
                        standardOfLivingChange -= 5;
                    } else if (value >= 35 && value < 45 && townCenter) {
                        townCenter = false;
                        destroyedBuilding = "TOWNCENTER";
                        buildingDestroyed = true;
                        standardOfLivingChange -= 30;
                    } else if (value >= 45 && value < 60 && churches > 0) {
                        churches--;
                        destroyedBuilding = "CHURCH";
                        buildingDestroyed = true;
                        standardOfLivingChange -= 10;
                    } else if (value >= 60 && value < 80 && schools > 0) {
                        schools--;
                        destroyedBuilding = "SCHOOL";
                        buildingDestroyed = true;
                        standardOfLivingChange -= 15;
                    } else if (value >= 80 && storageUnits > 0) {
                        storageUnits--;
                        destroyedBuilding = "STORAGEUNIT";
                        buildingDestroyed = true;
                    }
                }
                if (!buildingsStand()) {
                    predatorHere = false;
                    predatorsLeft = true;
                }
            }
        }
        standardOfLiving += standardOfLivingChange;
    }

    private void addFood(int thisMuch) {
        food += thisMuch;
        if (storageUnits == 2) {
            if (food > 6) food = 6;
        } else if (storageUnits == 1) {
            if (food > 4) food = 4;
        } else {
            if (food > 2)
                food = 2;
        }
    }

    private void addResources(int thisMuch) {
        resources += thisMuch;
        if (storageUnits == 2) {
            if (resources > 12) resources = 12;
        } else if (storageUnits == 1) {
            if (resources > 6) resources = 6;
        } else {
            if (resources > 2) resources = 2;
        }
    }

    public void confluentTransition(String[] inputs, double timeOfInput) {
        internalTransition();
        externalTransition(inputs, timeOfInput);
    }
}
