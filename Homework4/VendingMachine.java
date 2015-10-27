public class VendingMachine implements Model {
    
    private int q=0,d=0,n=0,value=0;

    // Decrements v to be between 0 and 100
    // Outputs the number of '100's dropped.
    private int adjustValue(int v) {
        int count = 0;
        while (v >= 100) {
            v -= 100;
            count += 1;
        }
        return count;
    }

    public boolean validInput(String input) {
        return input.equals("q") || input.equals("d") || input.equals("n");
    }

    // returns 2.0 if value > 0
    // returns maxInt otherwise
    public double timeAdvance() {
        if (value > 0) {
            return 2.0;
        } else {
            return Integer.MAX_VALUE;
        }
    }
    
    public void externalTransition (String input) {
        switch (input) {
        case "q": 
            q++;
            value += 25;
            break;
        case "d":
            d++;
            value += 10;
            break;
        case "n":
            n++;
            value +=5;
            break;
        default: ;
        }
    }

    public void internalTransition() {
        value -= 100 * adjustValue(value);
        removeChange();
    }

    public void confluentTransition (String input) {
        internalTransition();
        externalTransition(input);
    }

    public String output() {
        int numberOfCoffees = adjustValue(value);
        String[] coins = getChange();
        String output = "";
        for (int i = 0; i < numberOfCoffees; i++) {
            output = output + "Coffee ";
        }
        for (int i = 0; i < coins.length; i++) {
            output = output + coins[i] + " ";
        }
        return "Output: " + output;
    }

    // removes coins to try to set value to 0.
    private void removeChange() {
        int calcValue = value - 100*adjustValue(value), calcQ = 0, calcD = 0, calcN = 0;
        while (calcValue / 25 > 0 && q - calcQ > 0) {
            calcQ++;
            calcValue -= 25;
        }
        while (calcValue / 10 > 0 && d - calcD > 0) {
            calcD++;
            calcValue -= 10;
        }
        while (calcValue / 5 > 0 && n - calcN > 0) {
            calcN++;
            calcValue -= 5;
        }
        // If we are missing a nickel, remove a quarter and add 3 dimes (if possible).
        // If we are missing more than a nickel, then we must have run out of dimes so this won't be applicable anyway.
        if (calcValue == 5 && d - calcD >= 3 && calcQ > 0) {
            calcQ--;
            calcD += 3;
            calcValue -= 5;
        }
        // If we couldn't make exact change, we take a hit and give extra money back. After all, it's our fault for not stocking enough change.
        if (calcValue > 0) {
            if (d - calcD > 0) {
                calcD++;
                calcValue -= 10;
            } else if (q - calcQ > 0) {
                calcQ++;
                calcValue -= 25;
            }
        }
        q -= calcQ;
        d -= calcD;
        n -= calcN;
        value = calcValue;
        if (value < 0) {
            value = 0;
        }
    }

    // returns array of coins (Strings) representing the coins to remove
    private String[] getChange() {
        int calcValue = value - 100*adjustValue(value), calcQ = 0, calcD = 0, calcN = 0;
        while (calcValue / 25 > 0 && q - calcQ > 0) {
            calcQ++;
            calcValue -= 25;
        }
        while (calcValue / 10 > 0 && d - calcD > 0) {
            calcD++;
            calcValue -= 10;
        }
        while (calcValue / 5 > 0 && n - calcN > 0) {
            calcN++;
            calcValue -= 5;
        }
        // If we are missing a nickel, remove a quarter and add 3 dimes (if possible).
        // If we are missing more than a nickel, then we must have run out of dimes so this won't be applicable anyway.
        if (calcValue == 5 && d - calcD >= 3 && calcQ > 0) {
            calcQ--;
            calcD += 3;
            calcValue += 5;
        }
        // If we couldn't make exact change, we take a hit and give extra money back. After all, it's our fault for not stocking enough change.
        if (calcValue > 0) {
            if (d - calcD > 0) {
                calcD++;
            } else if (q - calcQ > 0) {
                calcQ++;
            }
        }
        String[] change = new String[calcQ + calcD + calcN];
        for (int i = 0; i < calcQ; i++) {
            change[i] = "q";
        }
        for (int i = calcQ; i < calcQ + calcD; i++) {
            change[i] = "d";
        }
        for (int i = calcQ + calcD; i < change.length; i++) {
            change[i] = "n";
        }
        return change;
    }
}
