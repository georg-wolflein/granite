package granite.engine.entities;

import granite.engine.Engine;
import granite.engine.core.IEngineObject;
import granite.engine.input.Input;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Camera implements IEngineObject {

    private Vector3f position = new Vector3f(0f, 0f, 0f);
    private float pitch = 0, yaw = 0, roll = 0;

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    @Override
    public void attach() {

    }

    @Override
    public void update() {
        Input input = Engine.getInstance().getInput();
        if (input.isPressed(GLFW_KEY_W)) {
            position.z -= .02f;
        }
        if (input.isPressed(GLFW_KEY_A)) {
            position.x -= .02f;
        }
        if (input.isPressed(GLFW_KEY_S)) {
            position.z += .02f;
        }
        if (input.isPressed(GLFW_KEY_D)) {
            position.x += .02f;
        }
        if (input.isPressed(GLFW_KEY_UP)) {
            pitch -= 1f;
        }
        if (input.isPressed(GLFW_KEY_DOWN)) {
            pitch += 1f;
        }
        if (input.isPressed(GLFW_KEY_LEFT)) {
            yaw -= 1f;
        }
        if (input.isPressed(GLFW_KEY_RIGHT)) {
            yaw += 1f;
        }
    }

    @Override
    public void destroy() {

    }
}
