package granite.test;

import granite.engine.Engine;
import org.junit.Test;

public class EngineTest {

    @Test
    public void runEngine() {
        Engine.getInstance().start();
    }
}
