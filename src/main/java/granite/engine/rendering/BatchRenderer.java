package granite.engine.rendering;

import granite.engine.entities.Camera;
import granite.engine.entities.Entity;
import granite.engine.entities.EntityManager;
import granite.engine.entities.Light;
import granite.engine.model.Mesh;
import granite.engine.shaders.Shader3D;

import java.util.List;
import java.util.Map;

public abstract class BatchRenderer<S extends Shader3D> implements IRenderer<S> {

    private S shader;
    private EntityManager entityManager;

    public BatchRenderer(S shader, EntityManager entityManager) {
        this.shader = shader;
        this.entityManager = entityManager;
    }

    public void render(Camera camera, Light light) {
        for (Map.Entry<Mesh, List<Entity>> entry : getEntityManager().getMeshes().entrySet()) {
            renderBatch(entry.getKey(), entry.getValue(), camera, light);
        }
    }

    public abstract void renderBatch(Mesh mesh, List<Entity> entities, Camera camera, Light light);

    @Override
    public S getShader() {
        return shader;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void destroy() {
        shader.destroy();
        entityManager.destroy();
    }
}
