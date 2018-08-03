package granite.engine.entities;

import granite.engine.Engine;
import granite.engine.core.IEngineObject;
import granite.engine.model.Mesh;
import granite.engine.model.Model;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityManager implements IEngineObject {

    private List<Entity> entities = new ArrayList<>();
    private Map<Mesh, List<Entity>> meshes = new ConcurrentHashMap<>();

    public Entity addEntity(Model model, Vector3f position, Vector3f rotation, float scale) {
        Entity entity = new Entity(model, position, rotation, scale);
        entities.add(entity);
        for (Mesh mesh : entity.getModel().getMeshes()) {
            meshes.putIfAbsent(mesh, new LinkedList<>());
            meshes.get(mesh).add(entity);
        }
        return entity;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public Map<Mesh, List<Entity>> getMeshes() {
        return meshes;
    }

    @Override
    public void destroy() {
        entities.clear();
        // TODO: clear meshes
    }

    @Override
    public void attach(Engine engine) {

    }

    @Override
    public void update(Engine engine) {

    }

}