public class Sticker implements Comparable<Sticker> {
    private int position;
    private String color;

    public Sticker(int p, String c) {
        position = p;
        color = c;
    }

    public int getPosition() {
        return position;
    }

    public String getColor() {
        return color;
    }

    public void changePosition(int p) {
        position = p;
    }

    public int compareTo(Sticker x) {
        if (position < x.getPosition())
            return -1;
        else if (position == x.getPosition())
            return 0;
        return 1;
    }
}
