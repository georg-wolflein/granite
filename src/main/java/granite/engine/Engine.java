package granite.engine;

import granite.engine.core.TimeManager;
import granite.engine.display.DisplayManager;
import granite.engine.entities.Camera;
import granite.engine.entities.Light;
import granite.engine.input.Input;
import granite.engine.model.ModelManager;
import granite.engine.rendering.MasterRenderer;
import org.joml.Vector3f;
import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Engine {

    private TimeManager timeManager;
    private DisplayManager displayManager;
    private Input input;
    private MasterRenderer renderer;
    private ModelManager modelManager;
    private Camera camera;
    private Light light;
    private IGame game;

    public Engine(IGame game) {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        timeManager = new TimeManager(50);
        displayManager = new DisplayManager("GRANITE", 800, 600, false);
        modelManager = new ModelManager();
        input = new Input();
        renderer = new MasterRenderer();
        camera = new Camera();
        light = new Light(new Vector3f(15, 0, 20), new Vector3f(1, 1, 1));
        this.game = game;
        glEnable(GL_DEPTH_TEST);
    }

    public void start() {
        getTimeManager().attach(this);
        getDisplayManager().attach(this);
        getInput().attach(this);
        getModelManager().attach(this);
        getRenderer().attach(this);
        getCamera().attach(this);
        getLight().attach(this);
        game.attach(this);

        while (!getDisplayManager().isCloseRequested()) {
            getTimeManager().update(this);
            getDisplayManager().update(this);
            getModelManager().update(this);
            getCamera().update(this);
            getLight().update(this);
            getRenderer().update(this);
            game.update(this);
            getInput().update(this);
            if (getInput().isPressed(GLFW_KEY_ESCAPE)) getDisplayManager().setCloseRequested(true);
        }

        destroy();
    }

    public void destroy() {
        getRenderer().destroy();
        getCamera().destroy();
        getLight().destroy();
        getInput().destroy();
        getModelManager().destroy();
        getDisplayManager().destroy();
        getTimeManager().destroy();
        game.destroy();
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public TimeManager getTimeManager() {
        return timeManager;
    }

    public DisplayManager getDisplayManager() {
        return displayManager;
    }

    public Input getInput() {
        return input;
    }

    public MasterRenderer getRenderer() {
        return renderer;
    }

    public ModelManager getModelManager() {
        return modelManager;
    }

    public Camera getCamera() {
        return camera;
    }

    public Light getLight() {
        return light;
    }
}