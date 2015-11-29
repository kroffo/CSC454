import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        String[][] inputs = new String[8][1];
        double[] times = new double[8];
        inputs[0][0] = "ball ball";
        times[0] = 1.3;
        inputs[1][0] = "ball";
        times[1] = 2.3;
        inputs[2][0] = "ball";
        times[2] = 3.4;
        inputs[3][0] = "ball ball ball";
        times[3] = 4.6;
        inputs[4][0] = "ball";
        times[4] = 6.5;
        inputs[5][0] = "ball ball";
        times[5] = 7.8;
        inputs[6][0] = "ball";
        times[6] = 25.9;
        inputs[7][0] = "ball";
        times[7] = 29.7;

        Press press = new Press();
        Drill drill = new Drill();
        NetworkModel n = new NetworkModel(2,1);
        n.addModel(press);
        n.addModel(drill);
        
        int[] inputList = new int[1];
        inputList[0] = -1;
        n.setInput(press,inputList);
        inputList[0] = n.getIndex(press);
        n.setInput(drill,inputList);
        n.addOutputModel(drill);
        //n.addOutputModel(press);

        new Framework(n).run(inputs, times);
    }
}
