package granite.engine.entities;

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
    public void attach() {
        VAO vao = new VAO(new float[]{0f, 0f, 0f, -.5f, 0f, 0f, -.5f, -.5f, 0f, 0f, -.5f, 0f, 0f, 0f, 1f, -.5f, 0f,
                1f, -.5f, -.5f, 1f, 0f, -.5f, 1f}, new float[]{0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1,
                0, 1, 1, 0, 0, 1}, new int[]{0, 1, 2, 0, 2, 3, 0, 1, 4, 4, 1, 5, 4, 5, 6, 4, 6, 7});
        Texture texture = new Texture("texture1.png");
        Model model = new Model(vao, texture);
        addEntity(model, new Vector3f(0, 0, -1), new Vector3f(0, 0, 0), 1);
        addEntity(model, new Vector3f(0, 1, -1), new Vector3f(0, 1, 0), 1);
    }

    @Override
    public void update() {
        getEntities().get(0).rotate(new Vector3f(1, 1, 1));
        getEntities().get(1).move(new Vector3f(0f, 0f, -.01f));
    }

}