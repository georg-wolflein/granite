package granite.engine.entities;

import org.joml.Vector3f;

public class Object3D {

    protected Vector3f position;
    protected Vector3f rotation;

    public Object3D(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void move(Vector3f d) {
        position.add(d);
    }

    public void rotate(Vector3f r) {
        rotation.add(r);
    }
}
