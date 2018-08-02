package granite.engine.model;

import granite.engine.Engine;
import granite.engine.core.IEngineObject;
import granite.engine.core.Manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModelManager extends Manager<String, Model> implements IEngineObject {

    private List<Model> models = new ArrayList<>();

    public List<Model> getModels() {
        return models;
    }

    public Model load(String name, String file) throws IOException {
        Model model = new Model(name);
        model.load(file);
        add(model);
        return model;
    }

    @Override
    public String add(Model item) {
        return add(item.getName(), item);
    }

    @Override
    public void attach(Engine engine) {

    }

    @Override
    public void update(Engine engine) {

    }
}
