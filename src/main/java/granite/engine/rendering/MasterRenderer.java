package granite.engine.rendering;

import granite.engine.Engine;
import granite.engine.core.Constants;
import granite.engine.core.IEngineObject;
import granite.engine.util.MathUtil;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;

public class MasterRenderer implements IEngineObject {

    private EntityRenderer entityRenderer;
    private Matrix4f projectionMatrix;

    private void attachRenderer(IRenderer renderer) {
        renderer.attach(this);
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
        entityRenderer.render(engine.getCamera(), engine.getLight());
    }

    @Override
    public void destroy() {

    }
}
