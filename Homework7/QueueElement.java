public class QueueElement implements Comparable<QueueElement> {
    private Model model;
    private double time;
    private int discreteTime;
    private String type;
    private String[] input;

    public QueueElement(Model m, double t, int dt, String tp) {
        model = m;
        time = t;
        discreteTime = dt;
        type = tp;
        input = null;
    }

    public QueueElement(Model m, double t, int dt, String tp, String[] i) {
        model = m;
        time = t;
        discreteTime = dt;
        type = tp;
        input = i;
    }

    public double getTime() {
        return time;
    }

    public int getDiscreteTime() {
        return discreteTime;
    }

    public String getType() {
        return type;
    }

    public String[] getInput() {
        return input;
    }

    public Model getModel() {
        return model;
    }

    public int compareTo(QueueElement x) {
        if (time < x.getTime()) {
            return -1;
        } else if (time == x.getTime()) {
            if (discreteTime < x.getDiscreteTime())
                return -1;
            else if (discreteTime == x.getDiscreteTime()) 
                return 0;
        }
        return 1;
    }
}
