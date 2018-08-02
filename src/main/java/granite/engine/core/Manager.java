package granite.engine.core;

import java.util.HashMap;
import java.util.Map;

public abstract class Manager<K, V extends IDestroyable> implements IManager<K, V> {

    private Map<K, V> items = new HashMap<>();

    protected final K add(K key, V item) {
        items.put(key, item);
        return key;
    }

    @Override
    public final V get(K id) {
        return items.get(id);
    }

    @Override
    public void destroy() {
        for (V item : items.values()) {
            item.destroy();
        }
    }
}
