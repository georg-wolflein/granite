package granite.engine.entities;

import granite.engine.model.Mesh;
import granite.engine.model.Model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

public class Entity extends SpatialNode {

    private Map<Mesh, Collection<Model>> meshes = new HashMap<>();

    public Map<Mesh, Collection<Model>> getMeshesMap() {
        return meshes;
    }

    @Override
    public void addDescendants(Collection<SpatialNode> descendants) {
        super.addDescendants(descendants);
        descendants.forEach(descendant -> {
            if (descendant instanceof Model) {
                Model model = (Model) descendant;
                for (Mesh mesh : model.getMeshes()) {
                    meshes.putIfAbsent(mesh, new HashSet<>());
                    meshes.get(mesh).add(model);
                }
            }
        });
    }

    @Override
    public void removeDescendants(Collection<SpatialNode> descendants) {
        super.removeDescendants(descendants);
        Collection<Model> models = descendants.parallelStream().filter(d -> d instanceof Model).map(d -> (Model) d).collect(Collectors.toSet());
        meshes.values().forEach(m -> m.removeAll(models));
    }
}
