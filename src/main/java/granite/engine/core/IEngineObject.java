package granite.engine.core;

import granite.engine.Engine;

public interface IEngineObject extends IDestroyable {

    void attach(Engine engine);

    /**
     * Should be executed in the main loop.
     */
    void update(Engine engine);

}
