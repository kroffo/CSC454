public class QueueElement implements Comparable<QueueElement> {
    private Model model;
    private double time;

    public QueueElement(Model m, double t) {
        model = m;
        time = t;
    }

    public double getTime() {
        return time;
    }

    public Model getModel() {
        return model;
    }

    public int compareTo(QueueElement x) {
        if (time < x.getTime()) {
            return -1;
        } else if (time == x.getTime()) {
            return 0;
        }
        return 1;
    }
}
