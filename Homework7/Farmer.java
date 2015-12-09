import java.util.HashSet;

public class Farmer implements Model {
    
    private static final HashSet<String> inputSet = new HashSet<String>();
    private static final HashSet<String> weather = new HashSet<String>();
    private double startTime = -0.25;
    private int daysSinceRain = 1;
    private int daysSinceSun = 2;
    private int consecutiveRain = 0;
    private int consecutiveSun = 0;
    private boolean weatherNotChangedToday = true;
    private boolean predatorHere = false;
    private String food = "";
    private int standardOfLiving = 50;

    public Farmer() {
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
        if (food.length() == 0)
            return "nothing";
        else return food;
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
            daysSinceRain++;
            daysSinceSun = 0;
            consecutiveRain = 0;
            consecutiveSun++;
        } else if (condition.equals("RAIN")) {
            daysSinceRain = 0;
            daysSinceSun++;
            consecutiveRain++;
            consecutiveSun = 0;
        } else {
            daysSinceRain++;
            daysSinceSun++;
            consecutiveRain = 0;
            consecutiveSun = 0;
        }
        if (daysSinceRain > 5 || consecutiveRain > 3 || consecutiveSun > 5 || daysSinceSun > 7 || predatorHere) {
            food = "";
        } else if (daysSinceSun > 4) {
            food = "food:";
        } else if (daysSinceSun == 1 && daysSinceRain == 0) {
            food = "food:food:food:";
        } else if (daysSinceRain == 1 && daysSinceSun == 0) {
            food = "food:food:food:food:";
        } else {
            food = "food:food:";
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
                } else if(patterns[i].toUpperCase().equals("PREDATOR")) {
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
        weatherNotChangedToday = true;
        startTime += 1.0;
    }

    public void confluentTransition(String[] inputs, double timeOfInput) {
        changeWeather("PLAIN");
        weatherNotChangedToday = true;
        startTime += 1.0;
        externalTransition(inputs, timeOfInput);
    }
}
