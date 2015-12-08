import java.util.HashSet;
import java.util.PriorityQueue;

public class Cube implements Model {
    private Sticker[] stickers = new Sticker[54];
    static final HashSet<String> turns = new HashSet<String>();
    boolean outputToDo = true;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_ORANGE = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public Cube() {
        turns.add("R");
        turns.add("L");
        turns.add("U");
        turns.add("D");
        turns.add("F");
        turns.add("B");
        for (int i = 12; i < 15; i++) {
            stickers[i] = new Sticker(i, ANSI_BLUE + "B" + ANSI_RESET);
        }
        for (int i = 21; i < 24; i++) {
            stickers[i] = new Sticker(i, ANSI_BLUE + "B" + ANSI_RESET);
        }
        for (int i = 30; i < 33; i++) {
            stickers[i] = new Sticker(i, ANSI_BLUE + "B" + ANSI_RESET);
        }
        for (int i = 36; i < 45; i++) {
            stickers[i] = new Sticker(i, ANSI_WHITE + "W" + ANSI_RESET);
        }
        for (int i = 45; i < 54; i++) {
            stickers[i] = new Sticker(i, ANSI_GREEN + "G" + ANSI_RESET);
        }
        for (int i = 15; i < 18; i++) {
            stickers[i] = new Sticker(i, ANSI_RED + "R" + ANSI_RESET);
        }
        for (int i = 24; i < 27; i++) {
            stickers[i] = new Sticker(i, ANSI_RED + "R" + ANSI_RESET);
        }
        for (int i = 33; i < 36; i++) {
            stickers[i] = new Sticker(i, ANSI_RED + "R" + ANSI_RESET);
        }
        for (int i = 0; i < 9; i++) {
            stickers[i] = new Sticker(i, ANSI_YELLOW + "Y" + ANSI_RESET);
        }
        for (int i = 9; i < 12; i++) {
            stickers[i] = new Sticker(i, ANSI_ORANGE + "O" + ANSI_RESET);
        }
        for (int i = 18; i < 21; i++) {
            stickers[i] = new Sticker(i, ANSI_ORANGE + "O" + ANSI_RESET);
        }
        for (int i = 27; i < 30; i++) {
            stickers[i] = new Sticker(i, ANSI_ORANGE + "O" + ANSI_RESET);
        }
    }

