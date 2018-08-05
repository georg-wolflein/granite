package granite.engine.rendering;

import granite.engine.Engine;
import granite.engine.core.Constants;
import granite.engine.core.IEngineObject;
import granite.engine.model.Mesh;
import granite.engine.model.Model;
import granite.engine.util.MathUtil;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

public class MasterRenderer implements IEngineObject {

    private EntityRenderer entityRenderer;
    private Matrix4f projectionMatrix;
    private Collection<IRenderer> renderers = new ArrayList<>();

    private void attachRenderer(BatchRenderer renderer) {
        renderer.attach(this);
        renderers.add(renderer);
    }

    public Collection<IRenderer> getRenderers() {
        return renderers;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public EntityRenderer getEntityRenderer() {
        return entityRenderer;
    }

    @Override
    public void attach(Engine engine) {
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glClearColor(0f, 0f, 0f, 1f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
        projectionMatrix = MathUtil.createProjectionMatrix(engine.getDisplayManager().getWidth(),
                engine.getDisplayManager().getHeight(), Constants.FIELD_OF_VIEW, Constants.NEAR_PLANE, Constants.FAR_PLANE);
        // Create renderers
        entityRenderer = new EntityRenderer();
        attachRenderer(entityRenderer);
    }

    @Override
    public void update(Engine engine) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
        for (Map.Entry<IRenderer, Map<Mesh, Collection<Model>>> entry : engine.getScene().getRenderableMeshesMap().entrySet()) {
            IRenderer renderer = entry.getKey();
            for (Map.Entry<Mesh, Collection<Model>> renderEntry : entry.getValue().entrySet()) {
                renderer.renderBatch(renderEntry.getKey(), renderEntry.getValue(), engine.getCamera(), engine.getLight());
            }
        }
    }

    @Override
    public void destroy() {

    }
}
