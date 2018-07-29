package granite.engine;

import granite.engine.core.TimeManager;
import granite.engine.display.DisplayManager;
import granite.engine.entities.Camera;
import granite.engine.entities.EntityManager;
import granite.engine.input.Input;
import granite.engine.model.ModelManager;
import granite.engine.rendering.Renderer;
import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Engine {

    private static Engine instance;
    private TimeManager timeManager;
    private DisplayManager displayManager;
    private Input input;
    private Renderer renderer;
    private ModelManager modelManager;
    private EntityManager entityManager;
    private Camera camera;

    public static Engine getInstance() {
        if (instance == null) {
            instance = new Engine();
        }
        return instance;
    }

    private Engine() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        timeManager = new TimeManager();
        displayManager = new DisplayManager("GRANITE", 800, 600, false);
        modelManager = new ModelManager();
        input = new Input();
        renderer = new Renderer();
        entityManager = new EntityManager();
        camera = new Camera();
        glEnable(GL_DEPTH_TEST);
    }

    public void start() {
        getTimeManager().attach();
        getDisplayManager().attach();
        getInput().attach();
        getModelManager().attach();
        getRenderer().attach();
        getEntityManager().attach();
        getCamera().attach();

        while (!getDisplayManager().isCloseRequested()) {
            getTimeManager().update();
            getDisplayManager().update();
            getInput().update();
            getModelManager().update();
            getEntityManager().update();
            getCamera().update();
            getRenderer().update();
            if (getInput().isPressed(GLFW_KEY_ESCAPE)) getDisplayManager().setCloseRequested(true);
        }

        destroy();
    }

    public void destroy() {
        getRenderer().destroy();
        getCamera().destroy();
        getInput().destroy();
        getModelManager().destroy();
        getEntityManager().destroy();
        getDisplayManager().destroy();
        getTimeManager().destroy();
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

    public Renderer getRenderer() {
        return renderer;
    }

    public ModelManager getModelManager() {
        return modelManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Camera getCamera() {
        return camera;
    }
}