package granite.engine.rendering;

import granite.engine.Engine;
import granite.engine.core.IEngineObject;
import granite.engine.entities.Entity;
import granite.engine.model.Model;
import granite.engine.shaders.StaticShader;
import granite.engine.util.MathUtil;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

public class Renderer implements IEngineObject {

    private static final float FIELD_OF_VIEW = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;

    private Matrix4f projectionMatrix;
    StaticShader staticShader;

    @Override
    public void attach() {
        //glClearColor(0f, 1f, 0f, 1f);
        glClear(GL_COLOR_BUFFER_BIT);
        projectionMatrix = MathUtil.createProjectionMatrix(Engine.getInstance().getDisplayManager().getWidth(),
                Engine.getInstance().getDisplayManager().getHeight(), FIELD_OF_VIEW, NEAR_PLANE, FAR_PLANE);
        staticShader = new StaticShader();
        staticShader.bind();
        staticShader.loadProjectionMatrix(projectionMatrix);
        staticShader.unbind();
    }

    @Override
    public void update() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
        staticShader.bind();
        staticShader.loadViewMatrix(Engine.getInstance().getCamera());
        for (Entity entity : Engine.getInstance().getEntityManager().getEntities()) {
            render(entity, staticShader);
        }
        staticShader.unbind();
    }

    public void render(Entity entity, StaticShader shader) {
        Model model = entity.getModel();

        model.getVao().bind();
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        Matrix4f transformationMatrix = MathUtil.createTransformationMatrix(entity.getPosition(),
                entity.getRotation(), entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);

        glActiveTexture(GL_TEXTURE0);
        model.getTexture().bind();
        glDrawElements(GL_TRIANGLES, model.getVao().getVertexCount(), GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
    }

    @Override
    public void destroy() {
        staticShader.destroy();
    }

    public StaticShader getShader() {
        return staticShader;
    }
}
