package granite.engine.core;

public interface IManager<K, V extends IDestroyable> extends IDestroyable {

    K add(V item);

    V get(K id);

}