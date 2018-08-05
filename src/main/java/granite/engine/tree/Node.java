package granite.engine.tree;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Node<T extends INode<T>> implements INode<T> {

    private INode<T> parent = null;
    private Collection<T> children = new HashSet<>();
    private Collection<T> descendants = new HashSet<>();

    @Override
    public INode<T> getParent() {
        return parent;
    }

    @Override
    public void setParent(INode<T> parent) {
        this.parent = parent;
    }

    @Override
    public Collection<T> getChildren() {
        return children;
    }

    @Override
    public void addChild(T child) {
        children.add(child);
        addDescendants(List.of(child));
        addDescendants(child.getDescendants());
        child.setParent(this);
    }

    @Override
    public void removeChild(T child) {
        children.remove(child);
        removeDescendants(Stream.concat(child.getDescendants().stream(), Stream.of(child)).collect(Collectors.toList()));
        child.setParent(null);
    }

    @Override
    public Collection<T> getDescendants() {
        return descendants;
    }

    @Override
    public void addDescendants(Collection<T> descendants) {
        this.descendants.addAll(descendants);
        if (this.parent != null)
            getParent().addDescendants(descendants);
    }

    @Override
    public void removeDescendants(Collection<T> descendants) {
        this.descendants.removeAll(descendants);
        if (this.parent != null)
            getParent().removeDescendants(descendants);
    }


}
