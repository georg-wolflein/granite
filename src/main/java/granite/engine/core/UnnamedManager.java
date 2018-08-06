package granite.engine.core;

import java.util.Random;

public class UnnamedManager<V extends IDestroyable> extends Manager<Integer, V> {

    private Random random = new Random();

    @Override
    public Integer add(V item) {
        int id;
        do {
            id = random.nextInt(1000000);
        } while (items.containsKey(id));
        add(id, item);
        return id;
    }
}
