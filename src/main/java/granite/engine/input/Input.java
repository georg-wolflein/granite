package granite.engine.input;

import granite.engine.Engine;
import granite.engine.core.IEngineObject;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;

public class Input implements IEngineObject {

    private Set<Integer> pressed = new HashSet<>();

    public Input() {

    }

    public boolean isPressed(int key) {
        return pressed.contains(key);
    }

    @Override
    public void attach() {
        glfwSetKeyCallback(Engine.getInstance().getDisplayManager().getWindowId(), (window, key, scancode, action, mods) -> {
            switch (action) {
                case GLFW_PRESS:
                    pressed.add(key);
                    break;
                case GLFW_RELEASE:
                    pressed.remove(key);
                    break;
            }
        });
    }

    @Override
    public void update() {

    }

    @Override
    public void destroy() {

    }
}
