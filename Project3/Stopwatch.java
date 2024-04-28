public class Stopwatch {
    private long start;

    public Stopwatch() {
        start = System.currentTimeMillis();
    }

    public void newStart() {
        start = System.currentTimeMillis();
    }

    public double check() {
        long now = System.currentTimeMillis();
        return (now - start);
    }
}