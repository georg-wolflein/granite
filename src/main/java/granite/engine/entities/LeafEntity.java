package granite.engine.entities;

import java.util.Collection;

public abstract class LeafEntity extends SpatialNode {

    @Override
    public void addChild(SpatialNode child) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeChild(SpatialNode child) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addDescendants(Collection<SpatialNode> descendants) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeDescendants(Collection<SpatialNode> descendants) {
        throw new UnsupportedOperationException();
    }
}
