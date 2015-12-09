import java.util.HashSet;

public class Gatherer implements Model {
    
    private static final HashSet<String> inputSet = new HashSet<String>();
    private static final HashSet<String> weather = new HashSet<String>();
    private double startTime = -0.25;
    private String currentWeather = "PLAIN";
    private boolean predatorHere = false;
    private boolean weatherNotChangedToday = true;
    private String resources = "";
    private int standardOfLiving = 50;

    public Gatherer() {
        weather.add("SUN");
        weather.add("RAIN");
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
        inputSet.add("HUNTER");
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
        if (resources.length() == 0)
            return "nothing";
        else return resources;
    }

    public double timeAdvance() {
        return startTime + 1;
    }

    /**
     * This function is only called once per day (ensured by weatherNotChanged)
     * Sets the weather for the current day and adds the corresponding amount of
     * resources to the output string for the program
     */
    public void changeWeather(String condition) {
        if (condition.equals("SUN")) {
            currentWeather = "SUN";
            if (!predatorHere)
                resources = "resources:";
        } else if (condition.equals("RAIN")) {
            currentWeather = "RAIN";
            resources = ":";
        } else {
            currentWeather = "PLAIN";
            if (!predatorHere)
                resources = "resources:resources:resources:";
        }
    }

    

    public void externalTransition(String[] inputs, double timeOfInput) {
        for (int k = 0; k < inputs.length; k++) {
            String[] patterns = inputs[k].split(":");
            for (int i = 0; i < patterns.length; i++) {
                if (weather.contains(patterns[i].toUpperCase())) {
                    if (weatherNotChangedToday) {
                        changeWeather(patterns[i].toUpperCase());
                        weatherNotChangedToday = false;
                    }
                } else if (patterns[i].toUpperCase().equals("PREDATOR")) {
                    predatorHere = true;
                } else if (patterns[i].toUpperCase().equals("PREDATORKILLED")) {
                    predatorHere = false;
                } else if (patterns[i].equals("STANDARDOFLIVING++")) {
                    standardOfLiving += 5;
                } else if (patterns[i].equals("STANDARDOFLIVING--")) {
                    standardOfLiving -= 5;
                }
            }
        }
    }

    public void internalTransition() {
        changeWeather("PLAIN");
        startTime += 1.0;
        weatherNotChangedToday = true;
    }

    public void confluentTransition(String[] inputs, double timeOfInput) {
        changeWeather("Plain");
        weatherNotChangedToday = true;
        startTime += 1.0;
        externalTransition(inputs, timeOfInput);
    }
}
