package granite.engine.entities;

import granite.engine.Engine;
import granite.engine.core.IEngineObject;
import granite.engine.model.Model;
import granite.engine.model.Texture;
import granite.engine.model.VAO;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class EntityManager implements IEngineObject {

    private List<Entity> entities = new ArrayList<>();

    public Entity addEntity(Model model, Vector3f position, Vector3f rotation, float scale) {
        Entity entity = new Entity(model, position, rotation, scale);
        entities.add(entity);
        return entity;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    @Override
    public void destroy() {
        entities.clear();
    }

    @Override
    public void attach(Engine engine) {

    }

    @Override
    public void update(Engine engine) {

    }

}