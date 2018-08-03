package granite.engine.rendering;

import com.mokiat.data.front.parser.MTLColor;
import granite.engine.Engine;
import granite.engine.core.IEngineObject;
import granite.engine.entities.Entity;
import granite.engine.model.Mesh;
import granite.engine.shaders.StaticRawShader;
import granite.engine.util.MathUtil;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

public class Renderer implements IEngineObject {

    private static final float FIELD_OF_VIEW = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;
    private static final float RENDER_DISTANCE = 1000;

    private Matrix4f projectionMatrix;
    StaticRawShader staticRawShader;

    @Override
    public void attach(Engine engine) {
        glClearColor(0f, 0f, 0f, 1f);
        glClear(GL_COLOR_BUFFER_BIT);
        projectionMatrix = MathUtil.createProjectionMatrix(engine.getDisplayManager().getWidth(),
                engine.getDisplayManager().getHeight(), FIELD_OF_VIEW, NEAR_PLANE, FAR_PLANE);
        staticRawShader = new StaticRawShader();
        staticRawShader.bind();
        staticRawShader.loadProjectionMatrix(projectionMatrix);
        staticRawShader.unbind();
    }

    @Override
    public void update(Engine engine) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
        for (Map.Entry<Mesh, List<Entity>> entry : engine.getEntityManager().getMeshes().entrySet()) {
            renderMesh(entry.getKey(), entry.getValue(), engine, staticRawShader);
        }
    }

    public void renderMesh(Mesh mesh, List<Entity> entities, Engine engine, StaticRawShader shader) {
        Vector3f cameraPosition = engine.getCamera().getPosition();
        shader.bind();
        shader.loadViewMatrix(engine.getCamera());
        MTLColor color = mesh.getMaterial().getDiffuseColor();
        shader.loadDiffuseColor(new Vector3f(color.r, color.g, color.b));
        shader.loadLight(engine.getLight());
        mesh.bind();
        mesh.enableArrays();
        for (Entity entity : entities) {
            if (new Vector3f(cameraPosition).sub(entity.getPosition()).length() <= RENDER_DISTANCE) {
                Matrix4f transformationMatrix = MathUtil.createTransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScale());
                shader.loadTransformationMatrix(transformationMatrix);
                glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);
            }
        }
        mesh.disableArrays();
        mesh.unbind();

//        model.getVao().bind();
//        glEnableVertexAttribArray(0);
//        glEnableVertexAttribArray(1);
//        Matrix4f transformationMatrix = MathUtil.createTransformationMatrix(entity.getPosition(),
//                entity.getRotation(), entity.getScale());
//        shader.loadTransformationMatrix(transformationMatrix);
//
//        glActiveTexture(GL_TEXTURE0);
//        model.getTexture().bind();
//        glDrawElements(GL_TRIANGLES, model.getVao().getVertexCount(), GL_UNSIGNED_INT, 0);
//        glDisableVertexAttribArray(1);
//        glDisableVertexAttribArray(0);
    }

    @Override
    public void destroy() {
        staticRawShader.destroy();
    }

    public StaticRawShader getShader() {
        return staticRawShader;
    }
}
