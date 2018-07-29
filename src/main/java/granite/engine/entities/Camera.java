package granite.engine.entities;

import granite.engine.Engine;
import granite.engine.core.IEngineObject;
import granite.engine.input.Input;
import granite.engine.model.Model;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Camera extends Object3D implements IEngineObject {


    public Camera() {
        super(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return getRotation().x();
    }

    public float getYaw() {
        return getRotation().y();
    }

    public float getRoll() {
        return getRotation().z();
    }

    @Override
    public void attach(Engine engine) {

    }

    @Override
    public void update(Engine engine) {

    }

    @Override
    public void destroy() {

    }
}
