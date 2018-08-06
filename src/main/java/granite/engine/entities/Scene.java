package granite.engine.entities;

import granite.engine.Engine;
import granite.engine.core.IEngineObject;
import granite.engine.model.Mesh;
import granite.engine.model.Model;
import granite.engine.rendering.IRenderer;
import granite.engine.tree.INode;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Scene extends Entity implements IEngineObject {

    private Map<IRenderer, Map<Mesh, Collection<Model>>> renderableMeshesMap = new HashMap<>();
    private Camera camera;
    private Light light;

    public Map<IRenderer, Map<Mesh, Collection<Model>>> getRenderableMeshesMap() {
        return renderableMeshesMap;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    @Override
    public void setParent(INode<SpatialNode> parent) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void attach(Engine engine) {

    }

    @Override
    public void update(Engine engine) {
        renderableMeshesMap.clear();
        for (IRenderer renderer : engine.getRenderer().getRenderers()) {
            renderableMeshesMap.put(renderer, new HashMap<>());
        }
        for (Map.Entry<Mesh, Collection<Model>> entry : getMeshesMap().entrySet()) {
            renderableMeshesMap.get(entry.getKey().getRenderer()).put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void destroy() {

    }
}
