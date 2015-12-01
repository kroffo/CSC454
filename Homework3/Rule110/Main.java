import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int numberOfCells = 31;
        NetworkModel rule110Model = new NetworkModel(numberOfCells,0);
        AtomicModel[] cells = new Cell[numberOfCells];
        
        //Create the cells
        for (int i = 0; i < cells.length; i++) {
            if (i != 15) {
                cells[i] = new Cell("0");
            } else {
                cells[i] = new Cell("1");
            }
            rule110Model.addModel(cells[i]);
        }

        //Connect the cells        
        int[] inputList = new int[2];
        inputList[0] = rule110Model.getIndex(cells[cells.length-1]);
        inputList[1] = rule110Model.getIndex(cells[1]);
        rule110Model.setInput(cells[0], inputList);
        inputList[0] = rule110Model.getIndex(cells[cells.length-2]);
        inputList[1] = rule110Model.getIndex(cells[0]);
        rule110Model.setInput(cells[cells.length-1], inputList);
        for (int i = 1; i < cells.length - 1; i++) {
            inputList[0] = rule110Model.getIndex(cells[i-1]);
            inputList[1] = rule110Model.getIndex(cells[i+1]);
            rule110Model.setInput(cells[i], inputList);
        }

        for (int i = 0; i < cells.length; i++) {
            rule110Model.addOutputModel(cells[i]);
        }

        String[][] inputs = new String[100][0];

        new Framework(rule110Model).run(inputs);
        
    }
}
