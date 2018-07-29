package granite.engine;

import granite.engine.core.TimeManager;
import granite.engine.display.DisplayManager;
import granite.engine.entities.Camera;
import granite.engine.entities.EntityManager;
import granite.engine.input.Input;
import granite.engine.model.ModelManager;
import granite.engine.rendering.Renderer;
import org.lwjgl.glfw.*;

import java.awt.font.ImageGraphicAttribute;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Engine {

    private TimeManager timeManager;
    private DisplayManager displayManager;
    private Input input;
    private Renderer renderer;
    private ModelManager modelManager;
    private EntityManager entityManager;
    private Camera camera;
    private IGame game;

    public Engine(IGame game) {
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
        this.game = game;
        glEnable(GL_DEPTH_TEST);
    }

    public void start() {
        getTimeManager().attach(this);
        getDisplayManager().attach(this);
        getInput().attach(this);
        getModelManager().attach(this);
        getRenderer().attach(this);
        getEntityManager().attach(this);
        getCamera().attach(this);
        game.attach(this);

        while (!getDisplayManager().isCloseRequested()) {
            getTimeManager().update(this);
            getDisplayManager().update(this);
            getModelManager().update(this);
            getEntityManager().update(this);
            getCamera().update(this);
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
        getInput().destroy();
        getModelManager().destroy();
        getEntityManager().destroy();
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