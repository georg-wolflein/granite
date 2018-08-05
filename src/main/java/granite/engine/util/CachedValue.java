package granite.engine.util;

public class CachedValue<T> {

    public CachedValue(CachedValueCallback<T> callback) {
        this.callback = callback;
    }

    public interface CachedValueCallback<T> {
        T update();
    }

    T value;
    CachedValueCallback<T> callback;

    boolean valid = false;

    public void invalidate() {
        valid = false;
    }

    public T get() {
        if (!valid) {
            value = callback.update();
            valid = true;
        }
        return value;
    }
}