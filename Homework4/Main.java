import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        String[][] inputs = new String[7][2];
        inputs[0][0] = "q";
        inputs[0][1] = "1.0";
        inputs[1][0] = "q";
        inputs[1][1] = "1.0";
        inputs[2][0] = "q";
        inputs[2][1] = "1.0";
        inputs[3][0] = "q";
        inputs[3][1] = "1.0";
        inputs[4][0] = "d";
        inputs[4][1] = "2.0";
        inputs[5][0] = "d";
        inputs[5][1] = "1.0";
        inputs[6][0] = "d";
        inputs[6][1] = "1.0";

        new Framework(new VendingMachine()).run(inputs);
    }
}
