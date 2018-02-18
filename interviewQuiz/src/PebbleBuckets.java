import java.util.List;

public class PebbleBuckets {

    public static int RED = 1;
    public static int BLUE = 2;
    public static int WHITE = 3;

    private List<String> buckets;

    private int swapCount;
    private int colorCount;


    public PebbleBuckets(List<String> buckets) {
        this.buckets = buckets;
    }

    public int getSwapCount() {
        return swapCount;
    }

    public int getColorCount() {
        return colorCount;
    }

    public int color(int i) {
        ++colorCount;
        String pebble = buckets.get(i);
        if (pebble.equals("R")) {
            return RED;
        } else if (pebble.equals("B")) {
            return BLUE;
        }
        return WHITE;
    }

    public void swap(int i, int j) {
        String pebble1 = buckets.get(i);
        buckets.set(i, buckets.get(j));
        buckets.set(j, pebble1);
        ++swapCount;
    }

    public int size() {
        return buckets.size();
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(buckets.size());
        stringBuilder.append(buckets.get(0));
        for(int i = 1; i < buckets.size(); i++) {
            stringBuilder.append(" ");
            stringBuilder.append(buckets.get(i));
        }
        return stringBuilder.toString();
    }
}
