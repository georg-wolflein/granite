package granite.engine.input;

import granite.engine.Engine;
import granite.engine.core.IEngineObject;
import granite.engine.display.DisplayManager;
import org.joml.Vector2d;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;

public class Input implements IEngineObject {

    private Set<Integer> pressed = new HashSet<>();
    private double cursorDeltaX = 0, cursorDeltaY = 0, cursorX = 0, cursorY = 0;
    private Set<MouseButton> mousePressed = new HashSet<>();

    public Input() {

    }

    public boolean isPressed(int key) {
        return pressed.contains(key);
    }

    public boolean isMouseButtonPressed(MouseButton button) {
        return mousePressed.contains(button);
    }

    public Vector2d getCursorDelta() {
        return new Vector2d(cursorDeltaX, cursorDeltaY);
    }

    @Override
    public void attach(Engine engine) {
        DisplayManager display = engine.getDisplayManager();
        glfwSetKeyCallback(display.getWindowId(), (window, key, scancode, action, mods) -> {
            switch (action) {
                case GLFW_PRESS:
                    pressed.add(key);
                    break;
                case GLFW_RELEASE:
                    pressed.remove(key);
                    break;
            }
        });
        glfwSetCursorPosCallback(display.getWindowId(), (window, x, y) -> {
            cursorDeltaX += x - cursorX;
            cursorDeltaY += y - cursorY;
            cursorX = x;
            cursorY = y;
        });
        glfwSetMouseButtonCallback(display.getWindowId(), (window, button, action, mods) -> {
            MouseButton mouseButton;
            switch (button) {
                case GLFW_MOUSE_BUTTON_LEFT:
                    mouseButton = MouseButton.LEFT;
                    break;
                case GLFW_MOUSE_BUTTON_RIGHT:
                    mouseButton = MouseButton.RIGHT;
                    break;
                case GLFW_MOUSE_BUTTON_MIDDLE:
                    mouseButton = MouseButton.MIDDLE;
                    break;
                default:
                    return;
            }
            switch (action) {
                case GLFW_PRESS:
                    mousePressed.add(mouseButton);
                    break;
                case GLFW_RELEASE:
                    mousePressed.remove(mouseButton);
                    break;
            }
        });
    }

    @Override
    public void update(Engine engine) {
        cursorDeltaX = 0;
        cursorDeltaY = 0;
    }

    @Override
    public void destroy() {

    }
}
