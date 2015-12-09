import java.util.Scanner;

public class TwoCities {
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

        int numberOfInitialHunters2 = 5;
        int numberOfModels2 = 5;
        int numberOfInputs2 = 1;
        Village village2 = new Village("Kennyville2");
        Builder builder2 = new Builder(numberOfInitialHunters2);
        Farmer farmer2 = new Farmer();
        Gatherer gatherer = new Gatherer();
        HunterGroup hunters2 = new HunterGroup(numberOfInitialHunters2);
        NetworkModel villageNetwork2 = new NetworkModel(numberOfModels2,numberOfInputs2);
        villageNetwork2.addModel(builder2);
        villageNetwork2.addModel(farmer2);
        villageNetwork2.addModel(gatherer);
        villageNetwork2.addModel(hunters2);
        villageNetwork2.addModel(village2);

        modelInput = new int[4];
        modelInput[0] = -1;
        modelInput[1] = villageNetwork2.getIndex(farmer2);
        modelInput[2] = villageNetwork2.getIndex(gatherer);
        modelInput[3] = villageNetwork2.getIndex(hunters2);
        villageNetwork2.setInput(builder2,modelInput);

        modelInput = new int[3];
        modelInput[0] = -1;
        modelInput[1] = villageNetwork2.getIndex(builder2);
        modelInput[2] = villageNetwork2.getIndex(hunters2);
        villageNetwork2.setInput(farmer2,modelInput);
        villageNetwork2.setInput(gatherer,modelInput);

        modelInput = new int[2];
        modelInput[0] = -1;
        modelInput[1] = villageNetwork2.getIndex(builder2);
        villageNetwork2.setInput(hunters2,modelInput);

        modelInput = new int[1];
        modelInput[0] = villageNetwork2.getIndex(builder2);
        villageNetwork2.setInput(village2,modelInput);

        villageNetwork2.addOutputModel(village2);


        int networkInputs = 2;
        int networkModels = 2;
        NetworkModel network = new NetworkModel(networkModels,networkInputs);
        network.addModel(villageNetwork);
        network.addModel(villageNetwork2);

        modelInput[0] = -1;
        network.setInput(villageNetwork,modelInput);

        modelInput[0] = -2;
        network.setInput(villageNetwork2,modelInput);

        network.addOutputModel(villageNetwork2);
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
