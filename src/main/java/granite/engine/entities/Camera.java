package granite.engine.entities;

import granite.engine.Engine;
import granite.engine.core.IEngineObject;
import org.joml.Vector3f;

public class Camera extends Entity implements IEngineObject {


    public Camera() {
        super(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));
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
