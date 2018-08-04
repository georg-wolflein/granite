package granite.engine.entities;

import granite.engine.model.Mesh;
import granite.engine.model.Model;
import granite.engine.tree.Node;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;

public class Entity extends Node<Entity> {

    private Vector3f position;
    private Vector3f rotation;
    private Map<Mesh, Model> meshes;

    private List<Entity> children;
    private Entity parent;

    public Entity(Vector3f position, Vector3f rotation) {
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

    public Vector3f getAbsolutePosition() {
        if (getParent() == null) {
            return new Vector3f(getPosition());
        } else {
            return new Vector3f(getPosition()).add(getParent().getAbsolutePosition());
        }
    }
}
