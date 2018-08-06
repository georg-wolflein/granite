package granite.engine;

import granite.engine.core.TimeManager;
import granite.engine.display.DisplayManager;
import granite.engine.entities.Camera;
import granite.engine.entities.Light;
import granite.engine.entities.Scene;
import granite.engine.input.Input;
import granite.engine.model.Color;
import granite.engine.rendering.MasterRenderer;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glEnable;

public class Engine {

    private TimeManager timeManager;
    private DisplayManager displayManager;
    private Input input;
    private MasterRenderer renderer;
    private Scene scene;
    private IGame game;

    public Engine(IGame game) {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        timeManager = new TimeManager(50);
        displayManager = new DisplayManager("GRANITE", 800, 600, false);
        input = new Input();
        renderer = new MasterRenderer();

        scene = new Scene();
        scene.setCamera(new Camera());
        Light light = new Light(new Color(1, 1, 1));
        light.move(new Vector3f(15, 0, 20));
        scene.setLight(light);

        this.game = game;
        glEnable(GL_DEPTH_TEST);
    }

    public void start() {
        getTimeManager().attach(this);
        getDisplayManager().attach(this);
        getInput().attach(this);
        getRenderer().attach(this);
        getScene().attach(this);
        game.attach(this);

        while (!getDisplayManager().isCloseRequested()) {
            getTimeManager().update(this);
            getDisplayManager().update(this);
            getScene().update(this);
            getRenderer().update(this);
            game.update(this);
            getInput().update(this);
            if (getInput().isPressed(GLFW_KEY_ESCAPE)) getDisplayManager().setCloseRequested(true);
        }

        destroy();
    }

    public void destroy() {
        getRenderer().destroy();
        getScene().destroy();
        getInput().destroy();
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

    public Scene getScene() {
        return scene;
    }

    public IGame getGame() {
        return game;
    }
}