package granite.engine.entities;

import granite.engine.model.Mesh;
import granite.engine.model.Model;
import granite.engine.tree.INode;
import granite.engine.tree.Node;
import granite.engine.util.CachedValue;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

public class Entity extends Node<Entity> {

    private Map<Mesh, Collection<Model>> meshes = new HashMap<>();
    private Vector3f translation = new Vector3f(0, 0, 0);
    private Vector3f rotation = new Vector3f(0, 0, 0);
    private Vector3f scale = new Vector3f(1, 1, 1);
    private CachedValue<Matrix4f> absoluteTransformation = new CachedValue<>(() -> getParent() == null ? new Matrix4f(getRelativeTransformation()) : new Matrix4f(getParent().getAbsoluteTransformation()).mul(getRelativeTransformation()));

    @Override
    public Entity getParent() {
        return (Entity) super.getParent();
    }

    @Override
    public void setParent(INode<Entity> parent) {
        super.setParent(parent);
        absoluteTransformation.invalidate();
        getDescendants().forEach(d -> d.absoluteTransformation.invalidate());
    }

    public Matrix4f getRelativeTransformation() {
        return new Matrix4f().scale(scale).rotateXYZ(rotation).translate(translation);
    }

    public Matrix4f getAbsoluteTransformation() {
        return new Matrix4f(absoluteTransformation.get());
    }

    public void move(Vector3f offset) {
        translation.add(offset);
        absoluteTransformation.invalidate();
        getDescendants().forEach(d -> d.absoluteTransformation.invalidate());
    }

    public void rotate(Vector3f offset) {
        rotation.add(offset);
        absoluteTransformation.invalidate();
        getDescendants().forEach(d -> d.absoluteTransformation.invalidate());
    }

    public void scale(Vector3f offset) {
        scale.mul(offset);
        absoluteTransformation.invalidate();
        getDescendants().forEach(d -> d.absoluteTransformation.invalidate());
    }

    public Map<Mesh, Collection<Model>> getMeshesMap() {
        return meshes;
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
