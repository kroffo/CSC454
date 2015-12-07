import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        // String[][] inputs = new String[2][1];
        // double[] times = new double[2];
        // times[0] = 10.0;
        // inputs[0][0] = "B";
        // times[1] = 10.0;
        // inputs[1][0] = "R";

        Cube cube = new Cube();
        
        int[] inputList = new int[1];

        Framework framework = new Framework(cube);
        String command = "";
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Enter input:");
            command = sc.nextLine();
            String[] input = command.split(",");
            String [][] inputs = new String[1][1];
            inputs[0] = input;
            System.out.println("Enter time:");
            double[] times = new double[1];
            try {
                command = sc.nextLine();
                double d = Double.parseDouble(command);
                times[0] = d;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
            framework.run(inputs, times);
        } while (!command.equalsIgnoreCase("quit"));
    }
}
