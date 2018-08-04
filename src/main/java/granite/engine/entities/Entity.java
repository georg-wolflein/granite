package granite.engine.entities;

import granite.engine.model.Mesh;
import granite.engine.model.Model;
import granite.engine.tree.Node;
import org.joml.Vector3f;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

public class Entity extends Node<Entity> {

    private Vector3f position;
    private Vector3f rotation;
    private Map<Mesh, Collection<Model>> meshes = new HashMap<>();

    public Entity(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Entity() {
        this(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));
    }

    @Override
    public Entity getParent() {
        return (Entity) super.getParent();
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
            return getPosition();
        } else {
            return new Vector3f(getPosition()).add(getParent().getAbsolutePosition());
        }
    }

    @Override
    public void addDescendants(Collection<Entity> descendants) {
        super.addDescendants(descendants);
        descendants.forEach(descendant -> {
            if (descendant instanceof Model) {
                Model model = (Model) descendant;
                for (Mesh mesh : model.getMeshes()) {
                    meshes.putIfAbsent(mesh, new HashSet<>());
                    meshes.get(mesh).add(model);
                }
            }
        });
    }

    @Override
    public void removeDescendants(Collection<Entity> descendants) {
        super.removeDescendants(descendants);
        Collection<Model> models = descendants.parallelStream().filter(d -> d instanceof Model).map(d -> (Model) d).collect(Collectors.toSet());
        meshes.values().forEach(m -> m.removeAll(models));
    }
}
