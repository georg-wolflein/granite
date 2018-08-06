package granite.engine.entities;

import granite.engine.tree.INode;
import granite.engine.tree.Node;
import granite.engine.util.CachedValue;
import granite.engine.util.math.MathUtil;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class SpatialNode extends Node<SpatialNode> {

    private Vector3f translation = new Vector3f(0, 0, 0);
    private Vector3f rotation = new Vector3f(0, 0, 0);
    private Vector3f scale = new Vector3f(1, 1, 1);
    private CachedValue<Matrix4f> absoluteTransformation = new CachedValue<>(() -> getParent() == null ? new Matrix4f(getRelativeTransformation()) : new Matrix4f(getParent().getAbsoluteTransformation()).mul(getRelativeTransformation()));

    @Override
    public SpatialNode getParent() {
        return (SpatialNode) super.getParent();
    }

    @Override
    public void setParent(INode<SpatialNode> parent) {
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

    public Vector3f getAbsolutePosition() {
        return MathUtil.getPositionFromTransformationMatrix(getAbsoluteTransformation());
    }
}
