package granite.engine.entities;

import granite.engine.core.IDestroyable;
import granite.engine.model.Mesh;
import granite.engine.model.Model;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityManager implements IDestroyable {

    private List<EntityOld> entities = new ArrayList<>();
    private Map<Mesh, List<EntityOld>> meshes = new ConcurrentHashMap<>();

    public EntityOld addEntity(Model model, Vector3f position, Vector3f rotation, float scale) {
        EntityOld entity = new EntityOld(model, position, rotation, scale);
        entities.add(entity);
        for (Mesh mesh : entity.getModel().getMeshes()) {
            meshes.putIfAbsent(mesh, new LinkedList<>());
            meshes.get(mesh).add(entity);
        }
        return entity;
    }

    public List<EntityOld> getEntities() {
        return entities;
    }

    public Map<Mesh, List<EntityOld>> getMeshes() {
        return meshes;
    }

    @Override
    public void destroy() {
        entities.clear();
        // TODO: clear meshes
    }

}