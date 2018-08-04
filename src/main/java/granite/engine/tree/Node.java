package granite.engine.tree;

import java.util.Collection;
import java.util.HashSet;

public class Node<T extends Node<T>> implements INode<T> {

    private T parent = null;
    private Collection<T> children = new HashSet<>();
    private Collection<T> descendants = new HashSet<>();

    @Override
    public T getParent() {
        return parent;
    }

    @Override
    public void setParent(T parent) {
        this.parent = parent;
    }

    @Override
    public Collection<T> getChildren() {
        return children;
    }

    @Override
    public void addChild(T child) {
        children.add(child);
        addDescendant(child);
        child.getDescendants().forEach(this::addDescendant);
        //child.setParent(this);
    }

    @Override
    public void removeChild(T child) {
        children.remove(child);
        removeDescendant(child);
        child.getDescendants().forEach(this::removeDescendant);
        child.setParent(null);
    }

    @Override
    public Collection<T> getDescendants() {
        return descendants;
    }

    @Override
    public void addDescendant(T descendant) {
        descendants.add(descendant);
        getParent().addDescendant(descendant);
    }

    @Override
    public void removeDescendant(T descendant) {
        descendants.remove(descendant);
        getParent().removeDescendant(descendant);
    }


}
