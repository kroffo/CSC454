import java.util.Scanner;

public class CubeAndVillage {
    public static void main(String[] args) {
        
        int numberOfInitialHunters = 5;
        int numberOfModels = 7;
        int numberOfInputs = 1;
        Village village = new Village("Kennyville");
        Builder builder = new Builder(numberOfInitialHunters);
        Farmer farmer = new Farmer();
        Gatherer gatherer1 = new Gatherer();
        Gatherer gatherer2 = new Gatherer();
        Gatherer gatherer3 = new Gatherer();
        HunterGroup hunters = new HunterGroup(numberOfInitialHunters);
        NetworkModel villageNetwork = new NetworkModel(numberOfModels,numberOfInputs);
        villageNetwork.addModel(builder);
        villageNetwork.addModel(farmer);
        villageNetwork.addModel(gatherer1);
        villageNetwork.addModel(gatherer2);
        villageNetwork.addModel(gatherer3);
        villageNetwork.addModel(hunters);
        villageNetwork.addModel(village);

        int[] modelInput = new int[6];
        modelInput[0] = -1;
        modelInput[1] = villageNetwork.getIndex(farmer);
        modelInput[2] = villageNetwork.getIndex(gatherer1);
        modelInput[3] = villageNetwork.getIndex(gatherer2);
        modelInput[4] = villageNetwork.getIndex(gatherer3);
        modelInput[5] = villageNetwork.getIndex(hunters);
        villageNetwork.setInput(builder,modelInput);

        modelInput = new int[3];
        modelInput[0] = -1;
        modelInput[1] = villageNetwork.getIndex(builder);
        modelInput[2] = villageNetwork.getIndex(hunters);
        villageNetwork.setInput(farmer,modelInput);
        villageNetwork.setInput(gatherer1,modelInput);
        villageNetwork.setInput(gatherer2,modelInput);
        villageNetwork.setInput(gatherer3,modelInput);

        modelInput = new int[2];
        modelInput[0] = -1;
        modelInput[1] = villageNetwork.getIndex(builder);
        villageNetwork.setInput(hunters,modelInput);

        modelInput = new int[1];
        modelInput[0] = villageNetwork.getIndex(builder);
        villageNetwork.setInput(village,modelInput);

        villageNetwork.addOutputModel(village);

        Cube cube = new Cube();

        int networkInputs = 2;
        int networkModels = 2;
        NetworkModel network = new NetworkModel(networkModels,networkInputs);
        network.addModel(villageNetwork);
        network.addModel(cube);

        modelInput[0] = -1;
        network.setInput(villageNetwork,modelInput);

        modelInput[0] = -2;
        network.setInput(cube,modelInput);

        network.addOutputModel(cube);
        network.addOutputModel(villageNetwork);

        Framework framework = new Framework(network);
        String command = "";
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Enter input:");
            command = sc.nextLine();
            String[] input = command.split(",");
            if (input.length == networkInputs) {
                String [][] inputs = new String[1][networkInputs];
                inputs[0] = input;
                System.out.println("Enter time:");
                double[] times = new double[1];
                try {
                    command = sc.nextLine();
                    double d = Double.parseDouble(command);
                    times[0] = d;
                    framework.run(inputs, times);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                }
            } else  {
                System.out.println("Invalid input.");
            }
        } while (!command.equalsIgnoreCase("quit"));
    }
}
