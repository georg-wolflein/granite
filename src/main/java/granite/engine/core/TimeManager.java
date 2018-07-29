package granite.engine.core;

public class TimeManager implements IEngineObject {

    private long lastTime;
    private double delta;
    private double time;

    public double getTime() {
        return time;
    }

    public double getFps() {
        return 1 / delta;
    }

    @Override
    public void attach() {
        lastTime = System.nanoTime();
        time = 0;
        delta = 1;
    }

    @Override
    public void update() {
        long now = System.nanoTime();
        delta = (now - lastTime) / (double) Constants.NANOSECONDS_PER_SECOND;
        time += delta;
        lastTime = now;
    }

    @Override
    public void destroy() {

    }
}
