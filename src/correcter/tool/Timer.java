package correcter.tool;

public class Timer {
    private long start;
    private long end;
    
    public Timer() {
        start = 0L;
        end = 0L;
    }
    
    public void start() {
        start = System.currentTimeMillis();
    }
    
    public void stop() {
        end = System.currentTimeMillis();
    }
    
    public int get() {
        return (int) (end - start);
    }
}
