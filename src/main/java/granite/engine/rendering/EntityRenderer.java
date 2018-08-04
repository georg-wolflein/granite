package granite.engine.rendering;

import granite.engine.entities.Camera;
import granite.engine.entities.Entity;
import granite.engine.entities.EntityManager;
import granite.engine.entities.Light;
import granite.engine.model.Material;
import granite.engine.model.Mesh;
import granite.engine.shaders.EntityShader;
import granite.engine.util.MathUtil;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class EntityRenderer extends BatchRenderer<EntityShader> {

    private static final float RENDER_DISTANCE = 1000;

    public EntityRenderer() {
        super(new EntityShader(), new EntityManager());
    }

    @Override
    public void renderBatch(Mesh mesh, List<Entity> entities, Camera camera, Light light) {
        Vector3f cameraPosition = camera.getPosition();
        getShader().bind();
        getShader().loadViewMatrix(camera);
        Material mtl = mesh.getMaterial();
        getShader().loadDiffuseColor(mtl.getDiffuseColor());
        getShader().loadSpecularVariables(mtl.getSpecularColor(), mtl.getSpecularExponent());
        getShader().loadLight(light);
        mesh.bind();
        mesh.enableArrays();
        for (Entity entity : entities) {
            if (new Vector3f(cameraPosition).sub(entity.getPosition()).length() <= RENDER_DISTANCE) {
                Matrix4f transformationMatrix = MathUtil.createTransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScale());
                getShader().loadTransformationMatrix(transformationMatrix);
                glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);
            }
        }
        mesh.disableArrays();
        mesh.unbind();
    }
}