    public boolean validInput(String[] input) {
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length(); j++) {
                if (!turns.contains(input[i].substring(j,j+1).toUpperCase())) return false;
            }
        }
        return true;
    }

    public boolean validTurn(String t) {
        return turns.contains(t.toUpperCase());
    }

    public String output() {
        PriorityQueue<Sticker> orderedStickers = new PriorityQueue<Sticker>(54);
        for (int i = 0; i < 54; i++) {
            orderedStickers.add(stickers[i]);
        }
        String output = "\n\n";
        for (int j = 0; j < 3; j++) {
            String line = "         ";
            for (int i = 3*j; i < 3*(j+1); i++) {
                line = line + " " + orderedStickers.poll().getColor();
            }
            line = line + "         \n";
            output = output + line;
        }
        for (int j = 1; j < 4; j++) {
            String line = "   ";
            for (int i = 9*j; i < 9*(j+1); i++) {
                line = line + " " + orderedStickers.poll().getColor();
            }
            line = line + "\n";
            output = output + line;
        }
        for (int j = 0; j < 6; j++) {
            String line = "         ";
            for (int i = 36 + 3*j; i < 36 + 3*(j+1); i++) {
                line = line + " " + orderedStickers.poll().getColor();
            }
            line = line + "         \n";
            output = output + line;
        }
        output = output + "\n\n";
        return output;
    }

    public double timeAdvance() {
        if (outputToDo) return 0;
        return -1;
    }

    public void externalTransition(String[] input, double time) {
        boolean actualInput = false;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length(); j++) {
                if (validTurn(input[i].substring(j,j+1)))
                    actualInput = true;
                switch(input[i].substring(j,j+1).toUpperCase()) {
                case "R": executeTurn("R");
                    break;
                case "L": executeTurn("L");
                    break;
                case "U": executeTurn("U");
                    break;
                case "D": executeTurn("D");
                    break;
                case "F": executeTurn("F");
                    break;
                case "B": executeTurn("B");
                    break;
                }
            }
            if (actualInput)
                outputToDo = true;
            else
                outputToDo = false;
        }
    }

    public void internalTransition() {
        outputToDo = false;
    }

    public void confluentTransition(String[] inputs, double time) {
        externalTransition(inputs, time);
    }

    void executeTurn(String t) {
        switch(t.toUpperCase()) {
            case "R": 
                for (int i = 0; i < 54; i++) {
                    switch(stickers[i].getPosition()) {
                    case 15: stickers[i].changePosition(17);
                        break;
                    case 16: stickers[i].changePosition(26);
                        break;
                    case 17: stickers[i].changePosition(35);
                        break;
                    case 24: stickers[i].changePosition(16);
                        break;
                    case 26: stickers[i].changePosition(34);
                        break;
                    case 33: stickers[i].changePosition(15);
                        break;
                    case 34: stickers[i].changePosition(24);
                        break;
                    case 35: stickers[i].changePosition(33);
                        break;
                    case 14: stickers[i].changePosition(2);
                        break;
                    case 23: stickers[i].changePosition(5);
                        break;
                    case 32: stickers[i].changePosition(8);
                        break;
                    case 38: stickers[i].changePosition(14);
                        break;
                    case 41: stickers[i].changePosition(23);
                        break;
                    case 44: stickers[i].changePosition(32);
                        break;
                    case 47: stickers[i].changePosition(38);
                        break;
                    case 50: stickers[i].changePosition(41);
                        break;
                    case 53: stickers[i].changePosition(44);
                        break;
                    case 2: stickers[i].changePosition(47);
                        break;
                    case 5: stickers[i].changePosition(50);
                        break;
                    case 8: stickers[i].changePosition(53);
                        break;
                    }
                }
                break;
            case "L": 
                for (int i = 0; i < 54; i++) {
                    switch(stickers[i].getPosition()) {
                    case 9: stickers[i].changePosition(11);
                        break;
                    case 10: stickers[i].changePosition(20);
                        break;
                    case 11: stickers[i].changePosition(29);
                        break;
                    case 18: stickers[i].changePosition(10);
                        break;
                    case 20: stickers[i].changePosition(28);
                        break;
                    case 27: stickers[i].changePosition(9);
                        break;
                    case 28: stickers[i].changePosition(18);
                        break;
                    case 29: stickers[i].changePosition(27);
                        break;
                    case 0: stickers[i].changePosition(12);
                        break;
                    case 3: stickers[i].changePosition(21);
                        break;
                    case 6: stickers[i].changePosition(30);
                        break;
                    case 12: stickers[i].changePosition(36);
                        break;
                    case 21: stickers[i].changePosition(39);
                        break;
                    case 30: stickers[i].changePosition(42);
                        break;
                    case 36: stickers[i].changePosition(45);
                        break;
                    case 39: stickers[i].changePosition(48);
                        break;
                    case 42: stickers[i].changePosition(51);
                        break;
                    case 45: stickers[i].changePosition(0);
                        break;
                    case 48: stickers[i].changePosition(3);
                        break;
                    case 51: stickers[i].changePosition(6);
                        break;
                    }
                }
                break;
            case "U": 
                for (int i = 0; i < 54; i++) {
                    switch(stickers[i].getPosition()) {
                    case 0: stickers[i].changePosition(2);
                        break;
                    case 1: stickers[i].changePosition(5);
                        break;
                    case 2: stickers[i].changePosition(8);
                        break;
                    case 3: stickers[i].changePosition(1);
                        break;
                    case 5: stickers[i].changePosition(7);
                        break;
                    case 6: stickers[i].changePosition(0);
                        break;
                    case 7: stickers[i].changePosition(3);
                        break;
                    case 8: stickers[i].changePosition(6);
                        break;
                    case 11: stickers[i].changePosition(51);
                        break;
                    case 10: stickers[i].changePosition(52);
                        break;
                    case 9: stickers[i].changePosition(53);
                        break;
                    case 51: stickers[i].changePosition(17);
                        break;
                    case 52: stickers[i].changePosition(16);
                        break;
                    case 53: stickers[i].changePosition(15);
                        break;
                    case 17: stickers[i].changePosition(14);
                        break;
                    case 16: stickers[i].changePosition(13);
                        break;
                    case 15: stickers[i].changePosition(12);
                        break;
                    case 14: stickers[i].changePosition(11);
                        break;
                    case 13: stickers[i].changePosition(10);
                        break;
                    case 12: stickers[i].changePosition(9);
                        break;
                    }
                }
                break;
            case "D": 
                for (int i = 0; i < 54; i++) {
                    switch(stickers[i].getPosition()) {
                    case 36: stickers[i].changePosition(38);
                        break;
                    case 37: stickers[i].changePosition(41);
                        break;
                    case 38: stickers[i].changePosition(44);
                        break;
                    case 39: stickers[i].changePosition(37);
                        break;
                    case 41: stickers[i].changePosition(43);
                        break;
                    case 42: stickers[i].changePosition(36);
                        break;
                    case 43: stickers[i].changePosition(39);
                        break;
                    case 44: stickers[i].changePosition(42);
                        break;
                    case 30: stickers[i].changePosition(33);
                        break;
                    case 31: stickers[i].changePosition(34);
                        break;
                    case 32: stickers[i].changePosition(35);
                        break;
                    case 33: stickers[i].changePosition(47);
                        break;
                    case 34: stickers[i].changePosition(46);
                        break;
                    case 35: stickers[i].changePosition(45);
                        break;
                    case 47: stickers[i].changePosition(27);
                        break;
                    case 46: stickers[i].changePosition(28);
                        break;
                    case 45: stickers[i].changePosition(29);
                        break;
                    case 27: stickers[i].changePosition(30);
                        break;
                    case 28: stickers[i].changePosition(31);
                        break;
                    case 29: stickers[i].changePosition(32);
                        break;
                    }
                }
                break;
            case "F": 
                for (int i = 0; i < 54; i++) {
                    switch(stickers[i].getPosition()) {
                    case 12: stickers[i].changePosition(14);
                        break;
                    case 13: stickers[i].changePosition(23);
                        break;
                    case 14: stickers[i].changePosition(32);
                        break;
                    case 21: stickers[i].changePosition(13);
                        break;
                    case 23: stickers[i].changePosition(31);
                        break;
                    case 30: stickers[i].changePosition(12);
                        break;
                    case 31: stickers[i].changePosition(21);
                        break;
                    case 32: stickers[i].changePosition(30);
                        break;
                    case 38: stickers[i].changePosition(29);
                        break;
                    case 37: stickers[i].changePosition(20);
                        break;
                    case 36: stickers[i].changePosition(11);
                        break;
                    case 29: stickers[i].changePosition(6);
                        break;
                    case 20: stickers[i].changePosition(7);
                        break;
                    case 11: stickers[i].changePosition(8);
                        break;
                    case 6: stickers[i].changePosition(15);
                        break;
                    case 7: stickers[i].changePosition(24);
                        break;
                    case 8: stickers[i].changePosition(33);
                        break;
                    case 15: stickers[i].changePosition(38);
                        break;
                    case 24: stickers[i].changePosition(37);
                        break;
                    case 33: stickers[i].changePosition(36);
                        break;
                    }
                }
                break;
            case "B": 
                for (int i = 0; i < 54; i++) {
                    switch(stickers[i].getPosition()) {
                    case 45: stickers[i].changePosition(47);
                        break;
                    case 46: stickers[i].changePosition(50);
                        break;
                    case 47: stickers[i].changePosition(53);
                        break;
                    case 48: stickers[i].changePosition(46);
                        break;
                    case 50: stickers[i].changePosition(52);
                        break;
                    case 51: stickers[i].changePosition(45);
                        break;
                    case 52: stickers[i].changePosition(48);
                        break;
                    case 53: stickers[i].changePosition(51);
                        break;
                    case 42: stickers[i].changePosition(35);
                        break;
                    case 43: stickers[i].changePosition(26);
                        break;
                    case 44: stickers[i].changePosition(17);
                        break;
                    case 35: stickers[i].changePosition(2);
                        break;
                    case 26: stickers[i].changePosition(1);
                        break;
                    case 17: stickers[i].changePosition(0);
                        break;
                    case 2: stickers[i].changePosition(9);
                        break;
                    case 1: stickers[i].changePosition(18);
                        break;
                    case 0: stickers[i].changePosition(27);
                        break;
                    case 9: stickers[i].changePosition(42);
                        break;
                    case 18: stickers[i].changePosition(43);
                        break;
                    case 27: stickers[i].changePosition(44);
                        break;
                    }
                }
                break;
            }
    }
}
