package granite.engine.core;

public interface IEngineObject extends IDestroyable {

    void attach();

    /**
     * Should be executed in the main loop.
     */
    void update();

}
