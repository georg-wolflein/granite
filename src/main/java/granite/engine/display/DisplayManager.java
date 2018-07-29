package granite.engine.display;

import granite.engine.Engine;
import granite.engine.core.IEngineObject;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

public class DisplayManager implements IEngineObject {

    private long id;
    private int width;
    private int height;

    public DisplayManager(String title, int width, int height, boolean resizeable) {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, resizeable ? GLFW_TRUE : GLFW_FALSE);

        this.width = width;
        this.height = height;

        id = glfwCreateWindow(width, height, title, NULL, NULL);
        if (id == NULL) {
            throw new RuntimeException("Failed to create window");
        }

        glfwMakeContextCurrent(id);
        GL.createCapabilities();

        glfwSwapInterval(1);
    }

    public boolean isCloseRequested() {
        return glfwWindowShouldClose(getWindowId());
    }

    public void setCloseRequested(boolean value) {
        glfwSetWindowShouldClose(getWindowId(), value);
    }

    @Override
    public void attach(Engine engine) {
        glfwShowWindow(getWindowId());
    }

    @Override
    public void update(Engine engine) {
        glfwSwapBuffers(getWindowId());
        glfwPollEvents();
    }

    @Override
    public void destroy() {
        glfwFreeCallbacks(getWindowId());
        glfwDestroyWindow(getWindowId());
    }

    public long getWindowId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
