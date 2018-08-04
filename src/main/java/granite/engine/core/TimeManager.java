package granite.engine.core;

import granite.engine.Engine;

public class TimeManager implements IEngineObject {

    private long lastTime;
    private double delta;
    private double time;
    private double targetDelta;

    public TimeManager(double targetFps) {
        this.targetDelta = 1. / targetFps;
    }

    public double getTime() {
        return time;
    }

    public double getFps() {
        return 1 / delta;
    }

    @Override
    public void attach(Engine engine) {
        lastTime = System.nanoTime();
        time = 0;
        delta = 1;
    }

    @Override
    public void update(Engine engine) {
        long now;
        do {
            now = System.nanoTime();
            delta = (now - lastTime) / (double) Constants.NANOSECONDS_PER_SECOND;
        } while (delta < targetDelta);
        time += delta;
        lastTime = now;
//        System.out.println(getFps());
    }

    @Override
    public void destroy() {

    }
}
