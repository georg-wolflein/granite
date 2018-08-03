package granite.engine.rendering;

import com.mokiat.data.front.parser.MTLColor;
import granite.engine.Engine;
import granite.engine.core.IEngineObject;
import granite.engine.entities.Entity;
import granite.engine.model.Mesh;
import granite.engine.model.Model;
import granite.engine.model.RawMesh;
import granite.engine.shaders.StaticRawShader;
import granite.engine.util.MathUtil;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class Renderer implements IEngineObject {

    private static final float FIELD_OF_VIEW = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;

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
        for (Entity entity : engine.getEntityManager().getEntities()) {
            render(entity, engine, staticRawShader);
        }
    }

    public void render(Entity entity, Engine engine, StaticRawShader shader) {
        shader.bind();
        shader.loadViewMatrix(engine.getCamera());
        Model model = entity.getModel();
        for (Mesh mesh : model.getMeshes()) {
            if (mesh instanceof RawMesh) {
                mesh.bind();
                mesh.enableArrays();
                Matrix4f transformationMatrix = MathUtil.createTransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScale());
                MTLColor color = mesh.getMaterial().getDiffuseColor();
                shader.loadDiffuseColor(new Vector3f(color.r, color.g, color.b));
                shader.loadTransformationMatrix(transformationMatrix);
                shader.loadLight(engine.getLight());
                glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);
                mesh.disableArrays();
                mesh.unbind();
            }
        }

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
