package granite.engine.model;

import granite.engine.core.Manager;

public class MaterialManager extends Manager<String, Material> {

    private String name;

    @Override
    public String add(Material item) {
        return add(item.getName(), item);
    }
}
